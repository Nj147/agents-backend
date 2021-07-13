package controllers

import models.ContactNumber
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import repos.AgentDetailsRepo
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import scala.concurrent.Future

class ChangeAgentDetailsController @Inject()(val controllerComponents: ControllerComponents, repo: AgentDetailsRepo) extends BaseController {

  def updateContactNumber(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[ContactNumber] match {
      case JsSuccess(x, _) => repo.updateContactNumber(x).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case JsError(_) => Future(BadRequest)
    }
  }

}
