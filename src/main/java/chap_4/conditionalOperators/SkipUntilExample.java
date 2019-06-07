package chap_4.conditionalOperators;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class SkipUntilExample {
    // takeUntil()의 예제와 같은 코드이나 takeUntil()을 skipUntil()로 바꿈
    public static void main(String[] args) {
        String[] data = {"1","2","3","4","5","6"};

        Observable<String> source = Observable.fromArray(data)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (val, notUsed) -> val)
                // 기존 Observable에서 값이 아무리 발행돼도 새로운 Observable이 값을 발행하지 않으면 다 무시함
                // 즉 새로운 Observable에서 값이 발행된 이후부터 첫 번째 Observable의 값이 발행 됨
                .skipUntil(Observable.timer(500L, TimeUnit.MILLISECONDS));

        source.subscribe(Log::i);

        CommonUtils.sleep(1000);

    }
}
/* 결과
RxComputationThreadPool-2 | value = 5
RxComputationThreadPool-2 | value = 6
 */
