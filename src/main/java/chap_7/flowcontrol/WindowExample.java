package chap_7.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class WindowExample {
    public static void main(String[] args) {
        String[] data = {"1", "2", "3", "4", "5", "6"};

        CommonUtils.exampleStart();

        Observable<String> earlySource = Observable.fromArray(data)
                .take(3)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        Observable<String> middleSource = Observable.just(data[3])
                .zipWith(Observable.timer(300L,TimeUnit.MILLISECONDS), (a, b) -> a);
        Observable<String> lateSource = Observable.just(data[4],data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<Observable<String>> source = Observable.concat(earlySource,middleSource,lateSource)
                // 처음에 Observable을 만든 후 3개의 데이터를 받으면 새로운 Observable을 생성해서 다시 값을 발행함
                .window(3);
        // source의 타입이 Observable<Observable<String>>이기 때문에 안에 있는 Observable을 구독해야 원하는 값이 나옴
        source.subscribe(observable -> {
            Log.dt("New Observable Started!!");
            observable.subscribe(Log::it);
                });

        CommonUtils.sleep(1000);

        /*
        RxComputationThreadPool-1 | 438 | debug = New Observable Started!!
        RxComputationThreadPool-1 | 439 | value = 1
        RxComputationThreadPool-1 | 544 | value = 2
        RxComputationThreadPool-1 | 630 | value = 3
        RxComputationThreadPool-2 | 956 | debug = New Observable Started!!
        RxComputationThreadPool-2 | 956 | value = 4
        RxComputationThreadPool-3 | 1057 | value = 5
        RxComputationThreadPool-3 | 1157 | value = 6
         */
    }
}
