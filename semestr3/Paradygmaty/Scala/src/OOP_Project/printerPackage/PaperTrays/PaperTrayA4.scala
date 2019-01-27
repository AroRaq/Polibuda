package OOP_Project.printerPackage.PaperTrays

import OOP_Project.printerPackage._

class PaperTrayA4 extends PaperTray {
  override val capacity = 50
  override var sheets = 50
  override def format = A4()
  override def hasPaper: Boolean = sheets > 0
  override def restock(number: Int): Unit = math.min(capacity, sheets+number)
}
