package OOP_Project.printerPackage.PaperTrays

import OOP_Project.printerPackage._

trait PaperTray {
  def format: Format
  def hasPaper : Boolean
  def restock(number: Int) : Unit
  val capacity: Int
  var sheets: Int
}
