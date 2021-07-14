package models

import play.api.libs.json.{Json, OFormat}


case class Address(propertyNumber: String, postcode: String)

object Address {
  implicit val format: OFormat[Address] = Json.format[Address]
}
