package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class AgentLoginSpec extends AbstractModelsTest {

  val agentLoginModel: AgentLogin = AgentLogin(arn = "ARN51226238", password = "testPassword")
  val agentLoginJs: JsValue = Json.parse(
    """{
      |    "arn" : "ARN51226238",
      |    "password" : "testPassword"
      |}""".stripMargin)

  "AgentLogin" should {
    "format from case class to json" in {
      Json.toJson(agentLoginModel) shouldBe agentLoginJs
    }
    "format from json to case class" in {
      Json.fromJson[AgentLogin](agentLoginJs) shouldBe JsSuccess(agentLoginModel)
    }
  }

}
