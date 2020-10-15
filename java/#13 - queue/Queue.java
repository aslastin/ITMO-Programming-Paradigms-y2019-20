package queue;

import java.util.function.Function;
import java.util.function.Predicate;

//IM: size' = size /\ ∀i = 1 ... size': a[i]' = a[i]

//I: size >= 0 /\ ∀i = 1 ... size: a[i] != null
public interface Queue extends CopiableQueue {
    //PRE: size > 0
    //POST: ℝ = a[1] /\ size' = size - 1 /\ ∀i = 1 ... size': a[i]' = a[i + 1]
    Object dequeue();

    //PRE: element != null
    //POST: size' = size + 1 /\ ∀i = 1 ... size: a[i]' = a[i] /\ a[size'] = element
    void enqueue(Object element);

    //POST: size' = 0
    void clear();

    //POST: IM /\ R = size' > 0
    boolean isEmpty();

    //POST: IM /\  R = size'
    int size();

    //PRE: size > 0
    //POST: IM /\ R = a[1]'
    Object element();

    //POST: IM /\ /\ R.size = size' /\ ∀i = 1 ... size': R[i] = a[i]'
    Object[] toArray();

    //PRE: p != null
    //POST: IM /\ R.size >= 0 /\ R.size <= size' /\
    //      /\ R.size = k /\ I = (I1 < I2 < ... < Ik) /\ I1 >= 0 /\ Ik <= size' /\
    //      /\ ∀Ix (x = [1 : k]): p(a[Ix]) /\ R[x] = a[Ix] /\ ∀i != Ix (x = [1 : k]): !p(a[i])
    Queue filter(final Predicate<Object> p);

    // :NOTE: f(a[i]) != null
    //PRE: f != null
    //POST: IM /\ R.size = size' /\ ∀i = 1 ... size': R[i] = f(a[i]')
    Queue map(final Function<Object, Object> f);

    //PRE: p != null
    //POST: size' >= 0 /\ size' <= size /\
    //      /\ size' = k /\ I = (I1 < I2 < ... < Ik) /\ I1 >= 0 /\ Ik <= size /\
    //      /\ ∀Ix (x = [1 : k]): !p(a[Ix]) /\ a'[x] = a[Ix] /\ ∀i != Ix (x = [1 : k]): p(a[i])
    void removeIf(Predicate<Object> p);

    //PRE: p != null
    //POST: size' >= 0 /\ size' <= size /\
    //      /\ size' = k /\ I = (I1 < I2 < ... < Ik) /\ I1 >= 0 /\ Ik <= size /\
    //      /\ ∀Ix (x = [1 : k]): p(a[Ix]) /\ a'[x] = a[Ix] /\ ∀i != Ix (x = [1 : k]): !p(a[i])
    void retainIf(Predicate<Object> p);

    //PRE: p != null
    //POST: size' >= 0 /\ size' <= size /\ I >= 0 /\ I <= size + 1 /\
    //      ∀j = [1 : I): p(a[j]) /\ (I <= size) -> !p(a[I]) /\  ∀x = [1 : size - I + 1]): a'[x] = a[I + x - 1] /\ size' = size - I + 1
    void dropWhile(Predicate<Object> p);

    //PRE: p != null
    //POST: size' >= 0 /\ size' <= size /\ I >= 0 /\ I <= size + 1 /\
    //      ∀j = [1 : I): p(a[j]) /\ a'[j] = a[j] /\ (I <= size) -> !p(a[i]) /\ size' = I - 1
    void takeWhile(Predicate<Object> p);
}