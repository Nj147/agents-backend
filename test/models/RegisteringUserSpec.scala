package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class RegisteringUserSpec extends AbstractModelsTest {

  val registeringUserModel:  RegisteringUser =  RegisteringUser(password = "testPassword", businessName = "testBusinessName", email = "test@gmail.com", mobileNumber = "98765".toInt, moc = List("test"), propertyNumber = "test", postcode = "test")
  val registeringUserJs: JsValue = Json.parse("""{
                                             |    "password" : "testPassword",
                                             |    "businessName" : "testBusinessName",
                                             |    "email" : "test@gmail.com",
                                             |    "mobileNumber" : 98765,
                                             |    "moc" : [
                                             |        "test"
                                             |    ],
                                             |    "propertyNumber" : "test",
                                             |    "postcode" : "test"
                                             |}
                                             |""".stripMargin)

  "AgentDetails" should {
    "format from case class to json" in {
      Json.toJson(registeringUserModel) shouldBe  registeringUserJs
    }
    "format from json to case class" in {
      Json.fromJson[ RegisteringUser](registeringUserJs) shouldBe JsSuccess(registeringUserModel)
    }
  }

}
