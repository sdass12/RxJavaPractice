package chap_4.combiningObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class MergeExample {
    public static void main(String[] args) {
        String[] data1 = {"1","3"};
        String[] data2 = {"2","4","6"};

        Observable<String> source1 = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> data1[idx])
                .take(data1.length);
        Observable<String> source2 = Observable.interval(50L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> data2[idx])
                .take(data2.length);
        // 값이 하나만 발행되도 다음 값을 기다리지 않고 바로바로 발행시킴
        Observable<String> source = Observable.merge(source1, source2);

        source.subscribe(Log::i);
        CommonUtils.sleep(1000);

    }
}
/* 결과
RxComputationThreadPool-1 | value = 1
RxComputationThreadPool-2 | value = 2
RxComputationThreadPool-1 | value = 3
RxComputationThreadPool-2 | value = 4
RxComputationThreadPool-2 | value = 6
 */
