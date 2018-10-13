import scala.annotation.tailrec

def fibb(n: Int) : Int = {
    if (n == 0)
        0
    else if (n == 1)
        1
    else
        fibb(n-1) + fibb(n-2)
}

def fibb2(n: Int) : Int = {
  @tailrec
  def fibbTl(n: Int, curr: Int, last: Int): Int = {
    if (n == 1)
      curr
    else
      fibbTl(n - 1, curr + last, curr)
  }

  fibbTl(n, 1, 0)
}


@tailrec
def root3(a: Double, dx: Double, x: Double) : Double = {
    if (Math.abs(x*x*x - a) < dx * Math.abs(a))
        x
    else
        root3(a, dx, x + (a/(x*x) - x)/3)
}

def intSegment[A](xs: List[A], ys: List[A]) : Boolean = {
    if (xs == Nil)
      true
    else
      xs.head == ys.head && intSegment(xs.tail, ys.tail)
}

def replaceNth[A](xs: List[A], n: Int, rep: A) : List[A] = {
  if (n == 0)
    rep :: xs.tail
  else
    xs.head :: replaceNth(xs.tail, n-1, rep)
}




val a = 46
//fibb(a)
fibb2(a)
root3(9, Math.pow(10, -15), if (a > 1) a/3 else a)

intSegment(1 :: 2 :: Nil, 1 :: 2 :: 3 :: 4 :: Nil)
intSegment(Nil, 1 :: 2 :: Nil)
intSegment(1 :: Nil, 2 :: 1 :: Nil)

replaceNth(1 :: 2 :: 3 :: 4 :: 5 :: 6 :: Nil, 4, 10);