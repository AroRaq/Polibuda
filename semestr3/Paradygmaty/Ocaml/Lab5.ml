type  'a  nlist  =  Koniec|  Element  of  'a  *  ('a  nlist);;
type  'a  llist  =  LKoniec  |  LElement  of  'a  *  (unit  ->  'a  llist);;

let rec toWeirdList = function
[] -> Koniec
| h::t -> Element(h, toWeirdList t);;

let rec toLazyList = function
[] -> LKoniec
| h::t -> LElement(h, function () -> toLazyList t);;

let rec toList = function
LKoniec -> []
| LElement(x, xf) -> x :: (toList (xf()));;

let rec toList2 = function
Koniec -> []
| Element(x, xnext) -> x :: (toList2 xnext);;


let lpodziel xs = 
  let rec f el take = 
    match el with 
    LKoniec -> LKoniec
    | LElement(x, xf) -> if take then LElement(x, function()->f (xf()) false) else f (xf()) true
  in match xs with 
  LKoniec -> (LKoniec, LKoniec)
  | LElement(_, _) as el -> (f el true, f el false);;

  
let podziel xs = 
  let rec f el take = 
    match el with 
    Koniec -> Koniec
    | Element(x, xnext) -> if take then Element(x, f xnext false) else f xnext true
  in match xs with 
  Koniec -> (Koniec, Koniec)
  | Element(_, _) as el -> (f el true, f el false);;

let k = lpodziel(toLazyList (1::2::3::4::5::[]));;
toList (fst k);;
toList (snd k);;

let l = podziel(toWeirdList(1::2::3::4::5::6::7::[]));;
toList2 (fst l);;
toList2 (snd l);;