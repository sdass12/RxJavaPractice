package chap_2;

import io.reactivex.Observable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class ObservableFromPublisherExample {
    public static void main(String[] args) {
        Publisher<String> publisher = (Subscriber<? super String> s) -> {
            s.onNext("Hello Observable.fromPublisher()");
            s.onComplete();
        };
        Observable<String> source = Observable.fromPublisher(publisher);
        source.subscribe(System.out::println);
    }
}
