let rec fibb n = 
  if n = 0 then 0
  else if n = 1 then 1
  else (fibb (n-1)) + (fibb (n-2));;


let fibb2 n = 
  let rec fibbIter (n, curr, last) = 
    if n = 1 then curr
    else fibbIter (n-1, curr+last, curr)
  in fibbIter(n, 1, 0);;


let root3 (a, dx) = 
  let rec root3iter x = 
    if abs_float(x*.x*.x -. a) < dx *. abs_float(a) then x
    else root3iter(x +. (a/.(x*.x)-.x)/.3.)
  in root3iter (if a > 1. then a/.3. else a);;


let rec intSegment (xs, ys) = 
  match (xs, ys) with
  ([], _) -> true
  | (_, []) -> false
  | (h1::t1, h2::t2) -> h1 = h2 && intSegment(t1, t2);;


let rec replaceNth (xs, n, rep) = 
  match (xs, n) with
  ([], _) -> []
  | (_, 0) -> rep :: List.tl xs
  | (h::t, _) -> h :: replaceNth(t, n-1, rep);;

  
let a = 20;;
fibb a;;
fibb2 a;;

root3 (9., 1e-15);;

intSegment(1 :: 2 :: [], 1 :: 2 :: 3 :: 4 :: []);;
intSegment([], 1 :: 2 :: []);;
intSegment(1 :: [], 2 :: 1 :: []);;

replaceNth(1 :: 2 :: 3 :: 4 :: 5 :: 6 :: [], 4, 10);;