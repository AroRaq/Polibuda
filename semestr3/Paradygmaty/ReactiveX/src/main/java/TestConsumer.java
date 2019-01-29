
import io.reactivex.subscribers.DefaultSubscriber;

public class TestConsumer extends DefaultSubscriber<Integer> {

    public TestConsumer(int num) {
        super();
        this.num = num;
    }

    @Override
    protected void onStart() {
        request(100);
        requestsLeft--;
    }

    public void onNext(Integer integer) {
        System.out.println("Consumer " + num + " consuming " + integer);
        if (requestsLeft > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            request(1);
            requestsLeft--;
        }
    }

    public void onError(Throwable throwable) {

    }

    public void onComplete() {

    }

    private int num;
    private int requestsLeft = 1000000;

}
