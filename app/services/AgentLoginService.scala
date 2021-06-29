package services

import models.AgentLogin
import repos.{AgentDetailsRepo, AgentLoginRepo}

import javax.inject.Inject
import scala.concurrent.Future

class AgentLoginService @Inject()(
                                 repo: AgentLoginRepo
                                 ){

  def checkAgentLogin(agentLogin: AgentLogin): Future[Boolean] = {
    repo.checkAgent(agentLogin)
  }

}
