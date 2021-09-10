package queue;


import java.util.function.Function;
import java.util.function.Predicate;


public abstract class AbstractQueue implements Queue {
    protected final static int INIT_LENGTH = 8;
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object dequeue() {
        assert size > 0;
        size--;
        return doDequeue();
    }

    protected abstract Object doDequeue();

    @Override
    public void enqueue(final Object element) {
        assert element != null;
        size++;
        doEnqueue(element);
    }

    protected abstract void doEnqueue(Object element);

    @Override
    public Object element() {
        assert size > 0;
        return doElement();
    }

    protected abstract Object doElement();

    @Override
    public void clear() {
        size = 0;
        doClear();
    }

    protected abstract void doClear();

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        if (size == 0){
            return new Object[0];
        }
        return doToArray();
    }

    protected abstract Object[] doToArray();

    @Override
    public Queue filter(final Predicate<Object> p) {
        assert p != null;
        final Queue from = this.makeCopy();
        final Queue to = getInstance();

        while (!from.isEmpty()) {
            final Object element = from.dequeue();
            if (p.test(element)) {
                to.enqueue(element);
            }
        }

        return to;
    }

    @Override
    public Queue map(final Function<Object, Object> f) {
        assert f != null;
        final Queue from = this.makeCopy();
        final Queue to = getInstance();

        while (!from.isEmpty()) {
            to.enqueue(f.apply(from.dequeue()));
        }

        return to;
    }

    @Override
    public void removeIf(final Predicate<Object> p) {
        assert p != null;
        apply(p.negate());
    }

    protected void apply(final Predicate<Object> p) {
        final Queue to = getInstance();
        while (!isEmpty()) {
            final Object element = dequeue();
            if (p.test(element)) {
                to.enqueue(element);
            }
        }
        initializeBy(to);
    }

    @Override
    public void retainIf(final Predicate<Object> p) {
        assert p != null;
        apply(p);
    }

    @Override
    public void dropWhile(final Predicate<Object> p) {
        assert p != null;
        while (!isEmpty() && p.test(element())) {
            dequeue();
        }
    }

    @Override
    public void takeWhile(final Predicate<Object> p) {
        assert p != null;
        final Queue to = getInstance();
        while (!isEmpty() && p.test(element())) {
            to.enqueue(dequeue());
        }
        initializeBy(to);
    }

}