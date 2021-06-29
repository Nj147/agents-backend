package controllers

import org.mockito.Mockito.mock
import play.api.test.Helpers
import repos.{AgentDetailsRepo, AgentLoginRepo}

class AgentLoginControllerSpec extends AbstractControllerTest {
  val agentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val agentLoginRepo = mock(classOf[AgentLoginRepo])
  val controller = new AgentLoginController(Helpers.stubControllerComponents(), agentLoginRepo)
}