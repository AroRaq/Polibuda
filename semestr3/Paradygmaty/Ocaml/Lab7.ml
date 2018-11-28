module type BST_FUN =
sig
  type t
  val create: unit -> t
  val push: int * t -> t
  val remove: int * t -> t
  val find: int * t -> bool
  val getPreOrder: t -> int list
  val getPostOrder: t -> int list
  val getInOrder: t -> int list
end;;


module BinarySearchTree : BST_FUN = 
struct
  type t = TEmpty | TNode of int * t * t
  
  let rec successor = function
  TEmpty -> failwith "błąd implementacji"
  | TNode(v, TEmpty, TEmpty) -> v
  | TNode(_, TEmpty, r) -> successor r
  | TNode(_, l, _) -> successor l

  let create = function() -> TEmpty

  let rec push (el, tree) = match tree with
    TEmpty -> TNode(el, TEmpty, TEmpty)
    | TNode(v, left, right) -> if (el < v) then TNode(v, push (el, left), right) else TNode(v, left, push(el, right))

  let rec remove (el, tree) = match tree with
    TEmpty -> TEmpty
    | TNode(v, l, r) -> 
      if (v = el) then
        match (l, r) with
        (TEmpty, TEmpty) -> TEmpty
        | (TEmpty, r) when r <> TEmpty -> r
        | (l, TEmpty) when l <> TEmpty -> l
        | (l, r) -> let s = (successor r) in TNode(s, l, remove (s, r))
      else if (el < v) then TNode(v, remove (el, l), r)
      else TNode(v, l, remove (el, r))

  let rec find (el, tree) = match tree with
    TEmpty -> false
    | TNode(v, left, right) -> if (el = v) then true else 
        if (el < v) then find(el, left) else find(el, right)

  let rec getPreOrder tree = match tree with
    TEmpty -> []
    | TNode(v, left, right) -> v :: (getPreOrder left) @ (getPreOrder right)

  let rec getPostOrder tree = match tree with
    TEmpty -> []
    | TNode(v, left, right) -> (getPostOrder left) @ (getPostOrder right) @ [v]
    
  let rec getInOrder tree = match tree with
    TEmpty -> []
    | TNode(v, left, right) -> (getInOrder left) @ [v] @ (getInOrder right)
end;;



let t = BinarySearchTree.create();;
let t = BinarySearchTree.push(6, t);;
let t = BinarySearchTree.push(2, t);;
let t = BinarySearchTree.push(1, t);;
let t = BinarySearchTree.push(9, t);;
let t = BinarySearchTree.push(8, t);;
let t = BinarySearchTree.push(15, t);;
let t = BinarySearchTree.push(13, t);;
let t = BinarySearchTree.push(11, t);;
let t = BinarySearchTree.push(18, t);;

BinarySearchTree.getInOrder(t);;

let t = BinarySearchTree.remove(9, t);;
BinarySearchTree.getInOrder(t);;

BinarySearchTree.find(13, t);;
BinarySearchTree.find(100, t);;

BinarySearchTree.getPreOrder(t);;
BinarySearchTree.getPostOrder(t);;
