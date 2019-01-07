import Files._

object Main {
  def main(args: Array[String]): Unit = {
    val basicPrinter1 = new BasicPrinter()
    println(basicPrinter1.canPrint(A4()))
    val doc1 = DocFile("doc1", 10)
    basicPrinter1.print(doc1, A4())
  }
}
