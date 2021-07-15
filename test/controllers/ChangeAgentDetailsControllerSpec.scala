package controllers

import models.AgentDetails
import play.api.http.Status._
import org.mockito.ArgumentMatchers.any
import play.api.test.{FakeRequest, Helpers}
import org.mockito.Mockito.{mock, when}
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import repos.AgentDetailsRepo
import scala.concurrent.Future

class ChangeAgentDetailsControllerSpec extends AbstractControllerTest {

  val repo: AgentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)

  val agentAddress: JsValue = Json.parse("""{"propertyNumber": "1 New Street", "postcode": "AA1 2BB"}""")
  val contact: JsValue = Json.parse("""{"contactNumber": 7986562663}""")
  val agentEmail: JsValue = Json.parse("""{"email": "test@test.com"}""")
  val agentCorrespondence: JsValue = Json.parse("""{ "moc": ["Phone call", "Text message"]}""")
  val agentDetails: AgentDetails = AgentDetails("ARN324234", "Business Ltd", "email@email.com", "0743534534".toLong, List("Text message"), "21", "SW12T54")
  val agentCheck = ("ARN324234")

  "GET /details" should {
    "return an OK status" when {
      "a valid body is sent" in {
        val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
        when(repo.getDetails(any())) thenReturn Future.successful(Some(agentDetails))
        val result = controller.readAgent(arn = "ARN324234").apply(FakeRequest().withHeaders().withBody(Json.toJson(agentCheck)))
        status(result) shouldBe OK
      }
    }
    "return a NOT_FOUND status" when {
      "no matching arn found" in {
        val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
        when(repo.getDetails(any())) thenReturn Future.successful(None)
        val result = controller.readAgent(arn = "ARN324234").apply(FakeRequest().withHeaders().withBody(Json.toJson(agentCheck)))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH /email" should {
    "return an accepted status" when {
      "the received JsValue is a valid agent email address and the update is successful" in {
        when(repo.updateEmail(any(), any())) thenReturn (Future.successful(true))
        val result = controller.updateEmail(arn = "ARN0001").apply(FakeRequest("PATCH", "/update-email").withBody(Json.toJson(agentEmail)))
        status(result) shouldBe OK
      }
    }
    "return an unacceptable status" when {
      "the received JsValue is a valid agent email address but nothing in the database is updated" in {
        when(repo.updateEmail(any(), any())) thenReturn Future.successful(false)
        val result = controller.updateEmail(arn = "ARN0001").apply(FakeRequest("PATCH", "/update-email").withBody(Json.toJson(agentEmail)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return a bad request status" when {
      "the received JsValue is not a valid agent email" in {
        val result = controller.updateEmail(arn = "ARN0001").apply(FakeRequest().withBody(Json.toJson("" -> "")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "/contact-number" should {
    "return accepted" when {
      "the update is successful" in {
        when(repo.updateContactNumber(any(), any())) thenReturn (Future.successful(true))
        val result = controller.updateContactNumber(arn = "ARN0001").apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(contact)))
        status(result) shouldBe OK
      }
    }
    "return not acceptable" when {
      "the update is unsuccessful" in {
        when(repo.updateContactNumber(any(), any())) thenReturn (Future.successful(false))
        val result = controller.updateContactNumber(arn = "ARN0001").apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(contact)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return badrequest" when {
      "valid json is not given" in {
        val result = controller.updateContactNumber(arn = "ARN0001").apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson("")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "PATCH /address" should {
    "return an accepted status" when {
      "the received JsValue is a valid agent address and the update is successful" in {
        when(repo.updateAddress(any(), any())) thenReturn Future.successful(true)
        val result = controller.updateAddress(arn = "ARN0000001").apply(FakeRequest("PATCH", "/update-address").withBody(Json.toJson(agentAddress)))
        status(result) shouldBe OK
      }
    }
    "return an unacceptable status" when {
      "the received JsValue is a valid agent address but nothing in the database is updated" in {
        when(repo.updateAddress(any(), any())) thenReturn Future.successful(false)
        val result = controller.updateAddress(arn = "ARN0000001").apply(FakeRequest("PATCH", "/update-address").withBody(Json.toJson(agentAddress)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return a bad request status" when {
      "the received JsValue is not a valid agent address" in {
        val result = controller.updateAddress(arn = "ARN0001").apply(FakeRequest().withBody(Json.toJson("" -> "")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

  "/correspondence" should {
    "return an accepted status" when {
      "the received JsValue is a valid agent correspondence and the update is successful" in {
        when(repo.updateCorrespondence(any(), any())) thenReturn Future.successful(true)
        val result = controller.updateCorrespondence(arn = "ARN0001").apply(FakeRequest("PATCH", "/update-correspondence").withBody(Json.toJson(agentCorrespondence)))
        status(result) shouldBe OK
      }
    }
    "return an unacceptable status" when {
      "the received JsValue is a valid agent correspondence but nothing in the database is updated" in {
        when(repo.updateCorrespondence(any(), any())) thenReturn Future.successful(false)
        val result = controller.updateCorrespondence(arn = "ARN0001").apply(FakeRequest("PATCH", "/update-correspondence").withBody(Json.toJson(agentCorrespondence)))
        status(result) shouldBe BAD_REQUEST
      }
    }
    "return a bad request status" when {
      "the received JsValue is not a valid agent correspondence" in {
        val result = controller.updateCorrespondence(arn = "ARN0001").apply(FakeRequest().withBody(Json.toJson("" -> "")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }

}
