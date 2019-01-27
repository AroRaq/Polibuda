def curry3[A, B, C, D](f: (A, B, C) => D) : A => (B => (C => D)) = {
  (x: A) => ((y: B) => ((z: C) => f(x, y, z)))
}

def uncurry3[A, B, C, D](f: A=>B=>C=>D) : (A, B, C) => D = {
  (x: A, y: B, z: C) => f(x)(y)(z)
}

def sumProd(xs: List[Int]) : (Int, Int) =
  xs.foldLeft(0, 1) { (sp: (Int, Int), x: Int) => (sp._1+x, sp._2*x) }

def insertSort[A](comp: (A, A) => Boolean, xs: List[A]) : List[A] = {
  def insert(sorted: List[A], x: A): List[A] =
    if (sorted == Nil) x :: Nil
    else if (comp(x, sorted.head)) x :: sorted
    else sorted.head :: insert(sorted.tail, x)

  xs.foldLeft[List[A]](Nil) { insert }
}

def mergeSort[A](comp: (A, A) => Boolean, xs: List[A]) : List[A] = {
  def merge(xs: List[A], ys: List[A]) : List[A] = {
    (xs, ys) match {
      case (Nil, Nil) => Nil
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (h1::t1, h2::t2) => if (comp(h1, h2)) h1::merge(t1, ys) else h2::merge(xs, t2)
    }
  }
  xs match {
    case Nil => Nil
    case x::Nil => x::Nil
    case _ => {
      val (a, b) = xs.splitAt(xs.length/2)
      merge(mergeSort(comp, a), mergeSort(comp, b))
    }
  }
}

sumProd(1 :: 2 :: 3 :: 4 :: 5 :: Nil)

//w insertsort elementy na odwrót, insert działa od przodu
insertSort ((x: Int, y: Int) => x > y, 123 :: 13 :: 41 :: 1234 :: 6 :: 123 :: 41 :: Nil)
mergeSort ((x: Int, y: Int) => x >= y, 123 :: 13 :: 41 :: 1234 :: 6 :: 123 :: 41 :: Nil)


insertSort ((x: (Int, Int), y: (Int, Int)) => x._1 > y._1,
  List((123, 0), (13, 1), (41, 2), (1234, 3), (13, 4), (123, 5), (6, 6)))

mergeSort ((x: (Int, Int), y: (Int, Int)) => x._1 >= y._1,
  List((123, 0), (13, 1), (41, 2), (1234, 3), (13, 4), (123, 5), (6, 6)))