sealed trait lBT[+A]
case object LEmpty extends lBT[Nothing]
case class LNode[+A](elem:A, left:()=>lBT[A], right:()=>lBT[A]) extends lBT[A]






def lrepeat[A](k: Int, str: Stream[A]) : Stream[A] = {
  def f (s: Stream[A], n: Int) : Stream[A] = {
    (s, n) match {
      case (Stream.Empty, _) => Stream.Empty
      case (_ #:: t, 0) => f(t, k)
      case (h #:: _, n) => h #:: f(s, n-1)
    }
  }
  f (str, k)
}

val lfib = {
  def f(n: Int, m: Int) : Stream[Int] = {
    Stream.cons(n, f(m, n+m))
  }
  f(0, 1)
}

def lBFS[A](tree: lBT[A]) : Stream[A] = {
  def f(que: List[lBT[A]]) : Stream[A] = {
    que match {
      case Nil => Stream.Empty
      case LEmpty::t => f(t)
      case LNode(el, lf, rf)::t => Stream.cons(el, f(t ++ List(lf(), rf())))
    }
  }
  f(List(tree))
}

def lTree(n: Int) : lBT[Int] = {
  LNode(n, () => lTree(n*2), () => lTree(2*n+1))
}





lrepeat(2, Stream(1, 2, 3, 4, 5, 6)) force

lfib.take(10) force

lBFS(lTree(1)).take(10) force