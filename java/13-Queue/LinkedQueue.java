package queue;

import java.util.StringJoiner;


public class LinkedQueue extends AbstractQueue {
    //I: value != null
    private static class Node{
        Object value;
        Node next;
        //PRE: value != null
        public Node(Object value, Node next) {
            assert value != null;
            this.value = value;
            this.next = next;
        }
    }
    private Node head, tail;

    @Override
    protected Object doDequeue() {
        Object element = head.value;
        head = head.next;
        return element;
    }

    @Override
    protected void doEnqueue(Object element) {
        Node newTail = new Node(element, null);
        if (size == 1) {
            head = newTail;
            tail = head;
        } else {
            tail.next = newTail;
            tail = newTail;
        }
    }

    @Override
    protected Object doElement() {
        return head.value;
    }

    @Override
    protected void doClear() {
        head = null;
        tail = null;
    }

    //POST: IM /\ R = "[" + a[1]' + ", " +  a[2]' + ", " + ... + ", " + a[size']' + "]"
    public String toStr() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");

        if (size == 0) {
            return stringJoiner.toString();
        }

        Node from = head;
        while (from.next != null) {
            stringJoiner.add(from.value.toString());
            from = from.next;
        }
        stringJoiner.add(from.value.toString());
        return stringJoiner.toString();
    }

    @Override
    protected Object[] doToArray() {
        Object[] array = new Object[size];
        Node from = head;
        int index = 0;

        while (from.next != null){
            array[index++] = from.value;
            from = from.next;
        }
        array[index] = from.value;

        return array;
    }

    @Override
    public LinkedQueue makeCopy() {
        final LinkedQueue copy = new LinkedQueue();
        copy.size = size;
        copy.head = head;
        copy.tail = tail;
        return copy;
    }

    @Override
    public Queue getInstance() {
        return new LinkedQueue();
    }

    @Override
    public void initializeBy(final Queue queue) {
        assert queue instanceof LinkedQueue;
        LinkedQueue linked = (LinkedQueue)queue;
        this.size = linked.size;
        this.head = linked.head;
        this.tail = linked.tail;
    }
}