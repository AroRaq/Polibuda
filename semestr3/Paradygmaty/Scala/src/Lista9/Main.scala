package Lista9
/*
object Main {
  def main(args: Array[String]): Unit = {
    try
      metoda1()
    catch {
      case e: Exception =>
        println(e.getMessage + "\n")
        e.printStackTrace()
    }
  }

  @throws[Exception]
  def metoda1(): Unit = {
    metoda2()
  }

  @throws[Exception]
  def metoda2(): Unit = {
    metoda3()
  }

  @throws[Exception]
  def metoda3(): Unit = {
    throw new Exception("Wyjatek zgloszony w metoda3")
  }
}




Wyjatek zgloszony w metoda3
java.lang.Exception: Wyjatek zgloszony w metoda3

at Lista9.Main$.metoda3(Main.scala:26)
at Lista9.Main$.metoda2(Main.scala:21)
at Lista9.Main$.metoda1(Main.scala:16)
at Lista9.Main$.main(Main.scala:6)
at Lista9.Main.main(Main.scala)
*/