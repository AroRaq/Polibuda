let rec filterListOfLists pred xss = 
  match xss with 
  [] -> []
  | h::t -> if List.for_all pred h then h::filterListOfLists pred t else filterListOfLists pred t;;


let binToDec = fun bs -> List.fold_left (fun x y -> 2*x + y) 0 bs;;

let isTriangle (x1, y1, x2, y2, x3, y3) = 
  let ab = (x2-.x1, y2-.y1) 
  and ac = (x3-.x1, y3-.y1) 
  in abs_float ((fst ab) *. (snd ac) -. (fst ac *. snd ab)) <> 0.;;



filterListOfLists (fun x -> x != 3) [[1; 2; 3];[3; 4];[5; 6]];;

binToDec [1; 1; 1; 1; 1; 0];;

isTriangle (0., 0., 1., 1., 0., 1.);;
isTriangle (0., 0., 1., 0., -1., 0.);;

(*let rec filterListOfLists pred xss = 
  let rec correct xs = 
    if xs = [] then true
    else pred (List.hd xs) && correct (List.tl xs)
  in match xss with 
  [] -> []
  | h::t -> if correct h then h::filterListOfLists pred t else filterListOfLists pred t;;

*)