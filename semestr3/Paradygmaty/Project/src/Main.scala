import Files._

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn._

object Main {
  def main(args: Array[String]): Unit = {
    val files: ArrayBuffer[File] = new ArrayBuffer[File]()
    val basicPrinter = new BasicPrinter()
    val allInOne = new AllInOnePrinter()
    while(true) {
      printf("Select Printer:\n")
      printf("1. Basic Printer\n")
      printf("2. All in one\n#")
      var input = readInt()
      var chosenPrinter: Printer = null
      input match {
        case 1 => chosenPrinter = basicPrinter
        case 2 => chosenPrinter = allInOne
      }
      printf("Select Action:\n")
      printf("1. Ink Level\n")
      printf("2. Refill\n")
      printf("3. Print file\n")
      if (chosenPrinter.isInstanceOf[Scanner])
        printf("4. Scan\n")
      input = readInt()
      input match {
        case 1 => printf("%s\n", chosenPrinter.InkStatus)
        case 2 => chosenPrinter.refill
        case 3 => {
          var i = 1
          files.foreach(f => {
            printf("%d. %s\n", i, f.name)
            i = i+1
          })
          printf("%d. New file\n", i)
          i=i-1
          input = readInt()
          input match {
            case in if in < i => {
              chosenPrinter.print(files(in), A4())
            }
            case i => {
              val name = readLine("File name: ")
              printf("File size: ")
              val size = readInt()
              printf("File type:\n")
              printf("1. Document\n")
              printf("2. Picture\n")
              printf("3. Other\n")
              input = readInt()
              input match {
                case 1 => files += DocFile(name, size)
                case 2 => files += JpegFile(name, size)
                case 3 => files += new File(name, size)
              }
            }
          }
        }
        case 4 if chosenPrinter.isInstanceOf[Scanner] => {
          val p = chosenPrinter.asInstanceOf[Scanner]
          printf("Scan type:\n")
          printf("1. Document\n")
          printf("2. Picture\n")
          input = readInt()
          input match {
            case 1 => files += p.scanMonochrome
            case 2 => files += p.scanColour
          }
        }
      }
    }
    val basicPrinter1 = new BasicPrinter()
    println(basicPrinter1.canPrint(A4()))
    val doc1 = DocFile("doc1", 10)
    basicPrinter1.print(doc1, A4())
  }
}
