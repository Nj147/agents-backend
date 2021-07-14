package controllers

import models.AgentLogin
import play.api.libs.json

import javax.inject._
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc._
import repos.AgentLoginRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class AgentLoginController @Inject()(val controllerComponents: ControllerComponents, repo: AgentLoginRepo) extends BaseController {

  def checkAgentLogin(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
      repo.checkAgent(arn ,(request.body \ "password").as[String]).map{
        case true => Ok
        case false => NotFound
      }
  }
}