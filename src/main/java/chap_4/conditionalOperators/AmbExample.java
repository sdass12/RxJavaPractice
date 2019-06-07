package chap_4.conditionalOperators;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmbExample {
    public static void main(String[] args) {
        String[] data1 = {"1", "3", "5"};
        String[] data2 = {"2", "4", "6"};

        List<Observable<String>> sources = Arrays.asList(
                Observable.fromArray(data1)
                    .doOnComplete(() -> Log.d("Observable #1 : onComplte()")),
                Observable.fromArray(data2)
                    .delay(100L, TimeUnit.MILLISECONDS)
                    .doOnComplete(() -> Log.d("Observable #2 : onComplte()"))
        );
        // 여러 개의 Observable 중 가장 먼저 데이터를 발행한 Observable 을 선택함
        Observable.amb(sources)
                .doOnComplete(() -> Log.d("Result : onComplete()"))
                .subscribe(Log::i);
        CommonUtils.sleep(1000);

    }
}
