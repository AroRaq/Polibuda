let rec sum xs = 
  if xs = [] then
    0.0
  else
    List.hd xs +. sum(List.tl xs);;


let rec napis (s, xs) = 
  if xs = [] then
    ""
  else
    List.hd xs ^ (if List.tl xs = [] then "" else s ^ napis (s, List.tl xs));; 


let isDescending (a, b, c) = 
  a > b && b > c;;




sum [];;
sum(1.5 :: 4.6 :: 0.2 :: -15.0 :: []);;


napis (", ", "ab" :: "cd" :: "ef" :: []);;
napis (", ", []);;


isDescending (1, 2, 3);;
isDescending (5, 3, 1);;
isDescending (1, 4, 2);;



