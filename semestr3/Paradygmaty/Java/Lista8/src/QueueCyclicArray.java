import java.util.ArrayList;

public class QueueCyclicArray<E> implements MyQueue<E> {
    public QueueCyclicArray() {
        f = 0;
        r = 0;
        tab = new ArrayList<E>(5);
        for (int i = 0; i < 5; i++)
            tab.add(null);
    }

    public QueueCyclicArray(int size) {
        f = 0;
        r = 0;
        tab = new ArrayList<E>(size + 1);
        for (int i = 0; i < size + 1; i++)
            tab.add(null);
    }

    @Override
    public void enqueue(E x) throws FullException {
        if (isFull())
            throw new FullException("QueueCyclicArray: enqueue");
        tab.set(r, x);
        r = (r+1) % tab.size();
    }

    @Override
    public void dequeue() {
        if (!isEmpty())
            f = (f+1) % tab.size();
    }

    @Override
    public E first() throws EmptyException {
        if (isEmpty())
            throw new EmptyException("QueueCyclicArray: first");
        return tab.get(f);
    }

    @Override
    public boolean isEmpty() {
        return f == r;
    }

    @Override
    public boolean isFull() {
        return length() == tab.size() - 1;
    }

    private int length() {
        if (f <= r)
            return r - f;
        else
            return tab.size() - f + r;
    }

    private ArrayList<E> tab;
    private int f;
    private int r;
}
