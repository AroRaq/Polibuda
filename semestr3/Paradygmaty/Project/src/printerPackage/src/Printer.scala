import Files.File

trait Printer {
  def print(file: File, format: Format) : Unit
  def canPrint(format: Format) : Boolean
  def canPrint(file: File) : Boolean
  def isEmptyBlack : Boolean
  def isEmptyColour : Boolean
  def isColour : Boolean
  def hasPaper : Boolean
  def InkStatus: String
  def refill: Unit
}