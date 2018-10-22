let rec fibb n = 
  if n = 0 then 0
  else if n = 1 then 1
  else (fibb (n-1)) + (fibb (n-2));;


let fibb2 n = 
  let rec fibbIter (n, curr, last) = 
    if n = 1 then curr
    else fibbIter (n-1, curr+last, curr)
  in fibbIter(n, 1, 0);;

  
let rec root3 (a, dx, x) = 
  if abs_float(x*.x*.x -. a) < dx *. abs_float(a) then x
  else root3(a, dx, x +. (a/.(x*.x)-.x)/.3.);;


let rec intSegment (xs, ys) = 
  if xs = [] then true
  else List.hd xs = List.hd ys && intSegment(List.tl xs, List.tl ys);;


let rec replaceNth (xs, n, rep) = 
  if n = 0 then rep :: List.tl xs
  else List.hd xs :: replaceNth(List.tl xs, n-1, rep);;

  
let a = 20;;
fibb a;;
fibb2 a;;

let b = 9.;;
root3 (b, 10. ** -15., if b > 1. then b/.3. else b);;

intSegment(1 :: 2 :: [], 1 :: 2 :: 3 :: 4 :: []);;
intSegment([], 1 :: 2 :: []);;
intSegment(1 :: [], 2 :: 1 :: []);;

replaceNth(1 :: 2 :: 3 :: 4 :: 5 :: 6 :: [], 4, 10);;