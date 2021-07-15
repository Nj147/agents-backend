package controllers

import models.Address
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites
import play.api.mvc.{Action, BaseController, ControllerComponents}
import repos.AgentDetailsRepo
import javax.inject.Inject
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class ChangeAgentDetailsController @Inject()(
                                              val controllerComponents: ControllerComponents,
                                              repo: AgentDetailsRepo
                                            ) extends BaseController {

  def readAgent(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    repo.getDetails(arn).map {
      case Some(details) => Ok(Json.toJson(details))
      case None => NotFound
    }
  }

  def updateEmail(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "email").as[String]
    } match {
      case Success(json) => repo.updateEmail(arn, json).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case Failure(_) => Future.successful(BadRequest)
    }
  }

  def updateContactNumber(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "contactNumber").as[Long]
    } match {
      case Success(json) => repo.updateContactNumber(arn, json).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case Failure(_) => Future.successful(BadRequest)
    }
  }

  def updateAddress(arn: String): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[Address] match {
      case JsSuccess(address, _) => repo.updateAddress(arn, address).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case JsError(_) => Future(BadRequest)
    }
  }

  def updateCorrespondence(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      (request.body \ "moc").as[List[String]]
    } match {
      case Success(json) => repo.updateCorrespondence(arn, json).map {
        case true => Accepted
        case false => NotAcceptable
      }
      case Failure(_) => Future.successful(BadRequest)
    }
  }
}
