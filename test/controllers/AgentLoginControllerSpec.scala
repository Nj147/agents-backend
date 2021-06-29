package controllers

import models.AgentLogin
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import repos.{AgentDetailsRepo, AgentLoginRepo}
import services.AgentLoginService

import scala.concurrent.Future

class AgentLoginControllerSpec extends AbstractControllerTest {
  val agentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val agentLoginRepo = mock(classOf[AgentLoginRepo])
  val service = mock(classOf[AgentLoginService])
  val controller = new AgentLoginController(Helpers.stubControllerComponents(), service)
  val agentLogin = AgentLogin("arnNo", "pa55w0rd")


  "checkAgentLogin" should {
    "return a 200 status when checkAgentLogin is successful" in {
      when(service.checkAgentLogin(any())) thenReturn Future.successful(true)
      val result = controller.checkAgentLogin().apply(FakeRequest().withHeaders().withBody(Json.toJson(agentLogin)))
      status(result) shouldBe 200
    }
    "return a 400 status when checkAgentLogin is unsuccessful" in {
      when(service.checkAgentLogin(any())) thenReturn Future.successful(false)
      val result = controller.checkAgentLogin().apply(FakeRequest().withHeaders().withBody(Json.toJson(agentLogin)))
      status(result) shouldBe 500
    }
  }



}