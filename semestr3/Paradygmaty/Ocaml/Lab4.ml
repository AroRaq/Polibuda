
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
  Empty -> 0
  | Node(v, l, r) -> v * sum_tree l * sum_tree r;;
