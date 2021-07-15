package models

import play.api.libs.json.{Json, OFormat}

case class AgentLogin(arn: String, password: String)

object AgentLogin {
  implicit val format: OFormat[AgentLogin] = Json.format[AgentLogin]
}