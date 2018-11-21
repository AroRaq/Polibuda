def ldzialanie[A](str1: Stream[A], str2: Stream[A], dzial: (A, A) => A) : Stream[A] = {
  (str1, str2) match {
    case (Stream.Empty, _) => str2
    case (_, Stream.Empty) => str1
    case (h1#::t1, h2#::t2) => dzial(h1, h1)#::ldzialanie(t1, t2, dzial)
  }
}

ldzialanie(1#::2#::3#::Stream.Empty, 2#::3#::4#::5#::Stream.Empty, (a: Int, b: Int) => a + b) force