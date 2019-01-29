import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class Main {
    private static Object TimeUnit;

    public static void main(String[] args) {
        TestProducer prod = new TestProducer();
        TestConsumer con1 = new TestConsumer(1);
        TestConsumer con2 = new TestConsumer(2);
        prod.subscribeOn(Schedulers.newThread()).subscribe(con1);
        //prod.subscribe(con2);
        while(true) {
            System.out.print("");
        }

    }
}

