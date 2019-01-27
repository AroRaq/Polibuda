package Producer_Consumer

import akka.actor._

import scala.concurrent.duration._
import scala.util.Random
import Producer._


class Producer (val buffer: Buffer) extends Actor {
  override def receive: Receive = {
    case Put => {
      if (buffer.Full) {
        printf("Putting producer to sleep\n")
        sleeping = true
      }
      else {
        buffer.Put
        context.actorSelection("../Consumer") ! Wake
        context.system.scheduler.scheduleOnce(rand.nextInt(500).millis, self, Put)(context.system.dispatcher)
      }
    }
    case Wake => {
      if (sleeping) {
        sleeping = false
        printf("Waking producer up\n")
        context.system.scheduler.scheduleOnce(10.millis, self, Put)(context.system.dispatcher)
      }
    }
  }
  var sleeping = true
}
object Producer {
  val rand = new Random()
}