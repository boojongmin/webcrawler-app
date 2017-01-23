package crawler.service

import java.net.{MalformedURLException, URI, URISyntaxException, URL}
import java.time.LocalDateTime
import javax.persistence.{EntityManager, EntityManagerFactory}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.Logger
import crawler.models.{Context, Host}
import crawler.util.{jpaDML, jpaInsertOrUpdate}

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
//import crawler.service.HostStatus.{ALL, FALSE, TRUE}
import crawler.util.{jpaSelect, jpaSelectOne}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class CrawlerService(emf: EntityManagerFactory, system: ActorSystem) {
  private val log = Logger(classOf[CrawlerService])

  implicit val _emf = emf
  implicit val _system = system
  implicit val materializer = ActorMaterializer()


  def getContext(hostName: String): Option[Context] =  {
    try {
      val uri = new URL("http://" + hostName)
    } catch {
      case e:MalformedURLException => log.error("invalid url")
      return None
    }

    val c = new Context(hostName)
    jpaDML {em => {
      val r = em.createQuery(s"from Host H where H.name = :name", classOf[Host]).setParameter("name", hostName ).getResultList.get(0)
      r match {
        case host => {
          host.nextDate = host.nextDate.plusSeconds(host.interval.getSeconds)
          em.persist(host)
        }
      }
    }}

    Some(c)
  }


  def getHtml(url: String): Future[HttpResponse] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))
    responseFuture.andThen {
      case Success(html) => println(html)
      case Failure(html) => println(html)
    }

  }

  def getHostToCrawling(): Option[Host] = {
    val hosts = getHosts(Some(true), LocalDateTime.now())
    hosts.size match {
      case x if x > 0 => Some(hosts.head)
      case 0 => None
    }
  }

  def getHosts(): Seq[Host] = {
    getHosts(None)
  }

  def getHosts(status:Boolean): Seq[Host] = {
    getHosts(Some(status))
  }

  def getHosts(status:Boolean, nextDate: LocalDateTime): Seq[Host] = {
    getHosts(Some(status), nextDate)
  }

  def getHosts(status: Option[Boolean], nextDate: LocalDateTime = null): Seq[Host] = {
    jpaSelect { em =>

      val whereQuery = status match {
        case None => ""
        case _ => s" where H.status = :status"
      }

      val nextDateQuery = nextDate match {
        case null => ""
        case _ => s" and H.nextDate <= :nextDate "
      }

      val query = s"from Host H $whereQuery $nextDateQuery"
      val q = em.createQuery(query, classOf[Host])

      if( status != None ) {
        val s = if( status.get ) true else false
       q.setParameter("status", s)
      }
      if(nextDate != null) { q.setParameter("nextDate", nextDate) }
      q.getResultList
    }
  }
}

//sealed trait HostStatus
//object HostStatus {
//  case object ALL extends HostStatus
//  case object TRUE extends HostStatus
//  case object FALSE extends HostStatus
//}
