package controllers


import models.AgentRegister
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.RegistrationService

import scala.concurrent.Future

class RegistrationControllerSpec extends AbstractControllerTest {
  val service: RegistrationService = mock(classOf[RegistrationService])
  val controller = new RegistrationController(Helpers.stubControllerComponents(), service)
  val obj = AgentRegister("password", "business", "email", "1234".toLong, List("gg"), "addressline1", "postcode")

  "registerAgent" should {
    "return 201 Created" in {
      when(service.register(any())) thenReturn (Future.successful(Some("ARN250")))
      val result = controller.registerAgent().apply(FakeRequest("POST", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(obj)))
      status(result) shouldBe 201
    }
    "return 500 InternalServerError" in {
      when(service.register(any())) thenReturn (Future.successful(None))
      val result = controller.registerAgent().apply(FakeRequest("POST", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(obj)))
      status(result) shouldBe 500
    }
    "returns 400 Badrequest" in {
      val result = controller.registerAgent().apply(FakeRequest("POST", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson("")))
      status(result) shouldBe 400
    }
  }
}
