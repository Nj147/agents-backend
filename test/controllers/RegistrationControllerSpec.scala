package controllers

import models.AgentRegister
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.http.Status.CREATED
import play.api.libs.json.Json
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.RegistrationService

import scala.concurrent.Future

class RegistrationControllerSpec extends AbstractControllerTest {
  val service: RegistrationService = mock(classOf[RegistrationService])
  val controller = new RegistrationController(Helpers.stubControllerComponents(), service)
  val obj: AgentRegister = AgentRegister("password", "business", "email", "1234".toLong, List("gg"), "addressline1", "postcode")

  "POST /register" should {
    "return Created" when {
      "the agent is successfully added to the databse" in {
        when(service.register(any())) thenReturn (Future.successful(Some("ARN250")))
        val result = controller.registerAgent().apply(FakeRequest("POST", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(obj)))
        status(result) shouldBe CREATED
      }
    }
    "return InternalServerError" when {
      "the agent cannot be added to the database " in {
        when(service.register(any())) thenReturn (Future.successful(None))
        val result = controller.registerAgent().apply(FakeRequest("POST", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson(obj)))
        status(result) shouldBe 500
      }
    }
    "returns Badrequest" when {
      "when no details are sent" in {
        val result = controller.registerAgent().apply(FakeRequest("POST", "/").withHeaders("Content-Type" -> "application/json").withBody(Json.toJson("")))
        status(result) shouldBe 400
      }
    }
  }
}
