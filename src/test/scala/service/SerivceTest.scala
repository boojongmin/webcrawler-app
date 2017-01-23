package service

import java.time.LocalDateTime
import javax.persistence.{EntityManager, EntityManagerFactory}

import crawler.models.{Host, Url}
import crawler.modules.{AkkaModules, CrawlerServiceModule, JpaModule, TestModule}
import crawler.service.CrawlerService
import crawler.util.{jpaDML, jpaSelect}
import org.specs2.Specification
import org.specs2.specification.core.SpecStructure
import scaldi.Module

class SerivceTest extends Specification with TestModule { def is: SpecStructure = s2"""
  This is a specitication to check CrawlerService

  The CrawlerService.getHosts method should
    no argument return 1                   $e11
    true argument return 1                 $e12
    false argument return 0                $e13

  The CrawlerService.getHostToCrawling method should
    host name is test                       $e21

  The CrawlerService.getContext method should
    input invalid url then None                                  $e31
    input valid url then return Context                          $e32
    host.next updated by host.interval after create context      $e33


  The CrawlerService.getHtml method should
    getHtml $e41

  """

  import scala.collection.JavaConverters._

  val hostName = "m.daum.net"
  private val url = "http://test.com"

  jpaDML { em =>
    val _h = new Host(hostName, true)
    val _u = new Url(url)
    _h.urls.add(_u)
    em.persist(_h)
  }

  val list =jpaSelect[Host]{ em =>
    val resultList = em.createQuery("from Host", classOf[Host]).getResultList
    resultList
  }

  val list1 = service.getHosts()
  val list2 = service.getHosts(true)
  val list3 = service.getHosts(false)

  val e11 = list1 must have size 1
  val e12 = list2 must have size 1
  val e13 = list3 must have size 0

  val host = service.getHostToCrawling()
  val e21 = host.get.name === "m.daum.net"

  val c1 = service.getContext("invalid host name")
  val c2 = service.getContext(hostName)
  val c2Host = service.getHosts().head

  val e31 = c1 === None
  val e32 = c2.get.url === url
  val e33 = (c2Host.nextDate.compareTo(host.get.nextDate) > 0) === true

  val e41 = service.getHtml(url) === ""


}
