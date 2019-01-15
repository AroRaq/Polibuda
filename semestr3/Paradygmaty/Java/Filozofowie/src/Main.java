public class Main {
    public static void main(String[] args) {
        int count = 5;
        Canteen canteen = new Canteen(count);
        Philosopher philosophers[] = new Philosopher[count];
        for (int i = 0; i < count; i++) {
            philosophers[i] = new Philosopher(i, canteen);
            philosophers[i].start();
        }
    }
}
