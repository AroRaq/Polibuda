import java.util.concurrent.Semaphore;

public class Canteen {
    public Canteen(int seats) {
        this.seats = seats;
        forks = new Semaphore[seats];
        arbitrator = new Semaphore(seats - 1, true);
        for (int i = 0; i < seats; i++)
            forks[i] = new Semaphore(1, true);
    }

    public Semaphore LeftFork(int philNum) {
        return forks[philNum];
    }
    public Semaphore RightFork(int philNum) {
        return forks[(philNum+1) % seats];
    }

    public int seats;

    public Semaphore arbitrator;
    private Semaphore forks[];
}
