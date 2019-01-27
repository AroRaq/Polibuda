package Lista11.Philosophers;

import java.util.Random;

public class Philosopher extends Thread {

    public Philosopher(int philNum, Canteen canteen) {
        this.philNum = philNum;
        this.canteen = canteen;
    }
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(rand.nextInt(2000));
                canteen.arbitrator.acquire();
                //System.out.printf("Philosopher %d entered canteen\n", philNum);
                canteen.LeftFork(philNum).acquire();
                canteen.RightFork(philNum).acquire();
                Thread.sleep(eatTime);
                canteen.LeftFork(philNum).release();
                canteen.RightFork(philNum).release();
                canteen.arbitrator.release();
                //System.out.printf("Philosopher %d left canteen\n", philNum);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final int philNum;
    private final Canteen canteen;
    private final int eatTime = 200;

    public static final Random rand = new Random();
}
