package OOP_Project.printerPackage

import java.text.SimpleDateFormat
import java.util.Calendar

import Files._
import OOP_Project.printerPackage.PaperTrays.{PaperTrayA3, PaperTrayA4}

import util.control.Breaks._
import scala.util.Random
class AllInOnePrinter extends Printer with Scanner {
  var inkBlack: Double = 1
  var inkCyan: Double = 1
  var inkMagenta: Double = 1
  var inkYellow: Double = 1
  val paperTrayA4 = new PaperTrayA4
  val paperTrayA3 = new PaperTrayA3

  override def print(file: File, format: Format): Unit = {
    breakable {
      if (!file.isInstanceOf[Printable]) {
        printf("File format is not suitable for printing.")
      }
      else if (!canPrint(format)) {
        printf("Printer doesn't support this file format.")
      }
      else {
        format match {
          case A4() => if (!paperTrayA4.hasPaper) {
            printf("No paper in the tray.\n")
            break
          }
          case A3() => if (!paperTrayA3.hasPaper) {
            printf("No paper in the tray.\n")
            break
          }
          case _ => {
            printf("No paper in the tray.\n")
            break
          }
        }
        val p = file.asInstanceOf[Printable]
        if (p.printInColour) {
          val size = file.size.toFloat / 100
          val r = new Random()
          if (size > inkCyan || size > inkYellow || size > inkMagenta) {
            printf("Not enough colour ink, please refill.")
          }
          else {
            inkMagenta = math.max(0, inkMagenta - size * (1 + r.nextDouble() * 0.1))
            inkYellow = math.max(0, inkYellow - size * (1 + r.nextDouble() * 0.1))
            inkCyan = math.max(0, inkCyan - size * (1 + r.nextDouble() * 0.1))
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
            format match {
              case A4() => paperTrayA4.sheets -= 1
              case A3() => paperTrayA3.sheets -= 1
              }
          }
        }
      }
    }
  }

  override def canPrint(format: Format): Boolean = format match {
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

  override def hasPaper: Boolean = paperTrayA3.hasPaper || paperTrayA4.hasPaper

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

  override def refillInk: Unit = {
    inkMagenta = 1
    inkYellow = 1
    inkCyan = 1
    inkBlack = 1
  }

  override def refillPaper: Unit = {
    paperTrayA4.restock(100)
    paperTrayA3.restock(100)
  }
}
