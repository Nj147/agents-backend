package models

import play.api.libs.json.{Json, OFormat}

case class AgentRegister(password: String, businessName: String, email: String, contactNumber: Long, moc: List[String], propertyNumber: String, postcode: String)

object AgentRegister {
  implicit val format: OFormat[AgentRegister] = Json.format[AgentRegister]
}