% splay_tree(Key, Value, Count, Left, Right)
% Top-Down Realisation
% Help methods
null(splay_tree(null, null, 0, null, null)).
count(Left, Right, C) :- get_count(Left, C1), get_count(Right, C2), C is C1 + C2 + 1.

get_left(splay_tree(_, _, _, Left, _), Left).
get_right(splay_tree(_, _, _, _, Right), Right).
get_count(splay_tree(_, _, C, _, _), C).
get_value(splay_tree(_, V, _, _, _), V).
get_key(splay_tree(K, _, _, _, _), K).

set_left(splay_tree(K, V, _, _, Right), Left, splay_tree(K, V, C, Left, Right)) :- count(Left, Right, C).
set_right(splay_tree(K, V, _, Left, _), Right, splay_tree(K, V, C, Left, Right)) :- count(Left, Right, C).

rotate_left(T, R) :-
	get_right(T, Right),
  get_left(Right, Left),
	set_right(T, Left, NT),
	set_left(Right, NT, R).

rotate_right(T, R) :-
	get_left(T, Left),
	get_right(Left, Right),
	set_left(T, Right, NT),
	set_right(Left, NT, R).

break_left(T, NT, L) :- get_right(T, NT), null(Z), set_right(T, Z, L).
break_right(T, NT, R) :- get_left(T, NT), null(Z), set_left(T, Z, R).

splay(T, K, NT) :- 
	splay(T, K, L, R, Rest),
	set_left(Rest, L, T1),
	set_right(T1, R, NT).  

splay(T, K, L, R, T) :- 
	get_key(T, Key), Key == K, !,
	get_left(T, L), get_right(T, R).

splay(T, K, L, R, Rest) :-
	get_key(T, Key1), Key1 > K,
	get_left(T, Left), null(Z), Z \== Left, get_key(Left, Key2), Key2 > K, !, 
	rotate_right(T, T1),
	break_right(T1, NT, NR),
	splay(NT, K, L, Right, Rest),
	set_left(NR, Right, R).

splay(T, K, L, R, Rest) :-
	get_key(T, Key), Key > K, !,
	break_right(T, NT, NR),
	splay(NT, K, L, Right, Rest),
	set_left(NR, Right, R).

splay(T, K, L, R, Rest) :-
	get_key(T, Key1), Key1 < K,
	get_right(T, Right), null(Z), Z \== Right, get_key(Right, Key2), Key2 < K, !, 
	rotate_left(T, T1),
	break_left(T1, NT, NL),
	splay(NT, K, Left, R, Rest),
	set_right(NL, Left, L).

splay(T, K, L, R, Rest) :-
	get_key(T, Key), Key < K, !,
	break_left(T, NT, NL),
	splay(NT, K, Left, R, Rest),
	set_right(NL, Left, L).

find(TreeMap, Key, T) :- find_(TreeMap, Key, K), splay(TreeMap, K, T).
	
find_(splay_tree(K, _, _, Left, _), Key, Key_) :-
	Key < K, null(Z), Left \== Z, !,
	find_(Left, Key, Key_).

find_(splay_tree(K, _, _, _, Right), Key, Key_) :-
	Key > K, null(Z), Right \== Z, !,
	find_(Right, Key, Key_).

find_(splay_tree(K, _, _, _, _), _, K).

split(Z, _, Z, Z) :- null(Z), !.
split(T, K, T1, T2) :-
	find(T, K, NT), 
	get_key(NT, Key),
	split(Key, K, NT, T1, T2).

split(Key, K, NT, T1, T2) :- Key is K, null(Z), get_left(NT, T1), get_right(NT, T2), !.
split(Key, K, NT, T1, T2) :- Key < K, null(Z), get_right(NT, T2), set_right(NT, Z, T1), !.
split(Key, K, NT, T1, T2) :- Key > K, null(Z), get_left(NT, T1), set_left(NT, Z, T2), !.

merge(Z, T2, T2) :- null(Z), !.
merge(T1, Z, T1) :- null(Z), !.
merge(T1, T2, T) :-
	get_key(T1, K),
	find(T2, K, NT),
	set_left(NT, T1, T).


map_get(TreeMap, Key, Value) :-
	number(Key),
	find(TreeMap, Key, T),
	get_key(T, K), K = Key, 
	get_value(T, V), V = Value.

map_put(TreeMap, Key, Value, Result) :-
	number(Key),
	split(TreeMap, Key, T1, T2),
	count(T1, T2, C),
	Result = splay_tree(Key, Value, C, T1, T2).

map_remove(TreeMap, Key, Result) :-
	number(Key),
	find(TreeMap, Key, T),
	map_remove(Key, T, TreeMap, Result). 

map_remove(K, T, TreeMap, TreeMap) :- get_key(T, Key), Key \== K, !. 
map_remove(K, T, TreeMap, Result) :- get_left(T, Left), get_right(T, Right), merge(Left, Right, Result).

map_build(ListMap, TreeMap) :- map_build_greater(ListMap, TreeMap), !.
map_build(ListMap, TreeMap) :- map_build_less(ListMap, TreeMap), !.
map_build(ListMap, TreeMap) :- map_build_put(ListMap, TreeMap).

map_build_put([], Z) :- null(Z), !.
map_build_put([(K, V) | T], TreeMap) :- map_build_put(T, Tree), map_put(Tree, K, V, TreeMap).
	
build([(K, V)], splay_tree(K, V, 1, Left, Right)) :- number(K), null(Left), null(Right).	

map_build_less(ListMap, TreeMap) :- build(ListMap, TreeMap), !.
map_build_less([(K1, V1), (K2, V2) | T], splay_tree(K1, V1, Count, Left, Right)) :- 
	number(K1), number(K2),
	K1 < K2,
	map_build_less([(K2, V2) | T], Right),
	null(Left),
	count(Left, Right, Count).

map_build_greater(ListMap, TreeMap) :- build(ListMap, TreeMap), !.
map_build_greater([(K1, V1), (K2, V2) | T], splay_tree(K1, V1, Count, Left, Right)) :- 
	number(K1), number(K2),
	K1 > K2,
	map_build_greater([(K2, V2) | T], Left),
	null(Right),
	count(Left, Right, Count).

map_submapSize(Map, FromKey, ToKey, Size) :- 
	split(Map, FromKey, T1, _),
	get_count(T1, C1),
	split(Map, ToKey, T2, _),
	get_count(T2, C2),
	Size is C2 - C1.
	
