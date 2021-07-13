package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class AgentDetailsSpec extends AbstractModelsTest {

  val agentDetailsModel: AgentDetails = AgentDetails(arn = "ARN51226238", businessName = "testBusinessName", email = "test@gmail.com", contactNumber = "98765".toLong, moc = List("test"), propertyNumber = "test", postcode = "test")
  val agentDetailsJs: JsValue = Json.parse(
    """{
      |    "arn" : "ARN51226238",
      |    "businessName" : "testBusinessName",
      |    "email" : "test@gmail.com",
      |    "contactNumber" : 98765,
      |    "moc" : [
      |        "test"
      |    ],
      |    "propertyNumber" : "test",
      |    "postcode" : "test"
      |}
      |""".stripMargin)

  "AgentDetails" should {
    "format from case class to json" in {
      Json.toJson(agentDetailsModel) shouldBe agentDetailsJs
    }
    "format from json to case class" in {
      Json.fromJson[AgentDetails](agentDetailsJs) shouldBe JsSuccess(agentDetailsModel)
    }
  }

}