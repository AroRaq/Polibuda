package Lista11;

import java.util.concurrent.Semaphore;

class IntCell {
    private final Semaphore available = new Semaphore(1);
    private int n = 0;
    public int getN() throws InterruptedException {
        available.acquire();
        return n;
    }
    public void setN(int n) {
        this.n = n;
        available.release();
    }
}

class Count extends Thread {
    private static IntCell n = new IntCell();
    @Override public void run() {
        int temp;
        for (int i = 0; i < 200000; i++) {
            try {
                temp = n.getN();
                n.setN(temp + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            System.out.println("The value of n is " + n.getN());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}