package Lab11.Producer_Consumer

import akka.actor._

import scala.concurrent.duration._
import scala.util.Random
import Consumer._

class Consumer (val buffer: Buffer) extends Actor {
  override def receive: Receive = {
    case Take => {
      if (buffer.Empty) {
        sleeping = true
        printf("Putting Consumer to sleep\n")
      }
      else {
        buffer.Take
        context.actorSelection("../Producer") ! Wake
        context.system.scheduler.scheduleOnce(rand.nextInt(300).millis, self, Take)(context.system.dispatcher)
      }
    }
    case Wake => {
      if (sleeping) {
        sleeping = false
        printf("Waking Consumer up\n")
        context.system.scheduler.scheduleOnce(10.millis, self, Take)(context.system.dispatcher)
      }
    }
  }
  var sleeping = true
}
object Consumer {
  val rand = new Random()
}