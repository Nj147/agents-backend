package controllers

import models.ContactNumber
import scala.concurrent.ExecutionContext.Implicits.global
import models.AgentAddress
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import repos.AgentDetailsRepo
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


  def updateAddress: Action[JsValue] = Action.async(parse.json) {
    _.body.validate[AgentAddress] match {
      case JsSuccess(x, _) => repo.updateAddress(x).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case JsError(_) => Future(BadRequest)
    }
  }
}
