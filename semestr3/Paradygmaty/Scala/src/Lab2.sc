import scala.annotation.tailrec
import scala.util.Random

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
     (n == 1) ? curr
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

def max(xs: List[Int]) : List[Int] = {
  @tailrec
  def maxIter(xs: List[Int], ret: List[Int]) : List[Int] = {
    if (xs == Nil)
      ret
    else {
      val ys = insert(ret, xs.head)
      maxIter(xs.tail, if (ys.length > 10) ys.tail else ys)
    }
  }
  def insert(xs: List[Int], x: Int) : List[Int] = {
    if (xs == Nil || xs.head > x)
      x :: xs
    else
     xs.head :: insert(xs.tail, x)
  }


  maxIter(xs, Nil)
}

fibb(10)
fibb2(10)


merge(1 :: 3 :: 5 :: 7 :: 9 :: Nil, 2 :: 4 :: 6 :: 8 :: 10 :: 12 :: 14 :: Nil)
seperate(List.range(-10, 10))

max(List.fill(100)(Random.nextInt((100))));