package chap_7.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class SamepleExample {
    public static void main(String[] args) {
        String[] data = {"1", "7", "2", "3", "6"};

        CommonUtils.exampleStart();

        Observable<String> earlySource = Observable.fromArray(data)
                .take(4)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a,b) -> a);

        Observable<String> lateSource = Observable.fromArray(data[4])
                .zipWith(Observable.interval(300L, TimeUnit.MILLISECONDS), (a,b) -> a);

        Observable<String> source = Observable.concat(earlySource,lateSource)
                //.sample(300L, TimeUnit.MILLISECONDS);
                // emitLast를 true로 하면 sample()의 실행이 끝나지 않았는데 Observable이 종료될 경우 마지막 값이 발행됨
                .sample(300L, TimeUnit.MILLISECONDS,true);

        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
        /*
        RxComputationThreadPool-1 | 819 | value = 2
        RxComputationThreadPool-1 | 1036 | value = 3
        RxComputationThreadPool-3 | 1147 | value = 6
         */
    }
}
