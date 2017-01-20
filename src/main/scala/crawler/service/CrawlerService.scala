package crawler.service

import java.net.{MalformedURLException, URI, URISyntaxException, URL}
import java.time.LocalDateTime
import javax.persistence.{EntityManager, EntityManagerFactory}

import com.typesafe.scalalogging.Logger
import crawler.models.{Context, Host}
import crawler.util.{jpaDML, jpaInsertOrUpdate}
//import crawler.service.HostStatus.{ALL, FALSE, TRUE}
import crawler.util.{jpaSelect, jpaSelectOne}

class CrawlerService(emf: EntityManagerFactory) {
  private val log = Logger(classOf[CrawlerService])

  implicit val _emf = emf

  def getContext(url: String): Option[Context] =  {
    try {
      val uri = new URL(url)
    } catch {
      case e:MalformedURLException => log.error("invalid url")
      return None
    }

    val c = new Context(url)
    jpaDML {em => {
      val r = em.createQuery(s"from Host H where H.url = :url", classOf[Host]).setParameter("url", url ).getResultList.get(0)
      r match {
        case host => {
          host.nextDate.plusSeconds(host.interval.getSeconds)
          em.persist(host)
        }
      }
    }}

    Some(c)

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
