package queue;

import java.util.StringJoiner;


//IM: size' = size /\ ∀i = 1 ... size': a[i]' = a[i]

//I: size >= 0 /\ ∀i = 1 ... size: a[i] != null
public class ArrayQueue extends AbstractQueue {
    private int start, end;
    private Object[] elements = new Object[INIT_LENGTH];

    @Override
    protected Object doDequeue() {
        Object element = elements[start];
        elements[start] = null;
        start = (start + 1) % elements.length;
        return element;
    }

    @Override
    protected void doEnqueue(Object element) {
        ensureCapacity();
        elements[end] = element;
        end = (end + 1) % elements.length;
    }

    @Override
    protected Object doElement() {
        return elements[start];
    }

    @Override
    protected void doClear() {
        start = end = 0;
        elements = new Object[INIT_LENGTH];
    }

    //PRE: size' = size + 1
    //POST: IM
    private void ensureCapacity() {
        if (size <= elements.length) {
            return;
        }
        Object[] newElements = new Object[2 * elements.length];

        System.arraycopy(elements, start, newElements, 0, elements.length - start);
        if (end <= start) {
            System.arraycopy(elements, 0, newElements, elements.length - start, end);
        }

        end = elements.length;
        elements = newElements;
        start = 0;
    }

    //POST: IM /\ R = "[" + a[1]' + ", " +  a[2]' + ", " + ... + ", " + a[size']' + "]"
    public String toStr() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        if (size == 0) {
            return stringJoiner.toString();
        }

        if (end <= start) {
            for (int i = start; i < elements.length; i++) {
                stringJoiner.add(elements[i].toString());
            }
            for (int i = 0; i < end; i++) {
                stringJoiner.add(elements[i].toString());
            }
        } else {
            for (int i = start; i < end; i++) {
                stringJoiner.add(elements[i].toString());
            }
        }

        return stringJoiner.toString();
    }

    @Override
    protected Object[] doToArray() {
        Object[] array = new Object[size];

        if (end > start) {
            System.arraycopy(elements, start, array, 0, end - start);
        } else {
            System.arraycopy(elements, start, array, 0, elements.length - start);
            System.arraycopy(elements, 0, array, elements.length - start, end);
        }

        return array;
    }

    //PRE: element != null
    //POST: size' = size + 1 /\ a[1]' = element /\ ∀i = 2 ... size': a[i]' = a[i - 1]
    public void push(Object element) {
        assert element != null;
        size++;
        ensureCapacity();
        start = start - 1 >= 0 ? start - 1 : elements.length - 1;
        elements[start] = element;
    }

    //PRE: size > 0
    //POST: IM /\ R = a[size']'
    public Object peek() {
        assert size > 0;
        int i = end - 1 >= 0 ? end - 1 : elements.length - 1;
        return elements[i];
    }

    //PRE: size > 0
    //POST: R = a[size] /\ size' = size - 1 /\ ∀i = 1 ... size': a[i]' = a[i]
    public Object remove(){
        assert size > 0;
        size--;
        end = end - 1 >= 0 ? end - 1 : elements.length - 1;
        Object element = elements[end];
        elements[end] = null;
        return element;
    }

    //PRE: index >= 0 /\ index < size
    //POST: IM /\ R = a[index + 1]'
    public Object get(int index) {
        assert index >= 0 && index < size : "Illegal index";
        return elements[(index + start) % elements.length];
    }

    //PRE: index >= 0 /\ index < size /\ element != null
    //POST: size' = size /\ a[index + 1]' = element /\ ∀i = 1 ... index, index + 2, ... size: a[i]' = a[i]
    public void set(int index, Object element) {
        assert element != null;
        assert index >= 0 && index < size : "Illegal index";
        elements[(index + start) % elements.length] = element;
    }

    @Override
    public ArrayQueue makeCopy() {
        final ArrayQueue copy = new ArrayQueue();
        copy.size = copy.end = size;
        copy.start = 0;
        copy.elements = toArray();
        return copy;
    }

    @Override
    public Queue getInstance() {
        return new ArrayQueue();
    }

    @Override
    public void initializeBy(final Queue queue) {
        assert queue instanceof ArrayQueue;
        ArrayQueue arrayQueue = (ArrayQueue)queue;
        this.size = arrayQueue.size;
        this.elements = arrayQueue.elements;
        this.start = arrayQueue.start;
        this.end = arrayQueue.end;
    }
}