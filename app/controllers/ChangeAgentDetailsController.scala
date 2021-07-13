package controllers

import models.{AgentAddress, AgentCheck, AgentCorrespondence, ContactNumber}

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import models.AgentAddress
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import repos.AgentDetailsRepo
import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.Future

class ChangeAgentDetailsController @Inject()(
                                              val controllerComponents: ControllerComponents,
                                              repo: AgentDetailsRepo
                                            ) extends BaseController {

  def readAgent(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[AgentCheck] match {
      case JsSuccess(x, _) => repo.getDetails(x.arn).map {
        case Some(agentDetails) => Ok(Json.toJson(agentDetails))
        case _ => NotFound("Agent details not found!")
      }
      case JsError(errors) => Future(BadRequest(s"JsError: $errors"))
    }
  }

  def updateEmail():Action[JsValue] = Action.async(parse.json){
    _.body.validate[AgentEmail] match {
      case JsSuccess (e, _) => repo.updateEmail(e).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case JsError(_) => Future(BadRequest)
    }
  }
  def updateContactNumber(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[ContactNumber] match {
      case JsSuccess(x, _) => repo.updateContactNumber(x).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case JsError(_) => Future(BadRequest)
    }
  }

  def updateAddress(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[AgentAddress] match {
      case JsSuccess(x, _) => repo.updateAddress(x).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case JsError(_) => Future(BadRequest)
    }
  }

  def updateCorrespondence(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[AgentCorrespondence] match {
      case JsSuccess(x, _) => repo.updateCorrespondence(x).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case JsError(_) => Future(BadRequest)
    }
  }
}
