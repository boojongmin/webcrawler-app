package service

import java.time.LocalDateTime
import javax.persistence.{EntityManager, EntityManagerFactory}

import crawler.models.Host
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
    input invalid url then None                     $e31
    input valid url then return Context             $e32

  """

  jpaDML { em =>
    em.persist(new Host("test", "http://test.com", true, LocalDateTime.now()))
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
  val e21 = host.get.name === "test"

  private val url = "http://test.com"
  val c1 = service.getContext("hello")
  val c2 = service.getContext(url)
  val c2Host = service.getHosts().head

  val e31 = c1 === None
  val e32 = c2.get.url === url
//  val e33 = c2Host.nextDate >= host.get.nextDate
  print("c2Host.nextDate")
  print(c2Host.nextDate)
  print(host.get.nextDate)


}
