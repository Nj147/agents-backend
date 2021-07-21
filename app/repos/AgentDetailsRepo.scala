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

  def updateContactNumber(arn: String, contactNumber: String): Future[Boolean] = collection.updateOne(equal("arn", arn), combine(set("contactNumber", contactNumber))).toFuture()
    .map {
      _.getMatchedCount match {
        case 1 => true
        case _ => false
      }
    }

  def updateAddress(arn: String, address: Address): Future[Boolean] = collection.updateOne(equal("arn", arn), combine(set("propertyNumber", address.propertyNumber), set("postcode", address.postcode))).toFuture().map { response =>
    response.getMatchedCount match {
      case 1 => true
      case _ => false
    }
  }

  def updateCorrespondence(arn: String, moc: List[String]): Future[Boolean] = collection.updateOne(equal("arn", arn), combine(set("moc", moc))).toFuture()
    .map {
      _.getMatchedCount match {
        case 1 => true
        case _ => false
      }
    }

  def updateEmail(arn: String, email: String):Future[Boolean] = collection.updateOne(equal("arn", arn), combine(set("email", email))).toFuture()
    .map{
      _.getMatchedCount match {
        case 1 => true
        case _ => false
      }
    }
}
