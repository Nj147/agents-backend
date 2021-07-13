package repos
import com.mongodb.client.model.Indexes.ascending
import models._
import org.mongodb.scala.Observable
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.{combine, set}
import org.mongodb.scala.model.{Filters, IndexModel, IndexOptions}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import scala.concurrent.Future

class AgentDetailsRepo @Inject() (mongoComponent: MongoComponent) extends PlayMongoRepository[AgentDetails](
  collectionName = "AgentDetails",
  mongoComponent = mongoComponent,
  domainFormat = AgentDetails.format,
  indexes = Seq(IndexModel(ascending("arn"), IndexOptions().unique(true)))
){

  //Creates an document in the db which consists of agent details
  def createAgent(agent: AgentDetails): Future[Boolean] = collection.insertOne(agent).toFuture().map{ _ => true}.recover{case _ => false}

  //GetS all the details for the agent by specifying the ARN
  def getDetails(arn: String) : Future[Option[AgentDetails]] = collection.find(filter = Filters.eq("arn", arn)).first().toFutureOption()


}
