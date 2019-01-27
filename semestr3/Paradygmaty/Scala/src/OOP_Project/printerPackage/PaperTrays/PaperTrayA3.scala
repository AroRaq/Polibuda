package OOP_Project.printerPackage.PaperTrays

import OOP_Project.printerPackage._

class PaperTrayA3 extends PaperTray {
  override val capacity = 25
  override var sheets = 25
  override def format = A3()
  override def hasPaper: Boolean = sheets > 0
  override def restock(number: Int): Unit = math.min(capacity, sheets+number)
}
