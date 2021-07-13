package controllers

import models.AgentAddress
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.mvc.AnyContent
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import repos.AgentDetailsRepo

import scala.concurrent.Future

class ChangeAgentDetailsControllerSpec extends AbstractControllerTest {

  val repo = mock(classOf[AgentDetailsRepo])
  val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
  val agentAddress = AgentAddress("ARN0000001", "1 New Street", "AA1 2BB")


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
}
