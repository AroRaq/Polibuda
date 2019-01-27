package Producer_Consumer

import akka.actor.{ActorSystem, Props}

object ProdCons extends App {
  val buff = new Buffer(10)
  val system = ActorSystem("ProdCons")
  val consumer = system.actorOf(Props(classOf[Consumer], buff), "Consumer")
  val producer = system.actorOf(Props(classOf[Producer], buff), "Producer")
  producer ! Wake
  consumer ! Wake
}