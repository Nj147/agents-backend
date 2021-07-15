package services

import models.AgentDetails
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.libs.json.Json
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AgentDetailsRepo

import scala.concurrent.Future

class ClientServiceSpec extends AbstractServiceTest {

  val repo: AgentDetailsRepo = mock(classOf[AgentDetailsRepo])
  val service = new ClientService(repo)
  val agent: AgentDetails = AgentDetails("ARN0001", "Big Business", "test@test.com", 123456789, List("gg"), "1 New Street", "A01 2BC")
  val response = Json.parse(s"""{
                    | "businessName": "Big Business",
                    | "email": "test@test.com"
                    |}""".stripMargin)

  "readAgent" should {
    "return an AgentClientDetails when agent can't be found" in {
      when(repo.getDetails(any())) thenReturn Future.successful(Some(agent))
      val result = service.readAgent("ARN0001")
      await(result) shouldBe Some(response)
    }
    "return none if agent isn't found" in {
      when(repo.getDetails(any())) thenReturn Future.successful(None)
      val result = service.readAgent("ARN0001")
      await(result) shouldBe None
    }
  }
}
