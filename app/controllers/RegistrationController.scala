package controllers

import models._
import org.mindrot.jbcrypt.BCrypt
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import repos.{AgentDetailsRepo, AgentLoginRepo}

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import scala.concurrent.Future

class RegistrationController @Inject()(val controllerComponents: ControllerComponents, agentDetailsRepo: AgentDetailsRepo, agentLoginRepo: AgentLoginRepo) extends BaseController {

  def registerAgent(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[RegisteringUser] match {
      case JsSuccess(agent, _) =>
        val arn = createARN()
        for{
          createAgent <- agentDetailsRepo.createAgent(AgentDetails(arn, agent.businessName, agent.email, agent.mobileNumber, agent.moc, agent.addresslineOne, agent.addressLineTwo, agent.city, agent.postcode))
          createLogin <- agentLoginRepo.createAgentLogin(AgentLogin(arn,  BCrypt.hashpw(agent.password, BCrypt.gensalt())))
          } yield(createAgent, createLogin) match {
          case (true, true) => Created
          case _ => InternalServerError
        }
      case JsError(_) => Future(BadRequest)
    }
  }

  def createARN(): String ={
    "ARN"+UUID.randomUUID()
  }
}
