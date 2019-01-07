import Files.File

trait Printer {
  def print(file: File, format: Format) : Unit
  def canPrint(format: Format) : Boolean
  def canPrint(file: File) : Boolean
  def isEmpty : Boolean
  def hasPaper : Boolean
}