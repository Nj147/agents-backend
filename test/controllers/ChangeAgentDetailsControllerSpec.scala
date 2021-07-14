package controllers

import models.AgentDetails
import play.api.http.Status._
import org.mockito.ArgumentMatchers.any
import play.api.test.{FakeRequest, Helpers}
import org.mockito.Mockito.{mock, when}
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import repos.AgentDetailsRepo
import play.api.http.Status

import scala.concurrent.Future

class ChangeAgentDetailsControllerSpec extends AbstractControllerTest {

  val repo: AgentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
  val agentAddress =Json.parse("""{"propertyNumber": "1 New Street", "postcode": "AA1 2BB"}""")
  val contact = Json.parse("""{"contactNumber": 7986562663}""")
  val agentEmail= Json.parse("""{"email": "test@test.com"}""")
  val agentCorrespondence = Json.parse("""{ "moc": [
                                         |      "Phone call",
                                         |      "Text message"
                                         |      ]
                                         |      }""".stripMargin)

  "/update-address" should {
    "return an accepted status" when {
      "the received JsValue is a valid agent address and the update is successful" in {
        when(repo.updateAddress(any(),any())) thenReturn Future.successful(true)
        val result = controller.updateAddress(arn="ARN0000001").apply(FakeRequest("PATCH", "/update-address").withBody(Json.toJson(agentAddress)))
        status(result) shouldBe ACCEPTED
      }
    }
    "return an unacceptable status" when {
      "the received JsValue is a valid agent address but nothing in the database is updated" in {
        when(repo.updateAddress(any(), any())) thenReturn Future.successful(false)
        val result = controller.updateAddress(arn="ARN0000001").apply(FakeRequest("PATCH", "/update-address").withBody(Json.toJson(agentAddress)))
        status(result) shouldBe NOT_ACCEPTABLE
      }
    }
    "return a bad request status" when {
      "the received JsValue is not a valid agent address" in {
        val result = controller.updateAddress(arn="ARN0001").apply(FakeRequest().withBody(Json.toJson("" -> "")))
        status(result) shouldBe BAD_REQUEST
      }
    }
  }

  "updateContactNumber" should {
    "return 202" when {
      "the update is accepted" in {
        when(repo.updateContactNumber(any(), any())) thenReturn (Future.successful(true))
        val result = controller.updateContactNumber(arn="ARN0001").apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(contact)))
        status(result) shouldBe 202
      }
    }
    "return 406" when {
      "the update is not accepted" in {
        when(repo.updateContactNumber(any(), any())) thenReturn (Future.successful(false))
        val result = controller.updateContactNumber(arn="ARN0001").apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(contact)))
        status(result) shouldBe 406
      }
    }
//    "returns 400" when {
//      "valid json is not given" in {
//        val result = controller.updateContactNumber(arn="ARN0001").apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson("")))
//        status(result) shouldBe 400
//      }
//    }
  }

  val agentDetails = AgentDetails("ARN324234", "Business Ltd", "email@email.com", "0743534534".toLong, List("Text message"), "21", "SW12T54")
  val agentCheck = ("ARN324234")

  "get-agent-details" should {
    "return an OK status" when {
      "a valid body is sent" in {
        val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
        when(repo.getDetails(any())) thenReturn Future.successful(Some(agentDetails))
        val result = controller.readAgent(arn="ARN324234").apply(FakeRequest().withHeaders().withBody(Json.toJson(agentCheck)))
        status(result) shouldBe Status.OK
      }
    }
    "return a NOT_FOUND status" when {
      "no matching arn found" in {
        val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
        when(repo.getDetails(any())) thenReturn Future.successful(None)
        val result = controller.readAgent(arn="ARN324234").apply(FakeRequest().withHeaders().withBody(Json.toJson(agentCheck)))
        status(result) shouldBe Status.NOT_FOUND
      }
    }
//    "return a BAD_REQUEST status" when {
//      "invalid json body sent" in {
//        val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
//        when(repo.getDetails(any())) thenReturn Future.successful(Some(agentDetails))
//        val result = controller.readAgent(arn="ARN324234").apply(FakeRequest().withHeaders().withBody(Json.toJson(None)))
//        status(result) shouldBe Status.BAD_REQUEST
//      }
//    }
  }


  "/update-correspondence" should {
    "return an accepted status" when {
      "the received JsValue is a valid agent correspondence and the update is successful" in {
        when(repo.updateCorrespondence(any(), any())) thenReturn Future.successful(true)
        val result = controller.updateCorrespondence(arn="ARN0001").apply(FakeRequest("PATCH", "/update-correspondence").withBody(Json.toJson(agentCorrespondence)))
        status(result) shouldBe ACCEPTED
      }
    }
    "return an unacceptable status" when {
      "the received JsValue is a valid agent correspondence but nothing in the database is updated" in {
        when(repo.updateCorrespondence(any(), any())) thenReturn Future.successful(false)
        val result = controller.updateCorrespondence(arn="ARN0001").apply(FakeRequest("PATCH", "/update-correspondence").withBody(Json.toJson(agentCorrespondence)))
        status(result) shouldBe NOT_ACCEPTABLE
      }
    }
//    "return a bad request status" when {
//      "the received JsValue is not a valid agent correspondence" in {
//        val result = controller.updateCorrespondence(arn="ARN0001").apply(FakeRequest().withBody(Json.toJson("" -> "")))
//        status(result) shouldBe BAD_REQUEST
//      }
//    }
  }

  "updateEmail;  /update-email" should {
    "return an accepted status" when {
      "the received JsValue is a valid agent email address and the update is successful" in {
        when(repo.updateEmail(any(), any())) thenReturn(Future.successful(true))
        val result = controller.updateEmail(arn="ARN0001").apply(FakeRequest("PATCH", "/update-email").withBody(Json.toJson(agentEmail)))
        status(result) shouldBe ACCEPTED
      }
    }

    "return an unacceptable status" when {
      "the received JsValue is a valid agent email address but nothing in the database is updated" in {
        when(repo.updateEmail(any(), any())) thenReturn Future.successful(false)
        val result = controller.updateEmail(arn="ARN0001").apply(FakeRequest("PATCH", "/update-email").withBody(Json.toJson(agentEmail)))
        status(result) shouldBe NOT_ACCEPTABLE
      }
    }

//    "return a bad request status" when {
//      "the received JsValue is not a valid agent email" in {
//        val result = controller.updateEmail(arn="ARN0001").apply(FakeRequest().withBody(Json.toJson("" -> "")))
//        status(result) shouldBe BAD_REQUEST
//      }
//    }
  }

}
