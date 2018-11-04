let curry3 f x y z = f (x, y, z);;
let uncurry3  f (x, y, z) = f x y z;;

let sumProd xs = List.fold_left (fun (s, p) x -> (s+x, p*x)) (0, 1) xs;;

let insertSort comp xs = 
  let rec insert sorted x = 
    if sorted = [] then [x]
    else if comp x (List.hd sorted) then x :: sorted
    else List.hd sorted :: insert (List.tl sorted) x
  in List.fold_left insert [] xs;;

let rec mergeSort comp xs = 
  let rec split xs' len =                           
    if len = 1 then ([List.hd xs'], List.tl xs') else
    let (a, b) = split (List.tl xs') (len-1)
    in (List.hd xs' :: a, b)
  and merge xs' ys' = 
    match xs', ys' with
    [], [] -> []
    | _, [] -> xs'
    | [], _ -> ys'
    | h1::t1, h2::t2 -> if comp h1 h2 then h1 :: merge t1 ys' else h2 :: merge xs' t2
  in
  match xs with
  [] -> []
  | x::[] -> [x]
  | _ ->
  let (a, b) = split xs ((List.length xs)/2) 
  in merge (mergeSort comp a) (mergeSort comp b);;

sumProd [1; 2; 3; 4; 5];;

insertSort (fun a b -> a > b) [123; 13; 41; 1234; 6; 123; 41];;
mergeSort (fun a b -> a >= b) [123; 13; 41; 1234; 6; 123; 41];;


insertSort (fun a b -> fst a > fst b) 
    [(123, 0); (13, 1); (41, 2); (1234, 3); (13, 4); (123, 5); (6, 6)];;
mergeSort (fun a b -> fst a >= fst b) 
    [(123, 0); (13, 1); (41, 2); (1234, 3); (13, 4); (123, 5); (6, 6)];;