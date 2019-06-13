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
        // ConnectableObservable 객체를 생성하려면 publish() 함수를 호출해야 됨
        ConnectableObservable<String> source = balls.publish();
        source.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        source.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        source.connect();

        CommonUtils.sleep(250);
        // connect()가 된 이후의 값만 발행시킴 (250ms 후에 발행을 시작했기 때문에 5만 발행됨)
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
