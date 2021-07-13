package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class UpdateCorrespondenceSpec extends AbstractModelsTest {

  val updateCorrespondenceModel: AgentCorrespondence = AgentCorrespondence(arn = "ARN51226238", moc = List("test"))
  val updateCorrespondenceJs: JsValue = Json.parse(
    """{
      |    "arn" : "ARN51226238",
      |    "moc" : [
      |        "test"
      |    ]}
      |""".stripMargin)

  "UpdateCorrespondence" should {
    "format from case class to json" in {
      Json.toJson(updateCorrespondenceModel) shouldBe updateCorrespondenceJs
    }
    "format from json to case class" in {
      Json.fromJson[AgentCorrespondence](updateCorrespondenceJs) shouldBe JsSuccess(updateCorrespondenceModel)
    }
  }

}
