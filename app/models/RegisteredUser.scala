package models

import play.api.libs.json.{Json, OFormat}

case class AgentDetails(arn: String, businessName: String, email: String, mobileNumber: Int, moc: List[String], propertyNumber: String, postcode: String)

object AgentDetails{
  implicit val format: OFormat[AgentDetails] = Json.format[AgentDetails]
}

case class AgentLogin(arn: String, password: String)

object AgentLogin{
  implicit val format: OFormat[AgentLogin] = Json.format[AgentLogin]
}

case class RegisteringUser(password: String, businessName: String, email: String, mobileNumber: Int, moc: List[String], propertyNumber: String, postcode: String)

object RegisteringUser{
  implicit val format: OFormat[RegisteringUser] = Json.format[RegisteringUser]
}

case class AgentClientDetails(arn: String, businessName: String, email: String)

object AgentClientDetails{
  implicit val format: OFormat[AgentClientDetails] = Json.format[AgentClientDetails]
}

case class AgentCheck(arn: String)

object AgentCheck{
  implicit val format: OFormat[AgentCheck] = Json.format[AgentCheck]
}