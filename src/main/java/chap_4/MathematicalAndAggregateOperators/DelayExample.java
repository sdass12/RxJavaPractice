package chap_4.MathematicalAndAggregateOperators;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class DelayExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();
        String[] data = {"!", "7", "2", "3", "4"};
        Observable<String> source = Observable.fromArray(data)
                .delay(100L, TimeUnit.MILLISECONDS);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
}
/*
RxComputationThreadPool-1 | 331 | value = !
RxComputationThreadPool-1 | 333 | value = 7
RxComputationThreadPool-1 | 333 | value = 2
RxComputationThreadPool-1 | 333 | value = 3
RxComputationThreadPool-1 | 333 | value = 4

--------------------------
// 만약 delay()를 안 쓰면 약 100ms만큼 빨라짐
main | 151 | value = !
main | 151 | value = 7
main | 152 | value = 2
main | 152 | value = 3
main | 152 | value = 4
 */
