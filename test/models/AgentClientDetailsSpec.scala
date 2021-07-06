package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class AgentClientDetailsSpec extends AbstractModelsTest {

  val agentClientDetailsModel: AgentClientDetails = AgentClientDetails(arn = "ARN51226238", businessName = "testBusinessName", email = "testEmail")
  val agentClientDetailsJs: JsValue = Json.parse("""{
                                           |    "arn" : "ARN51226238",
                                           |    "businessName" : "testBusinessName",
                                           |    "email" : "testEmail"
                                           |}""".stripMargin)

  "AgentLogin" should {
    "format from case class to json" in {
      Json.toJson(agentClientDetailsModel) shouldBe agentClientDetailsJs
    }
    "format from json to case class" in {
      Json.fromJson[AgentClientDetails](agentClientDetailsJs) shouldBe JsSuccess(agentClientDetailsModel)
    }
  }

}
