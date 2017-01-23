package crawler.modules

import javax.persistence.EntityManagerFactory

import akka.actor.ActorSystem
import crawler.actor.{PingActor, PongActor}
import crawler.config.HibernateConf
import crawler.service.CrawlerService
import scaldi.Module

class AkkaModules extends Module {
  bind [ActorSystem] to ActorSystem("CrawlerSystem") destroyWith (_.terminate())

  bind toProvider new PingActor
  bind toProvider new PongActor

}

class JpaModule extends Module {
  bind [EntityManagerFactory] /*identifiedBy 'entityManagerFactory */to HibernateConf.getEntityManagerFactory()
}


class CrawlerServiceModule extends Module {
  bind [CrawlerService] to new CrawlerService(inject [EntityManagerFactory], inject [ActorSystem] )

}

trait TestModule extends Module {
  implicit val modules = new JpaModule :: new CrawlerServiceModule :: new AkkaModules

  val service = inject [CrawlerService]
  implicit val emf = inject [EntityManagerFactory]

}
