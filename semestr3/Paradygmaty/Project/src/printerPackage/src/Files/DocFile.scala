package Files

case class DocFile (fileName: String, fileSize: Int) extends File(fileName, fileSize) with Printable {
  override def printInColour: Boolean = false
}
