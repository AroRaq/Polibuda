let rec flatten xss = 
  if List.length xss = 0 then
    []
  else
    List.hd xss @ flatten (List.tl xss);;


let rec count (a, xs) = 
  if xs = [] then
    0
  else
    count (a, List.tl xs) + (if List.hd xs = a then 1 else 0);;


let rec replicate (a, b) = 
  if b = 0 then 
    []
  else 
    a :: replicate (a, b-1);;


let rec sqrList xs = 
  if xs = [] then 
    []
  else
    List.hd xs * List.hd xs :: sqrList (List.tl xs);;


let rec palindrome xs = 
  (xs = [] || List.length xs = 1) || List.hd xs = List.hd (List.rev xs) && palindrome (List.tl (List.rev (List.tl xs)));;


let rec listLength xs = 
  if xs = [] then 
    0
  else
    1 + listLength (List.tl xs);;


let tab = [[1; 2]; [3; 4; 5]];;
flatten tab;;
let tab1 = 1 :: 2 :: 3 :: 1 :: 2 :: 1 :: -1 :: [];;
let tab2 = 'a' :: 'b' :: 'b' :: 'a' :: [];;
count (1, tab1);;
count ('a', tab2);;
sqrList tab1;;
palindrome tab2;;
listLength tab;;