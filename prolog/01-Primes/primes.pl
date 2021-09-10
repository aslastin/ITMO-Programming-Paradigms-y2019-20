init(MAX_N) :- strike(2, MAX_N, 4), init(3, MAX_N).
init(N, MAX_N) :- N * N > MAX_N, !.
init(N, MAX_N) :- composite(N), next(N, MAX_N), !.
init(N, MAX_N) :- S is N * N, strike(N, MAX_N, S), next(N, MAX_N).
next(N, MAX_N) :- N1 is N + 2, init(N1, MAX_N).

strike(_, MAX_N, S) :- S > MAX_N, !.
strike(N, MAX_N, S) :-
		assert(composite(S)),
		SN is S + N,
		strike(N, MAX_N, SN).
		
prime(N) :-  N > 1, \+ composite(N).

check_div([H]).
check_div([H1, H2 | _]) :- H1 =< H2.

prime_divisors(1, []) :- !.
prime_divisors(N, [H | T]) :-
		number(H), !, check_div([H | T]),
		prime(H),
		prime_divisors(R, T),
		N is R * H.

prime_divisors(N, Divisors) :- number(N),  prime_divisors(N, 2, Divisors), !.

prime_divisors(1, _, []).
prime_divisors(N, _, [N]) :- prime(N).
prime_divisors(N, D, [D | T]) :- 
		0 is mod(N, D), !,
		ND is div(N, D),
		prime_divisors(ND, D, T).
prime_divisors(N, D, Divisors) :- 
		D1 is D + 1,
		prime_divisors(N, D1, Divisors).


merge(R, [], R) :- !.
merge([], R, R).

merge([H1 | T1], [H2 | T2], [H1 | T]) :- 
		H1 < H2, merge(T1, [H2 | T2], T); 
		H1 is H2, merge(T1, T2, T).
merge([H1 | T1], [H2 | T2], [H2 | T]) :- 
		H1 > H2, merge([H1 | T1], T2, T).
		
lcm(A, B, LCM) :-
		prime_divisors(A, DA),
		prime_divisors(B, DB),
		merge(DA, DB, R),
		prime_divisors(LCM, R).
		
		



		


		
		