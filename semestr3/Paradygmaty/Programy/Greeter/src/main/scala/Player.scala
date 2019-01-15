package src.main.scala

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class Player extends Actor {
  def receive = {
    case "ping" => {
      printf("pong\n")
      sender ! "pong"
    }
    case "pong" => {
      printf("ping\n")
      sender ! "ping"
    }
  }
}

object Player {
  def props = Props(classOf[Player])
}


object Main extends App {
  // ActorSystem is a heavy object: create only one per application
  val ourSystem = ActorSystem("MySystem")
  val ping: ActorRef = ourSystem.actorOf(Props[Player])
  val pong: ActorRef = ourSystem.actorOf(Props[Player])
  ping.tell("ping", pong)

  Thread.sleep(1000)
  ourSystem.terminate
}