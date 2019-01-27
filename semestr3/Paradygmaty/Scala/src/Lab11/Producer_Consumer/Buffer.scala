package Lab11.Producer_Consumer

import scala.collection.mutable

class Buffer(val capacity: Int) {
  var size = 0

  def Put: Unit = synchronized {
    printf("Producing\n")
    size+=1
  }
  def Take = synchronized {
    printf("Consuming\n")
    size-=1
  }

  def Full = size >= capacity
  def Empty = size == 0

}
