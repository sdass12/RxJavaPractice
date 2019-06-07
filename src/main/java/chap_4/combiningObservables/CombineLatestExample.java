package chap_4.combiningObservables;

import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class CombineLatestExample {
    public static void main(String[] args) {
        String[] data1 = {"6", "7", "4", "2"};
        String[] data2 = {"DIAMOND", "STAR", "PENTAGON"};

        // zip()과 흡사하지만 combineLatest()는 안에 값이 바뀌면 최신 값을 보여준다는 점이 다름
        Observable<String> source = Observable.combineLatest(
                Observable.fromArray(data1)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS),
                        (shape, notUsed) -> Shape.getColor(shape)),
                Observable.fromArray(data2)
                .zipWith(Observable.interval(150L,200L, TimeUnit.MILLISECONDS),
                        (shape, notUsed) -> Shape.getSuffix(shape)), (v1, v2) -> v1 + v2);

        source.subscribe(Log::i);
        CommonUtils.sleep(1000);

    }
}
/* 결과
RxComputationThreadPool-2 | value = 6<>
RxComputationThreadPool-1 | value = 7<>
// 여기부터 두번 째 interval이 시작
RxComputationThreadPool-1 | value = 4<>
// 위에서 4를 썼지만 두번 째 값이 바뀌면서 최신 값을 발행함
RxComputationThreadPool-2 | value = 4-S
RxComputationThreadPool-1 | value = 2-S
RxComputationThreadPool-2 | value = 2-P
 */