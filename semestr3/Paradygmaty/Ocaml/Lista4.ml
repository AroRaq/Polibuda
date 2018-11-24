type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
type 'a graph = Graph of ('a -> 'a list);;

let bfs_BT root =
  let rec f = function
    [] -> []
    | Empty::t -> f t
    | Node(v, l, r)::t -> v :: f (t @ [l; r])
  in f [root];;

let intPathLength root = 
  let rec f level = function
    Empty -> 0
    | Node(_, l, r) -> level + f (level + 1) l + f (level + 1) r
  in f 0 root;;

let extPathLength root = 
  let rec f level = function
    Empty -> level
    | Node(_, l, r) -> f (level + 1) l + f (level + 1) r
  in f 0 root;;

let dfs_G (Graph g) start =
  let rec f visited = function
    [] -> []
    | h::t -> if List.mem h visited then f visited t
              else h :: f (h::visited) ((g h) @ t)
  in f [] [start];;



let f x = Not_found
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

let g = Graph (function
  0 -> [3]
  | 1 -> [0;2;4]
  | 2 -> [1]
  | 3 -> []
  | 4 -> [0;2]
  | n -> failwith ("Graph g: node "^string_of_int n^" doesn't exist") );;