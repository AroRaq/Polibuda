package Files

class JpegFile(name: String, size: Int) extends File(name, size) with Printable {
  override def printInColour: Boolean = true
}
