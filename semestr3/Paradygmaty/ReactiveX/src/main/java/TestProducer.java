import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.LinkedList;

public class TestProducer extends Flowable<Integer> {

    public TestProducer(){//LinkedList<Integer> buff) {
       // buffer = buff;
    }

    private synchronized int Produce() {
        for (int i = 0; i < 1000000; i++);
        return productNum++;
    }

    protected void subscribeActual(final Subscriber<? super Integer> subscriber) {
        Subscription s = new Subscription() {
            @Override
            public void request(long l) {
                requests += l;

                if (working) {
                    return;
                }

                working = true;
                while (requests-- > 0) {
                    subscriber.onNext(Produce());
                    System.out.println(requests);
                }
                working = false;
            }

            @Override
            public void cancel() {

            }

            private int requests = 0;
            private boolean working = false;
        };
        subscriber.onSubscribe(s);
    }

    private int productNum = 0;
    //private LinkedList<Integer> buffer;
}
