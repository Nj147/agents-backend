package repos

import com.mongodb.client.model.Indexes.ascending
import models.AgentLogin
import org.mindrot.jbcrypt.BCrypt
import org.mongodb.scala.model.{Filters, IndexModel, IndexOptions}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AgentLoginRepo @Inject()(mongoComponent: MongoComponent) extends PlayMongoRepository[AgentLogin](
  collectionName = "AgentLogin",
  mongoComponent = mongoComponent,
  domainFormat = AgentLogin.format,
  indexes = Seq(IndexModel(ascending("arn"), IndexOptions().unique(true)))
) {

  def createAgentLogin(agent: AgentLogin): Future[Boolean] = collection.insertOne(agent).toFuture().map { _ => true }.recover { case _ => false }

  def readAgent(arn: String): Future[Option[AgentLogin]] = collection.find(filter = Filters.eq("arn", arn)).first().toFutureOption()

  def checkAgent(arn: String, password: String): Future[Boolean] = readAgent(arn).map {
    case Some(user) => BCrypt.checkpw(password, user.password)
    case None => false
  }
}
