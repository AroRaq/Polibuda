
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


type expression = Value of int 
                | Plus of expression * expression
                | Negative of expression;;

let rec calculate expr = 
  match expr with
  Value(x) -> x
  | Plus(x, y) -> (calculate x) + (calculate y)
  | Negative(x) -> -(calculate x);;

calculate (Plus(Negative(Value(10)), Value(100)));;