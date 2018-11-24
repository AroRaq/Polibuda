type 'a llist = LNil | LCons of 'a * (unit -> 'a llist);;
type 'a lBT = LEmpty | LNode of 'a * (unit ->'a lBT) * (unit -> 'a lBT);;


let rec toLazyList = function
  [] -> LNil
  | h::t -> LCons(h, function () -> toLazyList t);;

let rec toList = function
  LNil -> []
  | LCons(x, xf) -> x :: (toList (xf()));;

let rec ltake = function
  (0, _) -> []
  | (_, LNil) -> []
  | (n, LCons(x,xf)) -> x::ltake(n-1, xf());;




  

let rec lrepeat k xs =
  let rec f = function
    (LNil, _) -> LNil
    | (LCons(_, yf), 0) -> f (yf(), k)
    | (LCons(y, _) as el, n) -> LCons(y, fun() -> f (el, n-1))
  in f (xs, k);;
  
let lfib = 
  let rec f a b = LCons(a, function() -> f b (a+b))
  in f 0 1;;

let lBFS root = 
  let rec f = function
    [] -> LNil
    | LEmpty::t -> f t
    | LNode(v, lf, rf)::t -> LCons(v, function() -> f (t @ [lf(); rf()]))
  in f [root];;

let rec lTree n = LNode(n, (function() -> lTree (n*2)), function() -> lTree (n*2+1));;





let x = [1;2;3;4;5;6;7];;
toList (lrepeat 3 (toLazyList x));;

ltake (10, lfib);;

ltake (10, lBFS (lTree 1));;