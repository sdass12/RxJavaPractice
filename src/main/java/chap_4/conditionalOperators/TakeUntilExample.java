package chap_4.conditionalOperators;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class TakeUntilExample {
    public static void main(String[] args) {
        String[] data = {"1","2","3","4","5","6"};

        Observable<String> source = Observable.fromArray(data)
                // val은 위에서 발행한 fromArray(data)의 값이고 notUsed는 interval()에서 발행한 값임
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (val, notUsed) -> val)
                // 값을 발행하던 도중 다른 Observable에서 값이 발행되면 기존 Observable에 onComplete 이벤트를 발생시킴
                .takeUntil(Observable.timer(500L, TimeUnit.MILLISECONDS));

        source.subscribe(Log::i);

        CommonUtils.sleep(1000);
    }
}
/*
RxComputationThreadPool-2 | value = 1
RxComputationThreadPool-2 | value = 2
RxComputationThreadPool-2 | value = 3
RxComputationThreadPool-2 | value = 4
 */