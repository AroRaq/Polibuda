def ldzialanie[A](str1: Stream[A], str2: Stream[A], f: (A, A) => A) : Stream[A] = {
  (str1, str2) match {
    case (Stream.Empty, str2) => str2
    case (str1, Stream.Empty) => str1
    case (x #:: t1, y #:: t2) => f(x,y) #:: ldzialanie(t1, t2, f)
  }
}

ldzialanie(Stream(1, 2, 3, 4, 5, 6), Stream(2, 3, 4, 5, 6, 7, 8), (x:Int, y:Int) => x + y) force