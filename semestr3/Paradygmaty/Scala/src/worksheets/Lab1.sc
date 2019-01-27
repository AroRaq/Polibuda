def sum(xs: List[Double]) : Double = {
  if (xs == Nil)
    0
  else
    xs.head + sum(xs.tail);
}

def larger(args: (List[Int], Int)) : List[Int] = {
  if (args._1 == Nil)
    Nil
  else if (args._1.head > args._2)
    args._1.head :: larger((args._1.tail, args._2))
  else larger(args._1.tail, args._2)
}

def different[A](args: (List[A], A)) : Int = {
  if (args._1 == Nil)
    0
  else (if (args._2 != args._1.head) 1 else 0) + different((args._1.tail, args._2));
}

sum(Nil)
sum(1.5 :: 4.6 :: 0.2 :: -15.0 :: Nil)

larger(Nil, 1)
larger(1 :: 2 :: 3 :: 4 :: 5 :: 6 :: Nil, 3)
larger(4 :: 5 :: 10 :: 1 :: 11 :: 3 :: Nil, 4)

different('a' :: 'b' :: 'c' :: 'a' :: Nil, 'a')
different(1 :: 2 :: 1 :: 4 :: 5 :: 4 :: 1 :: Nil, 1)
different(Nil, 1)
