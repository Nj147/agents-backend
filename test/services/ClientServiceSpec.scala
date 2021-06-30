package services

import models.{AgentClientDetails, AgentDetails}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AgentDetailsRepo

import scala.concurrent.Future

class ClientServiceSpec extends AbstractServiceTest{

  val repo = mock(classOf[AgentDetailsRepo])
  val service = new ClientService(repo)
  val agent = AgentDetails("ARN0001", "Big Business", "test@test.com", 123456789, "call", "1 New Street", "A01 2BC")
  val agentClient = AgentClientDetails("ARN0001", "Big Business", "test@test.com")
  "readAgent" should {
    "return an AgentClientDetails when agent can't be found" in {
      when(repo.getDetails(any())) thenReturn Future.successful(Some(agent))
      val result = service.readAgent("ARN0001")
      await(result) shouldBe Some(agentClient)
    }
    "return none if agent isn't found" in {
      when(repo.getDetails(any())) thenReturn Future.successful(None)
      val result = service.readAgent("ARN0001")
      await(result) shouldBe None
    }
  }
}
