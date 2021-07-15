package controllers

import javax.inject._
import play.api.libs.json.JsValue
import play.api.mvc._
import repos.AgentLoginRepo
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

@Singleton
class AgentLoginController @Inject()(val controllerComponents: ControllerComponents, repo: AgentLoginRepo) extends BaseController {

  def checkAgentLogin(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>

    Try {
      (request.body \ "password").as[String]
    } match {
      case Success(json) => repo.checkAgent(arn, json).map {
        case true => Ok
        case false => NotFound
      }
      case Failure(_) => Future.successful(BadRequest)
    }
  }
}
