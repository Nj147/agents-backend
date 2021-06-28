package connectors

import models._
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AgentDetailsRepo
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class AgentDetailsRepoSpec extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[AgentDetails]{
  lazy val repository = new AgentDetailsRepo(mongoComponent)
  def agent: AgentDetails = AgentDetails("ARN00000", "testBusinessName", "testEmail", 0x8, "testMoc", "testAddressLine1", "testAddressline2", "testCity", "testPostcode")

  "createAgent" should {
    "return true when agent details are inserted in the db" in {
      await(repository.createAgent(agent: AgentDetails)) shouldBe true
    }
    "returns a false if the same user exists" in {
      await(repository.createAgent(agent: AgentDetails))
      await(repository.createAgent(agent: AgentDetails)) shouldBe false
    }
  }

  "getDetails" should {
    "return all the details if the user exists" in {
      await(repository.createAgent(agent: AgentDetails))
      await(repository.getDetails("ARN00000": String)) shouldBe Some(agent)
    }
    "returns a null object if the user does not exist" in {
      await(repository.getDetails("ARN00000": String)) shouldBe None
    }
  }


}
