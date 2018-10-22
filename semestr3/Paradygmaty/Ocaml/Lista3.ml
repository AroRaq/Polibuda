let curry3 f x y z = f (x, y, z);;
let uncurry3  f (x, y, z) = f x y z;;

let sumProd xs = List.fold_left (fun (s, p) x -> (s+x, p*x)) (0, 1) xs;;

let insertSort comp xs = 
  let rec insert sorted x = 
    if sorted = [] then [x]
    else if comp x (List.hd sorted) then List.hd sorted :: insert (List.tl sorted) x
    else x :: sorted
  in List.fold_left insert [] xs;;

let rec mergeSort comp xs = 
  (*let rec split xs' l1 l2 =
    if xs' = [] then (l1, l2)
    else split (List.tl xs') (List.hd xs' :: l2) l1
    *)
  let rec split xs' l1 l2 len = 
    if len = 1 then ([List.hd xs'], List.tl xs') else
    let a, b = split (List.tl xs') l1 l2 (len-1)
    in (List.hd xs' :: a, b) 
  and merge xs' ys' = 
    match xs', ys' with
    [], [] -> []
    | _, [] -> xs'
    | [], _ -> ys'
    | h1::t1, h2::t2 -> if comp h1 h2 then h2 :: merge xs' t2 else h1 :: merge ys' t1
  in
  match xs with
  [] -> []
  | x::[] -> [x]
  | _ ->
  let (a, b) = split xs [] [] ((List.length xs)/2) in 
  let x = mergeSort comp a
  and y = mergeSort comp b in 
  merge x y;;



insertSort (fun a b -> a > b) (123 :: 13 :: 41 :: 1234 :: 6 :: 123 :: 41 :: []);;
mergeSort (fun a b -> a > b) (123 :: 13 :: 41 :: 1234 :: 6 :: 123 :: 41 :: []);;



let rec test x = test x;;

test 5;;