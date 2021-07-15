package controllers

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}
import services.ClientService
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class ClientController @Inject()(val controllerComponents: ControllerComponents, service: ClientService) extends BaseController {

  def readAgent(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    service.readAgent(arn).map {
      case Some(jsValue) => Ok(Json.toJson(jsValue))
      case None => InternalServerError
    }
  }
}
