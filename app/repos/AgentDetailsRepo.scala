package repos

import com.mongodb.client.model.Indexes.ascending
import models._
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.{combine, set}
import org.mongodb.scala.model.{Filters, IndexModel, IndexOptions}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import scala.concurrent.Future

class AgentDetailsRepo @Inject()(mongoComponent: MongoComponent) extends PlayMongoRepository[AgentDetails](
  collectionName = "AgentDetails",
  mongoComponent = mongoComponent,
  domainFormat = AgentDetails.format,
  indexes = Seq(IndexModel(ascending("arn"), IndexOptions().unique(true)))
) {

  def createAgent(agent: AgentDetails): Future[Boolean] = collection.insertOne(agent).toFuture().map { _ => true }.recover { case _ => false }

  def getDetails(arn: String): Future[Option[AgentDetails]] = collection.find(filter = Filters.eq("arn", arn)).first().toFutureOption()

  def updateEmail(contactNumber: ContactNumber): Future[Boolean] = collection.updateOne(equal("arn", contactNumber.arn), combine(set("contactNumber", contactNumber.contactNumber))).toFuture()
    .map {
      _.getMatchedCount match {
        case 1 => true
        case _ => false
      }
    }

  def updateOneEmail(agentEmail:AgentEmail):Future[Boolean] = collection.updateOne(equal("arn", agentEmail.arn), combine(set("email", agentEmail.email))).toFuture()
    .map{
      _.getMatchedCount match {
        case 1 => true
        case _ => false
      }
    }

}
