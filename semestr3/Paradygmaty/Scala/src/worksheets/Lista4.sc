sealed trait BT[+A]
case object Empty extends BT[Nothing]
case class Node[+A](elem:A, left:BT[A], right:BT[A]) extends BT[A]

sealed trait Graphs[A]
case class Graph[A](succ: A=>List[A]) extends Graphs[A]


def bfs_BT[A](root: BT[A]) : List[A] = {
  def f(queue: List[BT[A]]) : List[A] = {
    queue match {
      case Nil => Nil
      case Node(v, l, r)::t => v :: f(t ++ List(l, r))
      case Empty::t => f(t)
    }
  }
  f(List(root));
}

def intPathLength[A](root: BT[A]) : Int = {
  def f(node: BT[A], level: Int) : Int = {
    node match {
      case Empty => 0
      case Node(_, l, r) => level + f(l, level+1) + f(r, level+1)
    }
  }
  f(root, 0)
}

def extPathLength[A](root: BT[A]) : Int = {
  def f(node: BT[A], level: Int) : Int = {
    node match {
      case Empty => level
      case Node(_, l, r) => f(l, level+1) + f(r, level+1)
    }
  }
  f(root, 0)
}

def dfs_G[A](graph: Graph[A], start: A) : List[A] = {
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

