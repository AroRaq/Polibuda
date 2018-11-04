let rec f x = f x;;

type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
type 'a graph = Graph of ('a -> 'a list);;

let breadthBT root =
  let rec f thisLevel nextLevel = 
    match (thisLevel, nextLevel) with
    ([], []) -> []
    | ([], nextLevel) -> f nextLevel []
    | (Node(v, l, r)::t, _) -> v :: f t (l::r::nextLevel)
    | (Empty::t, _) -> f t nextLevel
  in f [root] [];;

let scWew root = 
  let rec f n level = 
    match n with 
    Empty -> 0
    | Node(v, l, r) -> level + f l (level + 1) + f r (level + 1)
  in f root 0;;

  
let scZew root = 
  let rec f n level = 
    match n with 
    Empty -> level
    | Node(v, l, r) -> f l (level + 1) + f r (level + 1)
  in f root 0;;

let depthSearch (Graph g) start =
  let rec f neighbours visited = 
    match (neighbours, stack) with
    ([], []) -> []
    | ([], h::t) -> f (g h) (h::visited) t
    | (h::t)

let tt = Node(1,
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
                );;

let g1 = Graph
(function
0 -> [3]
| 1 -> [0;2;4]
| 2 -> [1]
| 3 -> []
| 4 -> [0;2]
| n -> failwith ("Graph g: node "^string_of_int n^" doesn't exist")
);;

depthSearch g1 4;;

breadthBT tt;;