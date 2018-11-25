module type QUEUE_FUN = 
sig
  type 'a t
  exception Empty of string
  val empty: unit -> 'a t
  val enqueue: 'a * 'a t -> 'a t
  val dequeue: 'a t -> 'a t
  val first: 'a t -> 'a
  val isEmpty: 'a t -> bool
  val toList: 'a t -> 'a list
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
  let toList q = q 
end;;


module QueueTwoLists1 = 
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
  let toList q = let (xs, ys) = q in xs@(List.rev ys)
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
  let toList q = let (xs, ys) = q in xs@(List.rev ys)
end;;


module type QUEUE_MUT =
sig
 type 'a t
 (* The type of queues containing elements of type ['a]. *)
 exception Empty of string
 (* Raised when [first q] is applied to an empty queue [q]. *)
 exception Full of string
 (* Raised when [enqueue(x,q)] is applied to a full queue [q]. *)
 val empty: int -> 'a t
 (* [empty n] returns a new queue of length [n], initially empty. *)
 val enqueue: 'a * 'a t -> unit
 (* [enqueue (x,q)] adds the element [x] at the end of a queue [q]. *)
 val dequeue: 'a t -> unit
 (* [dequeue q] removes the first element in queue [q] *)
 val first: 'a t -> 'a
 (* [first q] returns the first element in queue [q] without removing
 it from the queue, or raises [Empty] if the queue is empty. *)
 val isEmpty: 'a t -> bool
 (* [isEmpty q] returns [true] if queue [q] is empty,
 otherwise returns [false]. *)
 val isFull: 'a t -> bool
 (* [isFull q] returns [true] if queue [q] is full,
 otherwise returns [false]. *)
end;;


module QueueCycleArray : QUEUE_MUT= 
struct
  type 'a t = {mutable tab: 'a option array; mutable f: int; mutable r: int}
  exception Empty of string
  exception Full of string
  let length q = if q.f < q.r then q.r - q.f else Array.length q.tab - q.f + q.r
  let isFull q = length q = Array.length q.tab - 1
  let isEmpty q = q.r = q.f
  let empty n = {tab = Array.make (n+1) None; f = 0; r = 0}
  let enqueue(x, q) = if isFull q then raise (Full "module QueueCycleArray: enqueue")
    else begin 
      if q.r = Array.length q.tab then q.r <- 0;
      q.tab.(q.r) <- Some x;
      q.r <- q.r+1
    end
  let dequeue q = if not (isEmpty q) then 
    q.r <- if q.r <> 0 then q.r-1 
           else Array.length q.tab - 1
  let first q = if isEmpty q then raise (Empty "module QueueCycleArray: first")
    else match q.tab.(q.f) with
    Some e -> e
    | None -> failwith "module QueueCycleArray: first (implementation error)"
end;;

let que = QueueTwoLists.empty();;
let que = QueueTwoLists.enqueue (1, que);;
let que = QueueTwoLists.enqueue (2, que);;
QueueTwoLists.toList que;;
QueueTwoLists.first que;;
let que = QueueTwoLists.dequeue que;;
let que = QueueTwoLists.dequeue que;;
let que = QueueTwoLists.dequeue que;;
QueueTwoLists.toList que;;