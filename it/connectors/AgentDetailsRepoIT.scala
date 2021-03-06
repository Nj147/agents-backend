package connectors

import models._
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AgentDetailsRepo
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class AgentDetailsRepoIT extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[AgentDetails] {
  lazy val repository = new AgentDetailsRepo(mongoComponent)

  val agent: AgentDetails = AgentDetails("ARN00000", "testBusinessName", "testEmail", "07986562663", List("test"), "testAddressLine1", "testPostcode")
  val agent2: AgentDetails = AgentDetails("ARN00000", "BusinessName", "Email", "07986562663", List("test"), "AddressLine1", "Postcode")
  val agentAddress: Address = Address("1 New Street", "AA1 2BB")
  val agentEmail = "test@test.com"


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

  "updateAddress" should {
    "return true and update values in the database" when {
      "given an agentAddress with an existing arn" in {
        await(repository.createAgent(agent))
        await(repository.getDetails("ARN00000": String)).get.propertyNumber shouldBe "testAddressLine1"
        await(repository.getDetails("ARN00000": String)).get.postcode shouldBe "testPostcode"
        await(repository.updateAddress("ARN00000", agentAddress)) shouldBe true
        await(repository.getDetails("ARN00000": String)).get.propertyNumber shouldBe "1 New Street"
        await(repository.getDetails("ARN00000": String)).get.postcode shouldBe "AA1 2BB"
      }
    }
    "return false" when {
      "no records in the database match the arn given" in {
        await(repository.createAgent(agent))
        await(repository.getDetails("ARN00000": String)).get.propertyNumber shouldBe "testAddressLine1"
        await(repository.getDetails("ARN00000": String)).get.postcode shouldBe "testPostcode"
        await(repository.updateAddress("invalidARN", agentAddress)) shouldBe false
      }
      "the arn given matches but there are no differences in the postcode and property number" in {
        await(repository.createAgent(agent))
        await(repository.updateAddress("ARN00000", agentAddress)) shouldBe true
      }
    }
  }

  "UpdateContactNumber" should {
    "return true" when {
      "the contact number has been updated" in {
        await(repository.createAgent(agent: AgentDetails))
        await(repository.updateContactNumber("ARN00000", "079865626663")) shouldBe true
        await(repository.getDetails("ARN00000": String)).get.contactNumber shouldBe "079865626663"
      }
    }
    "returns false " when {
      "the contact number is not updated" in {
        await(repository.updateContactNumber("ARN00000", "079865626663")) shouldBe false
      }
    }
  }

  "UpdateCorrespondence" should {
    "returns true" when {
      "the correspondence has been updated" in {
        await(repository.createAgent(agent: AgentDetails))
        await(repository.updateCorrespondence("ARN00000", List("call"))) shouldBe true
        await(repository.getDetails("ARN00000")).get.moc shouldBe List("call")
      }
    }
    "returns false" when {
      "the correspondence has not been updated" in {
        await(repository.updateCorrespondence("ARN00000", List("call"))) shouldBe false
      }
    }

  }
  "AgentDetailRepo .updateEmail" should {
    "return true and update values in the database" when {
      "given an agentEmail with an existing arn" in {
        await(repository.createAgent(agent))
        await(repository.getDetails("ARN00000": String)).get.email shouldBe "testEmail"
        await(repository.updateEmail("ARN00000", agentEmail)) shouldBe true
        await(repository.getDetails("ARN00000": String)).get.email shouldBe "test@test.com"
      }
    }
    "return false" when {
      "no records in the database match the arn given" in {
        await(repository.createAgent(agent))
        await(repository.getDetails("ARN00000": String)).get.email shouldBe "testEmail"
        await(repository.updateEmail("invalidARN", agentEmail)) shouldBe false

      }
      "the arn given matches but there is no change in email" in {
        await(repository.createAgent(agent))
        await(repository.updateEmail("ARN00000", agentEmail)) shouldBe true
      }
    }
  }
}
