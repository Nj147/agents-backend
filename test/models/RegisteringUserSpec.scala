package models

import play.api.libs.json.{JsSuccess, JsValue, Json}

class RegisteringUserSpec extends AbstractModelsTest {

  val RegisteringUserModel:  RegisteringUser =  RegisteringUser(password = "testPassword", businessName = "testBusinessName", email = "test@gmail.com", mobileNumber = "98765".toInt, moc = List("test"), propertyNumber = "test", postcode = "test")
  val RegisteringUserJs: JsValue = Json.parse("""{
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
      Json.toJson( RegisteringUserModel) shouldBe  RegisteringUserJs
    }
    "format from json to case class" in {
      Json.fromJson[ RegisteringUser]( RegisteringUserJs) shouldBe JsSuccess( RegisteringUserModel)
    }
  }

}
