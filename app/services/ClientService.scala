package services

import models.{AgentClientDetails, AgentDetails, AgentLogin}
import repos.{AgentDetailsRepo, AgentLoginRepo}

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ClientService @Inject()(repo: AgentDetailsRepo){

    def readAgent(arn: String) = {
      repo.getDetails(arn).map {
        case Some(agent) => Some(AgentClientDetails(agent.arn, agent.businessName, agent.email))
        case None => None
      }
    }

  }
