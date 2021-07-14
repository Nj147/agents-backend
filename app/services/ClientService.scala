package services

import play.api.libs.json.{JsValue, Json}
import repos.AgentDetailsRepo

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ClientService @Inject()(repo: AgentDetailsRepo) {

  def readAgent(arn: String): Future[Option[JsValue]] = {
    repo.getDetails(arn).map {
      case Some(agent) => Some(Json.parse(
        s"""{
          | "businessName": "${agent.businessName}",
          | "email": "${agent.email}"
          |}""".stripMargin))
      case None => None
    }
  }

}
