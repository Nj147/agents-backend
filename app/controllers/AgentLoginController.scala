package controllers

import models.AgentLogin
import javax.inject._
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc._
import repos.AgentLoginRepo
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class AgentLoginController @Inject()(val controllerComponents: ControllerComponents, repo: AgentLoginRepo) extends BaseController {

  def checkAgentLogin(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[AgentLogin] match {
      case JsSuccess(agentLogin, _) => repo.checkAgent(agentLogin).map {
        case true => Ok("Agent Login found!")
        case false => NotFound("Agent Login not found!")
      }
      case JsError(errors) => Future(BadRequest(errors.toString()))
    }
  }
}