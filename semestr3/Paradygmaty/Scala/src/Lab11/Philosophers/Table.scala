package Lab11.Philosophers

import akka.actor.{ActorRef, ActorSystem, Props}

object Table extends App {

  val system = ActorSystem("system")
  val philosophersNum = 5
  val philosophers = new Array[ActorRef](philosophersNum)
  val chopstickProviders = new Array[ActorRef](philosophersNum)
  for (i <- 0 until philosophersNum)
    chopstickProviders(i) = system.actorOf(Props(classOf[ChopstickProvider], i))
  for (i <- 0 until philosophersNum) {
    philosophers(i) = system.actorOf(Props(classOf[Philosopher], i, chopstickProviders(i), chopstickProviders((i + 1) % philosophersNum)))
  }


}
