import java.text.SimpleDateFormat
import java.util.Calendar
import Files._

import scala.util.Random
class AllInOnePrinter extends Printer with Scanner {
  var inkBlack: Double = 1
  var inkCyan: Double = 1
  var inkMagenta: Double = 1
  var inkYellow: Double = 1

  override def print(file: File, format: Format): Unit = {
    if (!file.isInstanceOf[Printable]){
      printf("File format is not suitable for printing.")
    }
    else if (!canPrint(format)) {
      printf("Printer doesn't support this file format.")
    }
    else {
      val p = file.asInstanceOf[Printable]
      if (p.printInColour) {
        val size = file.size.toFloat / 100
        val r = new Random()
        if (size > inkCyan || size > inkYellow || size > inkMagenta) {
          printf("Not enough colour ink, please refill.")
        }
        else {
          inkMagenta = math.max(0, inkMagenta - size*(1+r.nextDouble()*0.1))
          inkYellow = math.max(0, inkYellow - size*(1+r.nextDouble()*0.1))
          inkCyan = math.max(0, inkCyan - size*(1+r.nextDouble()*0.1))
          printf("Succesfully printed %s.", file.name)
        }
      }
      else {
        if (isEmptyColour) {
          printf("Not enough colour ink, please refill.")
        }
        else if (file.size.toFloat / 100 > inkBlack) {
          printf("Not enough ink to print that file.")
        }
        else {
          // druknij to
          printf("Successfully printed %s.", file.name)
          inkBlack -= file.size.toFloat / 100
        }
      }
    }
  }

  override def canPrint(format: Format): Boolean = format match {
    case A3() => false
    case _ => true
  }

  override def canPrint(file: File): Boolean =
    file match {
      case DocFile(_, _) => true
      case JpegFile(_, _) => true
      case _ => false
    }

  override def isEmptyBlack: Boolean = inkBlack <= 0.05
  override def isEmptyColour: Boolean = (inkMagenta <= 0.05 || inkYellow <= 0.05 || inkCyan <= 0.05)

  override def hasPaper: Boolean = ???

  override def scanMonochrome: File = {
    val today = Calendar.getInstance().getTime
    val timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val now = timeFormat.format(today)
    val r = new Random()
    new DocFile("document" + now + ".doc", 1+r.nextInt(9))
  }

  override def scanColour: File = {
    val today = Calendar.getInstance().getTime
    val timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val now = timeFormat.format(today)
    val r = new Random()
    new JpegFile("scan" + now + ".jpg", 10+r.nextInt(20))
  }

  override def isColour: Boolean = true

  override def InkStatus: String = "Black: %d\nCyan: %d\nMagenta: %d\n Yellow: %d".format(inkBlack*100, inkCyan*100, inkMagenta*100, inkYellow*100)

  override def refill: Unit = {
    inkMagenta = 1
    inkYellow = 1
    inkCyan = 1
    inkBlack = 1
  }
}
