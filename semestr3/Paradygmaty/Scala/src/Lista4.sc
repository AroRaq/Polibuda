sealed trait BT[+A]
case object Empty extends BT[Nothing]
case class Node[+A](elem:A, left:BT[A], right:BT[A]) extends BT[A]

sealed trait Graphs[A]
case class Graph[A](succ: A=>List[A]) extends Graphs[A]


def breadthBT[A](root: BT[A]) : List[A] = {
  def f(thisLevel: List[BT[A]], nextLevel: List[BT[A]]) : List[A] = {
    (thisLevel, nextLevel) match {
      case (Nil, Nil) => Nil
      case (Nil, nextLevel) => f(nextLevel, Nil)
      case (Node(v, l, r)::t, nextLevel) => v::f(t, l::r::nextLevel)
      case (Empty::t, nextLevel) => f(t, nextLevel)
    }
  }
  f(List(root), Nil);
}

def intPath[A](root: BT[A]) : Int = {
  def f(node: BT[A], level: Int) : Int = {
    node match {
      case Empty => 0
      case Node(_, l, r) => level + f(l, level+1) + f(r, level+1)
    }
  }
  f(root, 0)
}

def excPath[A](root: BT[A]) : Int = {
  def f(node: BT[A], level: Int) : Int = {
    node match {
      case Empty => level
      case Node(_, l, r) => f(l, level+1) + f(r, level+1)
    }
  }
  f(root, 0)
}

def depthSearch[A](graph: Graph[A], start: A) : List[A] = {
  def search(visited: List[A], stack: List[A]) : List[A] = {
    stack match {
      case Nil => Nil
      case h :: t =>
        if (visited.contains(h)) search(visited, t)
        else h :: search(h :: visited, graph.succ(h) ++ stack)
    }
  }
  search(Nil, List(start))
}

val tt = Node(1,
  Node(2,
    Node(4,
      Empty,
      Empty
    ),
    Empty
  ),
  Node(3,
    Node(5,
      Empty,
      Node(6,
        Empty,
        Empty
      )
    ),
    Empty
  )
)