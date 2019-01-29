import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import org.reactivestreams.Subscriber;

class TestBuffer extends Flowable<Integer> {

    @Override
    protected void subscribeActual(Subscriber<? super Integer> subscriber) {

    }
}