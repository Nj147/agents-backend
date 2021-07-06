package controllers

import models.{AgentCheck, RegisteringUser}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import services.ClientService

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ClientController @Inject()(val controllerComponents: ControllerComponents, service: ClientService) extends BaseController {

  def readAgent(): Action[JsValue] = Action.async(parse.json) {
    _.body.validate[AgentCheck] match {
      case JsSuccess(x, _) => service.readAgent(x.arn).map {
        case Some(agent) => Ok(Json.toJson(agent))
        case None => NotFound
      }
      case JsError(_) => Future(BadRequest)
      }
    }

}
