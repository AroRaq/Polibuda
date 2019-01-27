abstract class Sequence[+A] {
  def append[B>:A](x: Sequence[B]): Sequence[B]
}


class MyQueue[A] private (private val xs: List[A], private val ys: List[A]) {
  def enqueue[B>:A](el: B) : MyQueue[B] = {
    if (xs == Nil)
      new MyQueue(List(el), ys)
    else
      new MyQueue(xs, el::ys)
  }

  def dequeue() : MyQueue[A] = {
    (xs, ys) match {
      case (h::Nil, ys) => new MyQueue(ys.reverse, Nil)
      case (h::t, ys) => new MyQueue(t, ys)
      case (Nil, _) => this
    }
  }

  def first : A = {
    if (isEmpty)
      throw new Exception("first: Queue empty")
    xs.head
  }

  def isEmpty : Boolean = {
    xs == Nil
  }

  def toList : List[A] = {
    xs ++ ys.reverse
  }

  override def toString: String = toList.toString()
}
object MyQueue {
  def apply[T](xs: T*) = new MyQueue[T](xs.toList, Nil)
}


def copy[A](dest: collection.mutable.Seq[_>:A], src: collection.mutable.Seq[A]) : Unit = {
  require(src.length <= dest.length)
  var i = 0
  src.foreach(a => {dest.update(i, a); i+=1})
}