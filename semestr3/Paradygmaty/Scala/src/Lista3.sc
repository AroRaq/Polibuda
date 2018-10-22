def curry3[A, B, C, D](f: (A, B, C) => D) : A => (B => (C => D)) = {
  (x: A) => ((y: B) => ((z: C) => f(x, y, z)))
}

def uncurry3[A, B, C, D](f: A=>B=>C=>D) : (A, B, C) => D = {
  (x: A, y: B, z: C) => f(x)(y)(z)
}

def SumProd(xs: List[Int]) : (Int, Int) =
  xs.foldLeft(0, 1) { (sp: (Int, Int), x: Int) => (sp._1+x, sp._2*x) }

SumProd(1 :: 2 :: 3 :: 4 :: 5 :: Nil)

def insertSort[A](comp: (A, A) => Boolean, xs: List[A]) : List[A] = {
  def insert(sorted: List[A], x: A): List[A] =
    if (sorted == Nil) x :: Nil
    else if (comp(x, sorted.head)) sorted.head :: insert(sorted.tail, x)
    else x :: sorted

  xs.foldLeft[List[A]](Nil) { insert }
}

def mergeSort[A](comp: (A, A) => Boolean, xs: List[A]) : List[A] = {
  def merge(xs: List[A], ys: List[A]) : List[A] = {
    (xs, ys) match {
      case (Nil, Nil) => Nil
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (h1::t1, h2::t2) => if (comp (h1, h2)) h2::merge(xs, t2) else h1::merge(t1, ys)
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

insertSort ((x: Int, y: Int) => x > y, 123 :: 13 :: 41 :: 1234 :: 6 :: 123 :: 41 :: Nil)

mergeSort ((x: Int, y: Int) => x > y, 123 :: 13 :: 41 :: 1234 :: 6 :: 123 :: 41 :: Nil)