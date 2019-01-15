package src.main.scala

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import scala.util.Random

class Player1(val num: Int, val players: Array[ActorRef]) extends Actor {
  def receive = {
    case Player1.Ball(n) => {
      printf("%d %d\n", num, n)
      var r = Player1.rand.nextInt(players.length)
      while (r == num) r = Player1.rand.nextInt(players.length)
      players(r) ! Player1.Ball(n+1)
    }
  }
}

object Player1 {
  //def props = Props(classOf[Player])
  val rand: Random = new Random()
  case class Ball(count: Int)
}


object Main1 extends App {
  // ActorSystem is a heavy object: create only one per application
  val ourSystem = ActorSystem("MySystem")
  val actorArray: Array[ActorRef] = new Array[ActorRef](3)
  val player0: ActorRef = ourSystem.actorOf(Props(classOf[Player1], 0, actorArray))
  val player1: ActorRef = ourSystem.actorOf(Props(classOf[Player1], 1, actorArray))
  val player2: ActorRef = ourSystem.actorOf(Props(classOf[Player1], 2, actorArray))
  actorArray(0) = player0
  actorArray(1) = player1
  actorArray(2) = player2

  player0.tell(Player1.Ball(0), player0)

  Thread.sleep(1000)
  ourSystem.terminate
}