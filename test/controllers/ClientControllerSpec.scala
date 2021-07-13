package controllers

import models.AgentClientDetails
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.ClientService
import scala.concurrent.Future

class ClientControllerSpec extends AbstractControllerTest {
  val service: ClientService = mock(classOf[ClientService])
  val controller = new ClientController(Helpers.stubControllerComponents(), service)
  val agent: AgentClientDetails = AgentClientDetails("ARN0001", "TestName", "test@test,com")
  "POST /readAgent" should {
    "return OK response containing AgentClientDetails if matching ARN is found" in {
      when(service.readAgent(any())) thenReturn Future.successful(Some(agent))
      val result = controller.readAgent().apply(FakeRequest("POST", "/readAgent").withBody(Json.obj("arn" -> "ARN0001")))
      status(result) shouldBe 200
    }
    "return a not found request if agent doesn't exist" in {
      when(service.readAgent(any())) thenReturn Future.successful(None)
      val result = controller.readAgent().apply(FakeRequest("POST", "/readAgent").withBody(Json.obj("arn" -> "ARN0001")))
      status(result) shouldBe 404
    }
    "return BadRequest if json body sent isn't valid" in {
      val result = controller.readAgent().apply(FakeRequest("POST", "/readAgent").withBody(Json.obj("firstValue" -> "ARN0001", "secondValue" -> "random")))
      status(result) shouldBe 400
    }
  }
}
