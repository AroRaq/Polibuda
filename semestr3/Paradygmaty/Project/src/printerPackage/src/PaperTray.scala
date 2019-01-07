trait PaperTray {
  def hasPaper(format: Format) : Boolean
  def restock(number: Int) : Unit
}
