package services

import models.AgentLogin
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AgentLoginRepo
import scala.concurrent.Future


class AgentServiceSpec extends AbstractServiceTest {

  val repo: AgentLoginRepo = mock(classOf[AgentLoginRepo])
  val agentLogin: AgentLogin = AgentLogin("arnNo", "pa55w0rd")

  "checkAgentLogin" should {
    "return true when repo successfully checks agent login" in {
      when(repo.checkAgent(any())) thenReturn Future.successful(true)
      val result = repo.checkAgent(agentLogin)
      await(result) shouldBe true
    }
    "return false when repo does not find the agent login" in {
      when(repo.checkAgent(any())) thenReturn Future.successful(false)
      val result = repo.checkAgent(agentLogin)
      await(result) shouldBe false
    }
  }


}
