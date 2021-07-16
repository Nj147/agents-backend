package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.ClientService
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global

class ClientController @Inject()(val controllerComponents: ControllerComponents, service: ClientService) extends BaseController {

  def readAgent(arn: String): Action[AnyContent] = Action.async { implicit request =>
    service.readAgent(arn).map {
      case Some(jsValue) => Ok(Json.toJson(jsValue))
      case None => InternalServerError
    }
  }
}
