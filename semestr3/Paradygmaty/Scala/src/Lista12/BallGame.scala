package src.main.scala
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import scala.util.Random

class Player1(val num: Int, val players: Array[ActorRef]) extends Actor {
  def receive = {
    case Player1.Ball(n) => {
      printf("%d %d\n", num, n)
      val r = (num + 1 + Player1.rand.nextInt(players.length)) % players.length
      players(r) ! Player1.Ball(n+1)
    }
  }
}
object Player1 {
  val rand: Random = new Random()
  case class Ball(count: Int)
}

object Main_BallGame extends App {
  val system = ActorSystem("Ball_Game")
  val players = 3
  val actorArray: Array[ActorRef] = new Array[ActorRef](players)
  for (i <- 1 to players)
    actorArray(i-1) = system.actorOf(Props(classOf[Player1], i-1, actorArray))
  actorArray(0) ! Player1.Ball(0)
  Thread.sleep(1000)
  system.terminate
}