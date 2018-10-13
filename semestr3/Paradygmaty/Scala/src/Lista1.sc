def flatten[A](xs: List[List[A]]) : List[A] = {
  if (xs.isEmpty)
    Nil
  else
    xs.head ::: flatten(xs.tail)
}

def count[A](l: List[A], x: A) : Int = {
  if (l.isEmpty)
    0
  else if (l.head == x)
    count(l.tail, x) + 1
  else
    count(l.tail, x)
}

def replicate[A](x: A, n: Int) : List[A] = {
  if (n == 0)
    Nil
  else
    x :: replicate(x, n-1)
}

def sqrList(xs: List[Int]) : List[Int] = {
  if (xs.isEmpty)
    Nil
  else
    xs.head * xs.head :: sqrList(xs.tail)
}

def palindrome[A](xs: List[A]) : Boolean = {
  xs.isEmpty || xs.reverse == xs
}

def listLength[A](xs: List[A]) : Int = {
  if (!xs.isEmpty)
    listLength(xs.tail) + 1
  else
    0
}


val tab1 = 1 :: 2 :: 3 :: -1 :: 3 :: 2 :: 1 :: Nil
val tab2 = List(1 :: 2 :: 3 :: Nil, 4 :: 5 :: Nil)

flatten(tab2)
count(tab1, 1)
replicate("la", 3)
sqrList(tab1)
//reverse(tab1)
listLength(tab1)

palindrome(tab1)