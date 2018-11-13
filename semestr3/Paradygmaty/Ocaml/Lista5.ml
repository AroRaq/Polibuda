type 'a llist = LNil | LCons of 'a * (unit -> 'a llist);;
type 'a lBT = LEmpty | LNode of 'a * (unit ->'a lBT) * (unit -> 'a lBT);;


let rec (@$) ll1 ll2 = 
  match ll1 with
  LNil -> ll2
  | LCons(x, xf) -> LCons(x, function() -> (xf()) @$ ll2);;

let rec toLazyList = function
  [] -> LNil
  | h::t -> LCons(h, function () -> toLazyList t);;

let rec toList = function
  LNil -> []
  | LCons(x, xf) -> x :: (toList (xf()));;

let rec lrepeat k xs =
  match k with
  0 -> LNil
  | k -> xs @$ lrepeat (k-1) xs;;

let rec ltake = function
  (0, _) -> []
  | (_, LNil) -> []
  | (n, LCons(x,xf)) -> x::ltake(n-1, xf());;



let lfib = 
  let rec f a b = LCons(a, function() -> f b (a+b))
  in f 1 1;;

let lBFS root = 
  let rec f = function
    [] -> LNil
    | LEmpty::t -> f t
    | LNode(v, lf, rf)::t -> LCons(v, function() -> f (t @ [lf(); rf()]))
  in f [root];;

let rec lTree n = LNode(n, (function() -> lTree (n*2)), function() -> lTree (n*2+1));;


let x = [1;2;3;4;5;6;7];;
toList (lrepeat 5 (toLazyList x));;
ltake (10, lBFS (lTree 1));;