package Lab10

import scala.util.Random

class BankAccount {
  private var amount: Int = 1000

  def Set(newVal: Int): Unit = {
    amount = newVal
    printf("%d\n", amount)
  }

  def Get(): Int = {
    amount
  }
}

class Taker(val b: BankAccount) extends Thread {
  private var sum = 0
  private val rand = new Random()
  override def run(): Unit = {
    for (i <- 0 to 10) {
      val ac = b.Get()
      Thread.sleep(rand.nextInt(200))
      sum += 100
      b.Set(ac - 100)
      Thread.sleep(200+rand.nextInt(200))
    }
  }
}
class Putter(val b: BankAccount) extends Thread {
  private var sum = 0
  private val rand = new Random()
  override def run(): Unit = {
    for (i <- 0 to 10) {
      val ac = b.Get()
      Thread.sleep(rand.nextInt(200))
      sum += 100
      b.Set(ac + 100)
      Thread.sleep(200+rand.nextInt(200))
    }
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val bankAccount = new BankAccount()
    val putter = new Putter(bankAccount)
    val taker = new Taker(bankAccount)
    putter.start()
    taker.start()
    putter.join()
    taker.join()
    printf("Desired: 1000, Actual: %d", bankAccount.Get())
  }
}