package controllers

import org.mockito.Mockito.mock
import play.api.test.Helpers
import repos.{AgentDetailsRepo, AgentLoginRepo}

class RegistrationControllerSpec extends AbstractControllerTest {
  val agentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val agentLoginRepo = mock(classOf[AgentLoginRepo])
  val controller = new  RegistrationController(Helpers.stubControllerComponents(), agentDetailsRepo, agentLoginRepo)

}
