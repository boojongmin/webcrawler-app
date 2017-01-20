package crawler

import javax.persistence._

import akka.actor.ActorSystem
import crawler.actor.PingActor
import crawler.config.HibernateConf
import crawler.models.Host
import crawler.modules.{AkkaModules, JpaModule}
import org.hibernate.Hibernate
import org.hibernate.cfg.{Configuration, Environment}
import scaldi.Module

import scala.collection.convert.{DecorateAsJava, DecorateAsScala}
import scaldi.akka.AkkaInjectable._

object CrawlerApp extends App {

//  implicit val system = inject [ActorSystem]
//  val pingActor = injectActorRef [PingActor]
//  pingActor ! "start"

  implicit val injector = new JpaModule :: new AkkaModules

  val entityManagerFactory = inject [EntityManagerFactory]
  val entityManager = entityManagerFactory.createEntityManager()

  entityManager.getTransaction.begin()
  private val entity = new Host("hello", "http:hello", true)
  entityManager.persist(entity)

  entityManager.getTransaction.commit()
  val resultList = entityManager.createQuery(" from Host", classOf[Host]).getResultList()

  import scala.collection.JavaConverters._
  resultList.asScala.foreach { (x: Host) => println(">" + x) }


  entityManager.close()

  entityManagerFactory.close()


}

