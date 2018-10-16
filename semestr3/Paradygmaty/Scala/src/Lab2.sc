import scala.annotation.tailrec

def fibb(n: Int) : Int = {
  @tailrec
  def fibbTl(n: Int, curr: Int, last: Int): Int = {
    if (n == 1)
      curr
    else
      fibbTl(n - 1, curr + last, curr)
  }

  fibbTl(n, 1, 0)
}

def seperate(xs: List[Int]) : (List[Int], List[Int], List[Int]) = {
  if (xs == Nil)
    (Nil, Nil, Nil)
  else {
    val (x, y, z) = seperate(xs.tail)
    if (xs.head % 2 == 0) (xs.head :: x, y, z)
    else if (xs.head % 2 == 1) (x, xs.head :: y, z)
    else (x, y, xs.head :: z)
  }
}


def merge[A](xs: List[A], ys: List[A]) : List[A] = {
  if (xs == Nil) ys
  else xs.head :: merge(ys, xs.tail)
}


merge(1 :: 3 :: 5 :: 7 :: 9 :: Nil, 2 :: 4 :: 6 :: 8 :: 10 :: 12 :: 14 :: Nil)
seperate(List.range(-10, 10))