package controllers

import models.AgentEmail
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import repos.AgentDetailsRepo

import javax.inject.Inject
import scala.concurrent.Future

class ChangeAgentDetailsController @Inject()(val controllerComponents: ControllerComponents, repo:AgentDetailsRepo ) extends BaseController {

  def updateEmail():Action[JsValue] = Action.async(parse.json){
    _.body.validate[AgentEmail] match {
      case JsSuccess (e, _) => repo.updateOneEmail(e).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case JsError(_) => Future(BadRequest)
    }
  }
}
