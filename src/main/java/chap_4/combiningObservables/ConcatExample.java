package chap_4.combiningObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.functions.*;

import java.util.concurrent.TimeUnit;

public class ConcatExample {
    public static void main(String[] args) {
        // Action 객체는 doOnComplete() 함수처럼 인자가 없는 람다 표현식을 넣어야 할 때 사용
        Action onCompleteAction = () -> Log.d("on Complete");
        String[] data1 = {"1","3","5"};
        String[] data2 = {"2","4","6"};

        Observable<String> source1 = Observable.fromArray(data1)
                .doOnComplete(onCompleteAction);

        Observable<String> source2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> data2[idx])
                .take(data2.length)
                .doOnComplete(onCompleteAction);

        /*
         * concat() 함수는 여러 Observable를 이어주는 함수임
         * 특이사항으로는 첫 번째 Observable에 onComplete 이벤트가 발생되지 않으면 두 번째 Observable은 영원히 대기함
         * 따라서 메모리 누수의 위험을 내포함. 따라서 입력 Observable이 반드시 완료(onComplete 이벤트)될 수 있게 해야함
         */
        Observable<String> source = Observable.concat(source1, source2)
                .doOnComplete(onCompleteAction);
        source.subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
/* 결과
main | value = 1
main | value = 3
main | value = 5
main | debug = on Complete  //컴플리트가 발생한 후 다음 Observable의 값들이 발행됨
RxComputationThreadPool-1 | value = 2
RxComputationThreadPool-1 | value = 4
RxComputationThreadPool-1 | value = 6
RxComputationThreadPool-1 | debug = on Complete
 */