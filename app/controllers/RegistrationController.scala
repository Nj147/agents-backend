package controllers

import models._
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import services.RegistrationService

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import scala.concurrent.Future

class RegistrationController @Inject()(val controllerComponents: ControllerComponents, service: RegistrationService) extends BaseController {

  def registerAgent(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[RegisteringUser] match {
      case JsSuccess(x, _) => service.register(x).map {
        case Some(arn) =>
          Created(Json.toJson(arn))
        case None => InternalServerError
      }
      case JsError(_) => Future(BadRequest)
    }
  }
}
