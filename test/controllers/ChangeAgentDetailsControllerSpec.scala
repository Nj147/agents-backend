package controllers

import models.ContactNumber
import models.AgentAddress
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import repos.AgentDetailsRepo
import scala.concurrent.Future

class ChangeAgentDetailsControllerSpec extends AbstractControllerTest {

  val repo = mock(classOf[AgentDetailsRepo])
  val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
  val agentAddress = AgentAddress("ARN0000001", "1 New Street", "AA1 2BB")
  val contact: ContactNumber = ContactNumber("ARN0000", "07986562663".toLong)

  "/update-address" should {
    "return an accepted status" when {
      "the received JsValue is a valid agent address and the update is successful" in {
        when(repo.updateAddress(any())) thenReturn Future.successful(true)
        val result = controller.updateAddress.apply(FakeRequest("PATCH", "/update-address").withBody(Json.toJson(agentAddress)))
        status(result) shouldBe ACCEPTED
      }
    }
    "return an unacceptable status" when {
      "the received JsValue is a valid agent address but nothing in the database is updated" in {
        when(repo.updateAddress(any())) thenReturn Future.successful(false)
        val result = controller.updateAddress.apply(FakeRequest("PATCH", "/update-address").withBody(Json.toJson(agentAddress)))
        status(result) shouldBe NOT_ACCEPTABLE
      }
    }
    "return a bad request status" when {
      "the received JsValue is not a valid agent address" in{
        val result = controller.updateAddress.apply(FakeRequest().withBody(Json.toJson("" -> "")))
        status(result) shouldBe BAD_REQUEST
      }
    }
  }

  "updateContactNumber" should {
    "return 202 if the update is accepted" in {
      when(repo.updateContactNumber(any())) thenReturn (Future.successful(true))
      val result = controller.updateContactNumber().apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(contact)))
      status(result) shouldBe 202
    }
    "return 406 if the update is not accepted" in {
      when(repo.updateContactNumber(any())) thenReturn (Future.successful(false))
      val result = controller.updateContactNumber().apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(contact)))
      status(result) shouldBe 406
    }
    "returns 400 Badrequest" in {
      val result = controller.updateContactNumber().apply(FakeRequest("PATCH", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson("")))
      status(result) shouldBe 400
    }
  }



}
