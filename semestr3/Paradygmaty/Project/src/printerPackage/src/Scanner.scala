import Files.File

trait Scanner {
  def scanMonochrome: File
  def scanColour: File
}
