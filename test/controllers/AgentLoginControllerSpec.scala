package controllers

import models.AgentLogin
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.http.Status._
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

  "POST/login" should {
    "return OK status " when {
      "status when checkAgentLogin is successful" in {
        when(repo.checkAgent(any(), any())) thenReturn Future.successful(true)
        val result = controller.checkAgentLogin(arn = "ARN001").apply(FakeRequest().withHeaders().withBody(Json.toJson(agentLogin)))
        status(result) shouldBe OK
      }
    }
    "return a Not found" when {
      "status when checkAgentLogin is unsuccessful" in {
        when(repo.checkAgent(any(), any())) thenReturn Future.successful(false)
        val result = controller.checkAgentLogin(arn = "ARN001").apply(FakeRequest().withHeaders().withBody(Json.toJson(agentLogin)))
        status(result) shouldBe UNAUTHORIZED
      }
    }
    "return bad request" when {
      "when no password is sent" in {
        val result = controller.checkAgentLogin(arn = "ARN001").apply(FakeRequest("POST", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson("")))
        status(result) shouldBe BAD_REQUEST
      }
    }
  }
}