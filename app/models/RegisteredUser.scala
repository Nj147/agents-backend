package models

import play.api.libs.json.{Json, OFormat}

case class AgentDetails(arn: String, businessName: String, email: String, mobileNumber: Int, moc: String, addresslineOne: String, addressLineTwo: String, city: String, postcode: String)

object AgentDetails{
  implicit val format: OFormat[AgentDetails] = Json.format[AgentDetails]
}

case class AgentLogin(arn: String, password: String)

object AgentLogin{
  implicit val format: OFormat[AgentLogin] = Json.format[AgentLogin]
}