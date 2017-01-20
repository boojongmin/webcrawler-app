package crawler.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging
import scaldi.Injector
import scaldi.akka.AkkaInjectable

class PingActor(implicit inj: Injector) extends Actor with AkkaInjectable {
  val log = Logging(context.system, this)

  val pong = injectActorRef [PongActor]

  override def receive: Receive =  {
    case "start" | "pong" => {
      log info "ping"
      pong ! "ping"
    }
    case _ => {
      print("jhelll")
    }
  }

}

class PongActor extends Actor with AkkaInjectable {
  val log = Logging(context.system, this)
  override def receive: Receive =  {
    case "ping" => {
      sender ! "pong"
      log info "pong"
      Thread.sleep(1000)
    }
  }
}
