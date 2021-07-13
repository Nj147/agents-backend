package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class AgentEmailCheckSpec extends AbstractModelsTest{
  val agentEmailCheckModel:AgentEmail = AgentEmail(arn="ARN0001",  email = "test@test.com")
  val agentEmailCheckJs:JsValue = Json.parse(
    """{
      |    "arn" : "ARN0001",
      |    "email" : "test@test.com"
      |}
      |""".stripMargin)

  "ChangeAgentDetailsController .updateEmail" should {
    "format from case class to json" in {
      Json.toJson(agentEmailCheckModel) shouldBe agentEmailCheckJs
    }
    "format from json to case class" in {
      Json.fromJson[AgentEmail](agentEmailCheckJs) shouldBe JsSuccess(agentEmailCheckModel)
    }

  }
}
