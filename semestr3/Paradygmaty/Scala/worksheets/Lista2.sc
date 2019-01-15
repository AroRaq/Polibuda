import scala.annotation.tailrec

def fibb(n: Int) : Int = {
  n match {
    case 0 => 0
    case 1 => 1
    case _ => fibb(n-1) + fibb(n-2)
  }
}

def fibb2(n: Int) : Int = {
  @tailrec
  def fibbTl(n: Int, curr: Int, last: Int): Int = {
    n match {
      case 1 => curr
      case _ => fibbTl(n-1, curr + last, curr)
    }
  }
  fibbTl(n, 1, 0)
}

def root3(a: Double, dx: Double) : Double = {
  @tailrec
  def root3iter(x: Double) : Double = {
    if (Math.abs(x * x * x - a) < dx * Math.abs(a))
      x
    else
      root3iter(x + (a / (x * x) - x) / 3)
  }
  root3iter(if (a > 1) a/3 else a)
}

@tailrec
def initSegment[A](xs: List[A], ys: List[A]) : Boolean = {
  (xs, ys) match {
    case (Nil, _) => true
    case (_, Nil) => false
    case (h1::t1, h2::t2) => h1 == h2 && initSegment(t1, t2)
  }
}

def replaceNth[A](xs: List[A], n: Int, rep: A) : List[A] = {
  (xs, n) match {
    case (Nil, _) => Nil
    case (_, 0) => rep :: xs.tail
    case _ => xs.head :: replaceNth(xs.tail, n-1, rep)
  }
}

val a = 46
fibb(a)
fibb2(a)

root3(9, 1e-015)

initSegment(1 :: 2 :: Nil, 1 :: 2 :: 3 :: 4 :: Nil)
initSegment(Nil, 1 :: 2 :: Nil)
initSegment(1 :: Nil, 2 :: 1 :: Nil)

replaceNth(1 :: 2 :: 3 :: 4 :: 5 :: 6 :: Nil, 4, 10);