package chap_4.MathematicalAndAggregateOperators;

import common.Log;
import hu.akarnokd.rxjava2.math.MathFlowable;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MathFunctionsExample {
    public static void main(String[] args) {
        Integer[] data = {1, 2, 3, 4};
        // 이 예제에서 알아 볼 함수들은 count()를 제외하고는 RxJava2Extensions를 gradle에 넣어줘야 함

        // 1. count() - Observable에서 발행한 데이터의 개수를 발행
        Single<Long> source = Observable.fromArray(data)
                .count();
        source.subscribe(count -> Log.i("count is " + count));

        // 2. max() & min() - Observable이 발행한 데이터의 최대값과 최소값을 발행
        Flowable.fromArray(data)
                // to는 함수의 타입을 MathFlowable로 바꿔주기 위해 사용(max, min 등등은 Flowable<T>를 반환함)
                .to(MathFlowable::max)
                .subscribe(max -> Log.i("max is " + max ));
        Flowable.fromArray(data)
                .to(MathFlowable::min)
                .subscribe(min -> Log.i("min is " + min ));

        // 3. sum() & average() - Observable이 발행한 데이터의 합과 평균을 발행
        Flowable<Integer> flowable = Flowable.fromArray(data)
                .to(MathFlowable::sumInt);
        flowable.subscribe(sum -> Log.i("sum is " + sum));

        Flowable<Double> flowable2 = Observable.fromArray(data)
                .toFlowable(BackpressureStrategy.BUFFER)
                .to(MathFlowable::averageDouble);
        flowable2.subscribe(avg -> Log.i("average is " + avg));
    }
}
/* 결과
main | value = count is 4
main | value = max is 4
main | value = min is 1
main | value = sum is 10
main | value = average is 2.5
 */