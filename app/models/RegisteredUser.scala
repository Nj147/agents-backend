package models

import play.api.libs.json.{Json, OFormat}

case class AgentDetails(arn: String, businessName: String, email: String, contactNumber: Long, moc: List[String], propertyNumber: String, postcode: String)

object AgentDetails {
  implicit val format: OFormat[AgentDetails] = Json.format[AgentDetails]
}

case class RegisteringUser(password: String, businessName: String, email: String, contactNumber: Long, moc: List[String], propertyNumber: String, postcode: String)

object RegisteringUser {
  implicit val format: OFormat[RegisteringUser] = Json.format[RegisteringUser]
}

case class AgentClientDetails(arn: String, businessName: String, email: String)

object AgentClientDetails {
  implicit val format: OFormat[AgentClientDetails] = Json.format[AgentClientDetails]
}


case class ContactNumber(arn: String, contactNumber: Long)

object ContactNumber {
  implicit val format: OFormat[ContactNumber] = Json.format[ContactNumber]
}

case class AgentCorrespondence(arn: String, moc: List[String])

object AgentCorrespondence{
  implicit val format: OFormat[AgentCorrespondence] = Json.format[AgentCorrespondence]
}

case class AgentAddress(arn: String, propertyNumber: String, postcode: String)

object AgentAddress {
  implicit val format: OFormat[AgentAddress] = Json.format[AgentAddress]
}