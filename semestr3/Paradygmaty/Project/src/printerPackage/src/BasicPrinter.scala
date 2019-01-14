import Files._

class BasicPrinter extends Printer {
  var inkLevel : Double = 1
  val paperTray = new PaperTray{
    var capacity = 50
    var sheets = 50
    override def hasPaper(format: Format): Boolean = sheets > 0
    override def restock(number: Int): Unit = 0
  }

  override def print(file: File, format: Format): Unit = {
    if (!file.isInstanceOf[Printable]){
      printf("File format not suitable for printing.")
    }
    else if (!canPrint(format)) {
      printf("Printer doesn't support this file format.")
    }
    else if (file.asInstanceOf[Printable].printInColour) {
      printf("This printer can't print in colour")
    }
    else {
      if (file.size.toFloat / 100 > inkLevel) {
        printf("Not enough ink to print that file.");
      }
      else {
        // druknij to
        printf("Successfully printed %s.", file.name)
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

  override def hasPaper : Boolean = ???

  override def canPrint(file: File): Boolean =
    file match {
      case DocFile(_, _) => true
      case _ => false
    }

  override def isColour = false

  override def InkStatus = "Black ink container is " + inkLevel*100 + "% full"

  override def refill: Unit = {
    inkLevel = 1
    printf("Refilled black ink")
  }
}
