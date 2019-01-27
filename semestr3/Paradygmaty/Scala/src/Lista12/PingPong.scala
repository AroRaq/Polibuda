package src.main.scala
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class Player extends Actor {
  def receive = {
    case Player.Ping => {
      printf("pong\n")
      sender ! Player.Pong
    }
    case Player.Pong => {
      printf("ping\n")
      sender ! Player.Ping
    }
  }
}
object Player {
  def props = Props(classOf[Player])
  case object Ping
  case object Pong
}

object Main_PingPong extends App {
  val system = ActorSystem("Ping_Pong")
  val player1: ActorRef = system.actorOf(Props[Player])
  val player2: ActorRef = system.actorOf(Props[Player])
  player1.tell(Player.Ping, player2)
  Thread.sleep(1000)
  system.terminate
}