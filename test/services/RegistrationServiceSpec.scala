package services

import models.RegisteringUser
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.{AgentDetailsRepo, AgentLoginRepo}

import scala.concurrent.{Await, Future}

class RegistrationServiceSpec extends AbstractServiceTest {

  val detailsRepo: AgentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val loginRepo: AgentLoginRepo = mock(classOf[AgentLoginRepo])
  val service = new RegistrationService(detailsRepo, loginRepo)
  val agent = RegisteringUser("password", "business", "email", 1234, List("gg"), "addressline1", "postcode")

  "register" should {
    "return a true" in {
      when(detailsRepo.createAgent(any())) thenReturn(Future.successful(true))
      when(loginRepo.createAgentLogin(any())) thenReturn(Future.successful(true))
      val result = service.register(agent)
      await(result).get should include ("ARN")
    }
    "return a false" in {
      when(detailsRepo.createAgent(any())) thenReturn(Future.successful(false))
      when(loginRepo.createAgentLogin(any())) thenReturn(Future.successful(false))
      val result = service.register(agent)
      await(result) should be (None)
    }
  }

  "createARN" should {
    "return an ARN ID" in {
      val result = service.createARN()
      result should include ("ARN")
    }
  }
}
