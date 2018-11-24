type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;
type int_tree = int bt;;

let rec contains tree el = 
  match tree with
  Empty -> false
  | Node(v, l, r) -> el = v || if el < v then contains l el else contains r el;;

let rec sum_tree tree = 
  match tree with 
  Empty -> 0
  | Node(v, l, r) -> v + sum_tree l + sum_tree r;;

let rec prod_tree tree = 
  match tree with 
  Empty -> 1
  | Node(v, l, r) -> v * prod_tree l * prod_tree r;;


let tt = Node(5,
  Node(3,
      Node(1,
          Empty,
          Empty
          ),
      Empty
      ),
  Node(8,
      Node(6,
          Empty,
          Node(7,
                Empty,
                Empty
              )
          ),
      Empty
      )
    );;


let rec range a b =
  if a > b then []
  else a :: range (a+1) b;;

List.filter (contains tt) (range 1 10);;
sum_tree tt;;
prod_tree tt;;
