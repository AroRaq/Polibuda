package Files

class DocFile (name: String, size: Int) extends File(name, size) with Printable {
  override def printInColour: Boolean = false
}
