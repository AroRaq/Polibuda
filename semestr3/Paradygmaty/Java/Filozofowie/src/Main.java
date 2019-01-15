public class Main {

    public static void main(String[] args) {
        Canteen canteen = new Canteen(5);
        Philosopher philosophers[] = {
                new Philosopher(0, canteen),
                new Philosopher(1, canteen),
                new Philosopher(2, canteen),
                new Philosopher(3, canteen),
                new Philosopher(4, canteen)
        };
        for (Philosopher p : philosophers) {
            p.start();
        }
    }
}
