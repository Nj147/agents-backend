package connectors

import models._
import org.mindrot.jbcrypt.BCrypt
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import repos.AgentLoginRepo
import uk.gov.hmrc.mongo.test.DefaultPlayMongoRepositorySupport

class AgentLoginRepoIT extends AbstractRepoTest with DefaultPlayMongoRepositorySupport[AgentLogin]{
  lazy val repository = new AgentLoginRepo(mongoComponent)

  val agent: AgentLogin = AgentLogin("ARN101", BCrypt.hashpw("password", BCrypt.gensalt()))

  "createAgentLogin" should {
    "return a true if the details dont exist already" in {
      await(repository.createAgentLogin(agent: AgentLogin)) shouldBe true
    }
    "return false if the agents already exists" in {
      await(repository.createAgentLogin(agent: AgentLogin))
      await(repository.createAgentLogin(agent: AgentLogin)) shouldBe false
    }
  }

  "readAgent" should {
    "return an agent if it exists" in {
      await(repository.createAgentLogin(agent: AgentLogin))
      await(repository.readAgent(agent.arn: String)) shouldBe Some(agent)
    }
    "return none if the agents dosent exists" in {
      await(repository.readAgent(agent.arn: String)) shouldBe None
    }
  }

  "checkAgent" should {
    "return true if the details are correct" in {
      await(repository.createAgentLogin(agent: AgentLogin))
      await(repository.checkAgent(agent.copy(password = "password"): AgentLogin)) shouldBe true
    }
    "return false if the details are wrong" in {
      await(repository.checkAgent(agent.copy(password = BCrypt.hashpw("password1", BCrypt.gensalt())): AgentLogin)) shouldBe false
    }
  }


}
