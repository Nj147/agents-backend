package controllers

import models.AgentLogin
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import repos.{AgentDetailsRepo, AgentLoginRepo}
import scala.concurrent.Future

class AgentLoginControllerSpec extends AbstractControllerTest {
  val agentDetailsRepo: AgentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val agentLoginRepo: AgentLoginRepo = mock(classOf[AgentLoginRepo])
  val repo: AgentLoginRepo = mock(classOf[AgentLoginRepo])
  val controller = new AgentLoginController(Helpers.stubControllerComponents(), repo)
  val agentLogin: AgentLogin = AgentLogin("arnNo", "pa55w0rd")


  "checkAgentLogin" should {
    "return a 200 status when checkAgentLogin is successful" in {
      when(repo.checkAgent(any(),any())) thenReturn Future.successful(true)
      val result = controller.checkAgentLogin(arn="ARN001").apply(FakeRequest().withHeaders().withBody(Json.toJson(agentLogin)))
      status(result) shouldBe 200
    }
    "return a 404 status when checkAgentLogin is unsuccessful" in {
      when(repo.checkAgent(any(), any())) thenReturn Future.successful(false)
      val result = controller.checkAgentLogin(arn="ARN001").apply(FakeRequest().withHeaders().withBody(Json.toJson(agentLogin)))
      status(result) shouldBe 404
    }
//    "returns 400 not found" in {
//      val result = controller.checkAgentLogin(arn="ARN001").apply(FakeRequest("POST", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson("")))
//      status(result) shouldBe 400
//    }
  }
}