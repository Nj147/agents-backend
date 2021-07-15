package controllers

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.http.Status._
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers.{defaultAwaitTimeout, status}
import play.api.test.{FakeRequest, Helpers}
import services.ClientService
import scala.concurrent.Future

class ClientControllerSpec extends AbstractControllerTest {
  val service: ClientService = mock(classOf[ClientService])
  val controller = new ClientController(Helpers.stubControllerComponents(), service)
  val agent: Option[JsValue] = Some(Json.parse("""{"businessName": "businessName", "email": "testEmail"}"""))

  "GET /restricted-details" should {
    "return OK" when {
      "agent exists" in {
        when(service.readAgent(any())) thenReturn Future.successful(agent)
        val result = controller.readAgent(arn = "ARN0001").apply(FakeRequest("POST", "/readAgent").withBody(Json.obj("arn" -> "ARN0001")))
        status(result) shouldBe OK
      }
    }
    "return not found" when {
      "agent doesn't exist" in {
        when(service.readAgent(any())) thenReturn Future.successful(None)
        val result = controller.readAgent(arn = "ARN0001").apply(FakeRequest("POST", "/readAgent").withBody(Json.obj("arn" -> "ARN0001")))
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }
  }
}
