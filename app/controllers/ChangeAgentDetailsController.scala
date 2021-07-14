package controllers


import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsValue, Json}
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites
import play.api.mvc.{Action, BaseController, ControllerComponents}
import repos.AgentDetailsRepo
import javax.inject.Inject

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
    repo.updateEmail(arn, (request.body \ "email").as[String]).map {
      case true => Accepted
      case false => NotAcceptable
    }
  }

  def updateContactNumber(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    repo.updateContactNumber(arn, (request.body \ "contactNumber").as[Long]).map {
      case true => Accepted
      case false => NotAcceptable
    }
  }

  def updateAddress(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    repo.updateAddress(arn, (request.body \ "propertyNumber").as[String], (request.body \ "postcode").as[String]).map {
      case true => Accepted
      case false => NotAcceptable
    }
  }

  def updateCorrespondence(arn: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    repo.updateCorrespondence(arn, (request.body \ "moc").as[List[String]]).map {
      case true => Accepted
      case false => NotAcceptable
    }
  }
}
