package Philosophers

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable

class ChopstickProvider (num: Int) extends Actor {
  override def receive: Receive = {
    case Chopstick(n) => {
      chopstick = Chopstick(n)
      if (que.nonEmpty) {
        que.dequeue() ! chopstick
        chopstick = null
      }
    }
    case Request => {
      if (chopstick == null)
        que.enqueue(sender)
      else {
        sender ! chopstick
        chopstick = null
      }
    }
  }

  var chopstick: Chopstick = Chopstick(num)
  val que = new mutable.Queue[ActorRef]()
}
