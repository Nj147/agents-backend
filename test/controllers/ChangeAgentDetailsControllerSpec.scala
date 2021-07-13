package controllers

import models.ContactNumber
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import repos.AgentDetailsRepo
import scala.concurrent.Future

class ChangeAgentDetailsControllerSpec extends AbstractControllerTest {

  val repo: AgentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val controller = new ChangeAgentDetailsController(Helpers.stubControllerComponents(), repo)
  val contact: ContactNumber = ContactNumber("ARN0000", "07986562663".toLong)

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
