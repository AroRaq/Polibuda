type 'a llist = LNil | LCons of 'a * (unit -> 'a llist);;
type pawPatrol = Doggo of string * string;;

let rec toList = function
LNil -> []
| LCons(x, xf) -> x :: (toList (xf()));;






let rec insert sorted el = 
  match sorted with
  [] -> [el]
  | h::t -> if (h < el) then h::(insert t el) else el::h::t;;

let rec lrepeat xs = 
  let rec f = function
    (LNil, _) -> LNil
    | (LCons(_, yf), 0) -> lrepeat (yf())
    | (LCons(y, _) as el, n) -> LCons(y, function()->f (el, n-1))
  in match xs with
    LNil -> LNil
    | LCons(x, xf) -> f (xs, x);;

let rec repeat xs = 
  let rec f el reps = 
    match reps with
    0 -> repeat (List.tl xs)
    | reps -> el :: f el (reps-1)
  in match xs with
  h::_ -> f h h
  | [] -> [];;

insert [1;2;3;4;5;7;8;9] 6;;
insert [1;2;3;4;5;7;8;9] 60;;
insert [1;2;3;4;5;7;8;9] 0;;


toList (lrepeat (LCons(1, function()->LCons(2, function()->LCons(3, function()->LNil)))));;
repeat (1::2::3::4::5::[]);;

let rasa doggo = 
  let Doggo(rasa, _) = doggo in rasa;;

let pojazd doggo = 
  let Doggo(_, pojazd) = doggo in function() -> pojazd;;

let marshall = Doggo("dalmatynczyk", "woz strazacki");;
let chase = Doggo("owczarek niemiecki", "woz policyjny");;
let rubble = Doggo("buldog angielski", "buldozer");;
let rocky = Doggo("kundel", "zielony eko");;
let skye = Doggo("mieszaniec", "helokopter");;
let zuma = Doggo("labrador retriver", "poduszkowiec");;
let everest = Doggo("husky", "plug sniezny");;

pojazd rocky;;