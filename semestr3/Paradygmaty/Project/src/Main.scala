import Files._
import printerPackage._

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn._
import util.control.Breaks._

object Main {
  def main(args: Array[String]): Unit = {
    val files: ArrayBuffer[File] = new ArrayBuffer[File]()
    val basicPrinter = new BasicPrinter()
    val allInOne = new AllInOnePrinter()
    while(true) {
      breakable {
        printf("Select Printer:\n")
        printf("1. Basic Printer\n")
        printf("2. All in one\n#")
        var input = readInt()
        var chosenPrinter: Printer = null
        input match {
          case 1 => chosenPrinter = basicPrinter
          case 2 => chosenPrinter = allInOne
          case _ => {
            printf("No such position\n")
            break
          }
        }
        printf("Select Action:\n")
        printf("1. Ink Level\n")
        printf("2. Refill Ink\n")
        printf("3. Restock Paper\n")
        printf("4. Print file\n")
        if (chosenPrinter.isInstanceOf[Scanner])
          printf("5. Scan\n")
        input = readInt()
        input match {
          case 1 => printf("%s\n", chosenPrinter.InkStatus)
          case 2 => chosenPrinter.refillInk
          case 3 => chosenPrinter.refillPaper
          case 4 => {
            var i = 1
            files.foreach(f => {
              printf("%d. %s\n", i, f.name)
              i = i + 1
            })
            printf("%d. New file\n", i)
            input = readInt()
            input match {
              case in if in < i => {
                chosenPrinter.print(files(in-1), A4())
              }
              case in if in == i => {
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
                  case _ => printf("No such position\n")
                }
              }
              case _ => printf("No such position\n")
            }
          }
          case 5 if chosenPrinter.isInstanceOf[Scanner] => {
            val p = chosenPrinter.asInstanceOf[Scanner]
            printf("Scan type:\n")
            printf("1. Document\n")
            printf("2. Picture\n")
            input = readInt()
            input match {
              case 1 => files += p.scanMonochrome
              case 2 => files += p.scanColour
              case _ => printf("No such position\n")
            }
          }
          case _ => printf("No such position\n")
        }
      }
    }
  }
}
