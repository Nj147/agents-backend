package controllers

import org.mockito.Mockito.mock
import play.api.test.Helpers
import repos.{AgentDetailsRepo, AgentLoginRepo}
import services.AgentLoginService

class AgentLoginControllerSpec extends AbstractControllerTest {
  val agentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val agentLoginRepo = mock(classOf[AgentLoginRepo])
  val service = mock(classOf[AgentLoginService])
  val controller = new AgentLoginController(Helpers.stubControllerComponents(), service)



}