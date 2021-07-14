package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class AgentRegisterSpec extends AbstractModelsTest {

  val agentRegisterModel = AgentRegister(password = "testPassword", businessName = "testBusinessName", email = "test@gmail.com", contactNumber = "98765".toLong, moc = List("test"), propertyNumber = "test", postcode = "test")
  val agentRegisterJs = Json.parse(
    """{
      |    "password" : "testPassword",
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
      Json.toJson(agentRegisterModel) shouldBe agentRegisterJs
    }
    "format from json to case class" in {
      Json.fromJson[AgentRegister](agentRegisterJs) shouldBe JsSuccess(agentRegisterModel)
    }
  }

}
