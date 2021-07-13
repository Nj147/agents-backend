package connectors

import models._
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AgentDetailsRepo
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class AgentDetailsRepoIT extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[AgentDetails] {
  lazy val repository = new AgentDetailsRepo(mongoComponent)

  def agent: AgentDetails = AgentDetails("ARN00000", "testBusinessName", "testEmail", 0x8, List("test"), "testAddressLine1", "testPostcode")
  def agent2: AgentDetails = AgentDetails("ARN00000", "BusinessName", "Email", 0x8, List("test"), "AddressLine1", "Postcode")

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
    "returns none if the user does not exist" in {
      await(repository.getDetails("ARN00000": String)) shouldBe None
    }
  }



}
