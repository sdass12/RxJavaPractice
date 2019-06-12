package chap_7.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ThrottleFirstAndLast {
    public static void main(String[] args) {
        String[] data = {"1", "2", "3", "4", "5", "6"};

        CommonUtils.exampleStart();

        Observable<String> earlySource = Observable.just(data[0])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        Observable<String> middleSource = Observable.just(data[1])
                .zipWith(Observable.timer(300L,TimeUnit.MILLISECONDS), (a, b) -> a);
        Observable<String> lateSource = Observable.just(data[2],data[3],data[4],data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
                .doOnNext(Log::dt);

        Observable<String> source = Observable.concat(earlySource,middleSource,lateSource)
                // 200ms씩 끊어서 가장 첫 번째로 발행된 값만 발행함
                //.throttleFirst(200L, TimeUnit.MILLISECONDS);
                // 200ms씩 끊어서 가장 마지막으로 발행된 값만 발행함
                .throttleLast(200L, TimeUnit.MILLISECONDS);

        source.subscribe(Log::it);
        CommonUtils.sleep(1200);
        /*  throttleFirst() 결과
        RxComputationThreadPool-1 | 431 | value = 1
        RxComputationThreadPool-3 | 739 | value = 2
        RxComputationThreadPool-4 | 852 | debug = 3
        RxComputationThreadPool-4 | 952 | debug = 4
        RxComputationThreadPool-4 | 952 | value = 4
        RxComputationThreadPool-4 | 1052 | debug = 5
        RxComputationThreadPool-4 | 1155 | debug = 6
        RxComputationThreadPool-4 | 1155 | value = 6
         */
    }
}
