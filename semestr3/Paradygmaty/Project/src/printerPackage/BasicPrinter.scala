package printerPackage

import Files._
import printerPackage.PaperTrays.PaperTray

class BasicPrinter extends Printer {
  var inkLevel : Double = 1
  val paperTray = new PaperTray{
    override val capacity = 50
    override var sheets = 50
    override def format = A4()
    override def hasPaper: Boolean = sheets > 0
    override def restock(number: Int): Unit = math.min(capacity, sheets+number)
  }

  override def print(file: File, format: Format): Unit = {
    if (!hasPaper) {
      printf("No paper, restock the tray.\n")
    }
    else if (!file.isInstanceOf[Printable]){
      printf("File format not suitable for printing.\n")
    }
    else if (!canPrint(format)) {
      printf("Printer doesn't support this file format.\n")
    }
    else if (file.asInstanceOf[Printable].printInColour) {
      printf("This printer can't print in colour.\n")
    }
    else {
      if (file.size.toFloat / 100 > inkLevel) {
        printf("Not enough ink to print that file.\n")
      }
      else {
        // druknij to
        printf("Successfully printed %s.\n", file.name)
        inkLevel -= file.size.toFloat / 100
      }
    }
  }

  override def canPrint(format: Format) : Boolean = {
    format match {
      case A3() => false
      case _ => true
    }
  }

  override def isEmptyBlack : Boolean = inkLevel == 0
  override def isEmptyColour : Boolean = true

  override def hasPaper : Boolean = paperTray.hasPaper

  override def canPrint(file: File): Boolean =
    file match {
      case DocFile(_, _) => true
      case _ => false
    }

  override def isColour = false

  override def InkStatus = "Black ink container is " + inkLevel*100 + "% full"

  override def refillInk: Unit = {
    inkLevel = 1
    printf("Refilled black ink")
  }

  override def refillPaper: Unit = {
    paperTray.restock(100)
  }
}
