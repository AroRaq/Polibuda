module type QUEUE_FUN = 
sig
  type 'a t
  exception Empty of string
  val empty: unit -> 'a t
  val enqueue: 'a * 'a t -> 'a t
  val dequeue: 'a t -> 'a t
  val first: 'a t -> 'a
  val isEmpty: 'a t -> bool
end;;



module QueueList : QUEUE_FUN = 
struct
  type 'a t = 'a list
  exception Empty of string
  let empty = function() -> []
  let enqueue(e, q) = q @ [e]
  let dequeue = function
    h::t -> t
    | [] -> []
  let first = function
    h::t -> h
    | [] -> raise (Empty "module QueueList: first")
  let isEmpty = function
    h::t -> false
    | [] -> true
end;;



module QueueTwoLists : QUEUE_FUN = 
struct 
  type 'a t = 'a list * 'a list
  exception Empty of string
  let empty = function() -> ([], [])
  let enqueue (e, q) = let (xs, ys) = q in if xs = [] then ([e], ys) else (xs, e::ys)
  let dequeue = function
    (h::[], ys) -> (List.rev ys, [])
    | (h::t, ys) -> (t, ys)
    | ([], _) -> ([], [])
  let first = function
    (h::_, _) -> h
    | ([], _) -> raise (Empty "module QueueTwoLists: first")
  let isEmpty = function
    (h::_, _) -> false
    | _ -> true
end;;











module type QUEUE_MUT =
sig
 type 'a t
 exception Empty of string
 exception Full of string
 val empty: int -> 'a t
 val enqueue: 'a * 'a t -> unit
 val dequeue: 'a t -> unit
 val first: 'a t -> 'a
 val isEmpty: 'a t -> bool
 val isFull: 'a t -> bool
end;;



module QueueCycleArray : QUEUE_MUT= 
struct
  type 'a t = {mutable tab: 'a option array; mutable f: int; mutable r: int}
  exception Empty of string
  exception Full of string
  let length q = if q.r >= q.f then q.r - q.f else q.r + Array.length q.tab - q.f
  let isFull q = length q = Array.length q.tab - 1
  let isEmpty q = q.f = q.r
  let empty n = {tab = Array.make (n+1) None; f = 0; r = 0}
  let enqueue(x, q) = 
        if isFull q then raise (Full "module QueueCycleArray: enqueue") 
        else begin
          q.tab.(q.r) <- Some(x);
          q.r <- (q.r + 1) mod (Array.length q.tab)
        end

  let dequeue q = if not (isEmpty q) then q.f <- (q.f + 1) mod (Array.length q.tab)

  let first q = if isEmpty q then raise (Empty "module QueueCycleArray: first") 
                else let Some(v) = q.tab.(q.f) in v
end;;