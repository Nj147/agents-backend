package services

import models.AgentLogin
import org.mindrot.jbcrypt.BCrypt
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.test.Helpers.status
import repos.AgentLoginRepo

import scala.concurrent.{Await, Awaitable, Future}
import scala.concurrent.duration.Duration

class AgentServiceSpec extends AbstractServiceTest {

  val repo = mock(classOf[AgentLoginRepo])
  val agentLogin = AgentLogin("arnNo", "pa55w0rd")

  def await[T](awaitable: Awaitable[T]): T = Await.result(awaitable, Duration.Inf)

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
