
import io.reactivex.schedulers.Schedulers;

public class Main {

    public static void main(String[] args) {
        TestProducer prod = new TestProducer();
        TestConsumer con1 = new TestConsumer(1);
        TestConsumer con2 = new TestConsumer(2);
        prod.subscribeOn(Schedulers.newThread()).subscribe(con1);
        prod.subscribeOn(Schedulers.newThread()).subscribe(con2);



        while(true) {
            System.out.print("");
        }

        /*

        Flowable<Long> producer = Observable
                .interval(1, java.util.concurrent.TimeUnit.MICROSECONDS)
                .toFlowable(BackpressureStrategy.LATEST);

        producer.subscribe(new DisposableSubscriber<Long>() {

            @Override
            public void onStart() {
                request(1);
            }

            @Override
            public void onNext(Long aLong) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(aLong);
                request(1);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });

        while (true) {
            System.out.print("");
        }
        */
    }


    public static void Produce() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

