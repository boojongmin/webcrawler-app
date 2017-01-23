//package example
//
//import akka.NotUsed
//import org.junit.Test
//import akka.stream._
//import akka.stream.scaladsl._
//import akka.{ NotUsed, Done }
//import akka.actor.ActorSystem
//import akka.util.ByteString
//import scala.concurrent._
//import scala.concurrent.duration._
//import java.nio.file.Paths
//
//class AkkaTest {
//
//  @Test
//  def test01() = {
//    val source: Source[Int, NotUsed] = Source(1 to 100)
//    val materializer = ActorMaterializer()
//    source.runForeach(i => println(i))(materializer)
//
//  }
//
//
//}
