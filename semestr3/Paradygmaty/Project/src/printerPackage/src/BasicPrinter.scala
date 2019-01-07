import Exceptions._
import Files._

class BasicPrinter extends Printer {
  var inkLevel : Double = 1
  val paperTray = new PaperTray{
    var capacity = 50
    var sheets = 50
    override def hasPaper(format: Format): Boolean = sheets > 0
    override def restock(format: Format, number: Int): Unit =
  }

  override def print(file: File, format: Format): Unit = {
    if (!file.isInstanceOf[Printable])
      throw new FileNotPrintableException
    if (!canPrint(format))
      throw new FormatNotSupported
    val p = file.asInstanceOf[Printable]
    if (p.printInColour)
      throw new PrinterNotColourException
    else {
      if (file.size.toFloat / 100 > inkLevel)
        throw new NotEnoughInkException
      else {
        // druknij to
        printf("Wydrukowano %s.", file.name)
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

  override def isEmpty : Boolean = inkLevel == 0

  override def hasPaper : Boolean = ???

  override def canPrint(file: File): Boolean =
    file match {
      case DocFile(_, _) => true
      case _ => false
    }
}
