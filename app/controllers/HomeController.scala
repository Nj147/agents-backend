package controllers

import models.{AgentLogin}

import javax.inject._
import play.api._
import play.api.libs.json.JsSuccess
import play.api.mvc._
import repos.AgentLoginRepo

import scala.concurrent.ExecutionContext.Implicits.global


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, agentLoginRepo: AgentLoginRepo) extends BaseController {

  def checkAgentLogin() = Action.async(parse.json) { implicit request =>
    request.body.validate[AgentLogin] match {
      case JsSuccess(agentLogin, _) => agentLoginRepo.checkAgent(agentLogin).map {
        case true => Ok("Agent Login found!")
        case false => BadRequest("Agent Login not found!")
      }
    }
  }
}
