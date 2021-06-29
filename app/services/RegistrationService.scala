package services

import models.{AgentDetails, AgentLogin, RegisteringUser}
import org.mindrot.jbcrypt.BCrypt
import repos.{AgentDetailsRepo, AgentLoginRepo}

import scala.concurrent.ExecutionContext.Implicits.global
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.Future

class RegistrationService @Inject()(agentDetailsRepo: AgentDetailsRepo, agentLoginRepo: AgentLoginRepo) {

  def register(agent: RegisteringUser): Future[Boolean] = {
    val arn = createARN()
    for {
      createAgent <- agentDetailsRepo.createAgent(AgentDetails(arn, agent.businessName, agent.email, agent.mobileNumber, agent.moc, agent.addresslineOne, agent.addressLineTwo, agent.city, agent.postcode))
      createLogin <- agentLoginRepo.createAgentLogin(AgentLogin(arn, BCrypt.hashpw(agent.password, BCrypt.gensalt())))
    } yield (createAgent, createLogin) match {
      case (true, true) => true
      case _ => false
    }
  }

  def createARN(): String = {
    val id = UUID.randomUUID().toString.replace("-", "")
    "ARN" + id.substring(id.length() - 8).toUpperCase()
  }
}



