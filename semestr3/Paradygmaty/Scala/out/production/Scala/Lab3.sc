def findOdd (xs: List[Int], accum: Int = 0) : Int = {
  if (xs == Nil) accum
  else findOdd (xs.tail, accum ^ xs.head)
}

findOdd(1 :: 2::3::2::3::1::(-1)::10::(-1)::Nil)