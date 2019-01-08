import java.util.concurrent.Semaphore;

class IntCell {
    private final Semaphore available = new Semaphore(1, true);
    private int n = 0;
    public int getN() {
        return n;
    }
    public void setN(int n) {
        available.tryAcquire();
        this.n = n;
        available.release();
    }
}

class Count extends Thread {
    private static IntCell n = new IntCell();
    @Override public void run() {
        int temp;
        for (int i = 0; i < 200000; i++) {
            n.setN(n.getN() + 1);
        }
    }
    public static void main(String[] args) {
        Count p = new Count();
        Count q = new Count();
        p.start();
        q.start();
        try {
            p.join();
            q.join();
        }
        catch (InterruptedException e) { }
        System.out.println("The value of n is " + n.getN());
    }
}