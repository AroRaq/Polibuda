package printerPackage.PaperTrays
import printerPackage._

trait PaperTray {
  def format: Format
  def hasPaper : Boolean
  def restock(number: Int) : Unit
  val capacity: Int
  var sheets: Int
}
