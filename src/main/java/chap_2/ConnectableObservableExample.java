package chap_2;

import common.CommonUtils;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class ConnectableObservableExample {
    public static void main(String[] args) {
        String[] dt = {"1", "3", "5"};
        Observable<String> balls = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> dt[i])
                .take(dt.length);
        ConnectableObservable<String> source = balls.publish();
        source.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        source.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        source.connect();

        CommonUtils.sleep(250);
        source.subscribe(data -> System.out.println("Subscriber #3 => " + data));
        CommonUtils.sleep(100);
    }
}
/*
Subscriber #1 => 1
Subscriber #2 => 1
Subscriber #1 => 3
Subscriber #2 => 3
Subscriber #1 => 5
Subscriber #2 => 5
Subscriber #3 => 5
 */
