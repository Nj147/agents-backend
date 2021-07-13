package connectors

import models._
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AgentDetailsRepo
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class AgentDetailsRepoIT extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[AgentDetails] {
  lazy val repository = new AgentDetailsRepo(mongoComponent)

  val agent: AgentDetails = AgentDetails("ARN00000", "testBusinessName", "testEmail", 0x8, List("test"), "testAddressLine1", "testPostcode")
  val agent2: AgentDetails = AgentDetails("ARN00000", "BusinessName", "Email", 0x8, List("test"), "AddressLine1", "Postcode")
  val agentAddress: AgentAddress = AgentAddress("ARN00000", "1 New Street", "AA1 2BB")


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

  "updateAddress" should{
    "return true and update values in the database" when {
      "given an agentAddress with an existing arn" in {
        await(repository.createAgent(agent))
        await(repository.getDetails("ARN00000": String)).get.propertyNumber shouldBe "testAddressLine1"
        await(repository.getDetails("ARN00000": String)).get.postcode shouldBe "testPostcode"
        await(repository.updateAddress(agentAddress)) shouldBe true
        await(repository.getDetails("ARN00000": String)).get.propertyNumber shouldBe "1 New Street"
        await(repository.getDetails("ARN00000": String)).get.postcode shouldBe "AA1 2BB"
      }
    }
    "return false" when {
      "no records in the database match the arn given" in {
        await(repository.createAgent(agent))
        await(repository.getDetails("ARN00000": String)).get.propertyNumber shouldBe "testAddressLine1"
        await(repository.getDetails("ARN00000": String)).get.postcode shouldBe "testPostcode"
        await(repository.updateAddress(agentAddress.copy(arn = "invalidARN"))) shouldBe false
      }
      "the arn given matches but there are no differences in the postcode and property number" in {
        await(repository.createAgent(agent))
        await(repository.updateAddress(agentAddress.copy(propertyNumber = agent.propertyNumber, postcode = agent.postcode))) shouldBe false
      }
    }
  }


  "UpdateContactNumber" should {
    "returns true if the contact number updated" in {
      await(repository.createAgent(agent: AgentDetails))
      await(repository.updateContactNumber(ContactNumber("ARN00000", "079865626663".toLong))) shouldBe true
    }
    "returns false if the contact number is not updated" in {
      await(repository.updateContactNumber(ContactNumber("ARN00000", "079865626663".toLong))) shouldBe false
    }
  }

}
