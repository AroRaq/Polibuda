package OOP_Project.printerPackage.Files

case class JpegFile(fileName: String, fileSize: Int) extends File(fileName, fileSize) with Printable {
  override def printInColour: Boolean = true
}
