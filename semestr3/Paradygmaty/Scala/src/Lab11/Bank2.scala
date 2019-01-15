package Lab11

import scala.util.Random

class BankAccount2 {
  private var amount: Int = 1000
  var wants_to_enter = Array(false, false)
  var turn: Int = 0

  def Set(newVal: Int): Unit = {
    amount = newVal
    printf("%d\n", amount)
  }

  def Get(): Int = {
    amount
  }
}

class Taker2(val b: BankAccount2) extends Thread {
  private var sum = 0
  private val rand = new Random()
  override def run(): Unit = {
    for (i <- 0 to 10) {
      b.wants_to_enter(0) = true
      while (b.wants_to_enter(1)) {
        Thread.sleep(1)
        if (b.turn != 0) {
          b.wants_to_enter(0) = false
          while (b.turn != 0) {
            Thread.sleep(1)
          }
          b.wants_to_enter(0) = true
        }
      }
      val ac = b.Get()
      Thread.sleep(rand.nextInt(200))
      sum += 100
      b.Set(ac - 100)
      b.turn = 1
      b.wants_to_enter(0) = false
      Thread.sleep(200+rand.nextInt(200))
    }
  }
}
class Putter2(val b: BankAccount2) extends Thread {
  private var sum = 0
  private val rand = new Random()
  override def run(): Unit = {
    for (i <- 0 to 10) {
      b.wants_to_enter(1) = true
      while (b.wants_to_enter(0)) {
        Thread.sleep(1)
        if (b.turn != 1) {
          b.wants_to_enter(1) = false
          while (b.turn != 1) {
            Thread.sleep(1)
          }
          b.wants_to_enter(1) = true
        }
      }
      val ac = b.Get()
      Thread.sleep(rand.nextInt(200))
      sum += 100
      b.Set(ac + 100)
      b.turn = 0
      b.wants_to_enter(1) = false
      Thread.sleep(200+rand.nextInt(200))
    }
  }
}

object Main2 {
  def main(args: Array[String]): Unit = {
    val bankAccount = new BankAccount2()
    val putter = new Putter2(bankAccount)
    val taker = new Taker2(bankAccount)
    putter.start()
    taker.start()
    putter.join()
    taker.join()
    printf("Desired: 1000, Actual: %d", bankAccount.Get())
  }
}