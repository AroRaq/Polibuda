import Exceptions.{FileNotPrintableException, FormatNotSupported, NotEnoughInkException, PrinterNotColourException}
import Files.{File, Printable}

class BasicPrinter extends Printer {
  var inkLevel : Double = 1

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
        inkLevel -= file.size.toFloat / 100
      }
    }
  }

  override def canPrint(format: Format) : Boolean = {
    format match
    {
      case A3() => false
      case _ => true
    }
  }

  override def isEmpty : Boolean = inkLevel == 0

  override def canPrint(file: File): Boolean = ???
}
