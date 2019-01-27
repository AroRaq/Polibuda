package Lista8;

public interface MyQueue<E> {
    void enqueue(E x) throws FullException;
    void dequeue();
    E first() throws EmptyException;
    boolean isEmpty();
    boolean isFull();
}