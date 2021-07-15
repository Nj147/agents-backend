package services

import models.{AgentDetails, AgentLogin, AgentRegister}
import org.mindrot.jbcrypt.BCrypt
import repos.{AgentDetailsRepo, AgentLoginRepo}
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.UUID
import javax.inject.Inject
import scala.concurrent.Future

class RegistrationService @Inject()(agentDetailsRepo: AgentDetailsRepo, agentLoginRepo: AgentLoginRepo) {

  def register(agent: AgentRegister): Future[Option[String]] = {
    val arn = createARN()
    for {
      createAgent <- agentDetailsRepo.createAgent(AgentDetails(arn, agent.businessName, agent.email, agent.contactNumber, agent.moc, agent.propertyNumber, agent.postcode))
      createLogin <- agentLoginRepo.createAgentLogin(AgentLogin(arn, BCrypt.hashpw(agent.password, BCrypt.gensalt())))
    } yield (createAgent, createLogin) match {
      case (true, true) =>
        Some(arn)
      case _ => None
    }
  }

  def createARN(): String = {
    val id = UUID.randomUUID().toString.replace("-", "")
    "ARN" + id.substring(id.length() - 8).toUpperCase()
  }
}



