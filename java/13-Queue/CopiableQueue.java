package queue;

//IM: size' = size /\ ∀i = 1 ... size': a[i]' = a[i]
public interface CopiableQueue {
    //POST: IM /\  R.size = size' /\ i = 1 ... size': R[i] = a'[i]
    Queue makeCopy();
    Queue getInstance();
    //PRE: queue instanceof this
    //POST: size' = queue.size /\ i = 1 ... queue.size: a'[i] = queue.a[i]
    void initializeBy(final Queue queue);
}
