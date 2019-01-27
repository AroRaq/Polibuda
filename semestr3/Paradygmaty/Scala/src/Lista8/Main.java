package Lista8;

public class Main {
    public static void main(String[] args) {
        QueueCyclicArray<Integer> que = new QueueCyclicArray<>(3);
        try {
            que.enqueue(5);
            que.enqueue(10);
            que.enqueue(15);
            que.dequeue();
            que.enqueue(20);
            System.out.println(que.first());
        }
        catch (EmptyException e) {
            System.out.println("Queue was empty");
        }
        catch (FullException e) {
            System.out.println("Queue was full");
        }
    }
}
