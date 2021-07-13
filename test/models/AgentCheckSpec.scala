package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class AgentCheckSpec extends AbstractModelsTest {

  val agentCheckModel: AgentCheck = AgentCheck(arn = "ARN10005")
  val agentCheckJs: JsValue = Json.parse(
    """{
      |    "arn" : "ARN10005"
      |}
      |""".stripMargin)

  "AgentDetails" should {
    "format from case class to json" in {
      Json.toJson(agentCheckModel) shouldBe agentCheckJs
    }
    "format from json to case class" in {
      Json.fromJson[AgentCheck](agentCheckJs) shouldBe JsSuccess(agentCheckModel)
    }
  }

}
