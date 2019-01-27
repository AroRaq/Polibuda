package Philosophers

import akka.actor.{Actor, ActorRef}

import scala.concurrent.duration._
import scala.util.Random
import Philosopher._

class Philosopher (val num: Int, val leftChopstickProvider: ActorRef, val rightChopstickProvider: ActorRef) extends Actor {
  context.system.scheduler.scheduleOnce(thinkingTime.millis, self, Eat)(context.system.dispatcher)
  override def receive: Receive = {
    case Think => {
      leftChopstickProvider ! left
      left = null
      rightChopstickProvider ! right
      right = null
      state = Thinking
      context.system.scheduler.scheduleOnce(rand.nextInt(maxEatingTime).millis, self, Eat)(context.system.dispatcher)
      printf("Philosopher %d started thinking\n", num)
    }
    case Eat => {
      printf("Philosopher %d wants to eat\n", num)
      if (left == null) {
        leftChopstickProvider ! Request
        state = Waiting
      }
    }
    case Chopstick(n) => {
      if (left == null) {
        left = Chopstick(n)
        rightChopstickProvider ! Request
      }
      else {
        right = Chopstick(n)
        state = Eating
        printf("Philosopher %d started eating\n", num)
        context.system.scheduler.scheduleOnce(rand.nextInt(maxEatingTime).millis, self, Think)(context.system.dispatcher)
      }
    }
  }

  var state: PhilosopherState = Thinking
  var left: Chopstick = null
  var right: Chopstick = null


}
object Philosopher {
  val rand = new Random()
  val thinkingTime = 500
  val maxEatingTime = 1000
}