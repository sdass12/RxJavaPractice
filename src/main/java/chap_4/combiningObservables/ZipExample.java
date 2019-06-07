package chap_4.combiningObservables;

import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ZipExample {
    public static void main(String[] args) {
        String[] shapes = {"BALL", "PENTAGON", "STAR"};
        String[] coloredTriangles = {"2-T", "6-T", "4-T"};

        Observable<String> source = Observable.zip(
                // getSuffix()는 모양을 보고 해당하는 접미사를 반환해주는 함수임 ex)PENTAGON -> -P, STAR -> -S 등등
                Observable.fromArray(shapes).map(Shape::getSuffix),
                // getColor()는 뒤에 모양을 없애주는 함수임 ex)2-T -> 2, 6-T -> 6 등등
                Observable.fromArray(coloredTriangles).map(Shape::getColor),
                // 위에 두 Observable들이 발행할 값을 합쳐줌
                (suffix, color) -> color + suffix);

        source.subscribe(Log::i);

        //////////// 숫자 결합 ////////////
        System.out.println("-------------------------");


        Observable<Integer> source2 = Observable.zip(
                Observable.just(100, 200, 300),
                Observable.just(10, 20, 30),
                Observable.just(1, 2, 3),
                (a, b, c) -> a + b + c);

        source2.subscribe(Log::i);


        //////////// interval ////////////
        System.out.println("----------------");
        CommonUtils.exampleStart();

        Observable<String> source3 = Observable.zip(
                Observable.just("Red", "Blue", "GREEN"),
                Observable.interval(200L, TimeUnit.MILLISECONDS),
                (value, i) -> value
        );

        source3.subscribe(Log::it);
        CommonUtils.sleep(1000);
        ///////////// zipWith ////////////
        System.out.println("----------------");

        Observable<Integer> source4 = Observable.zip(
                Observable.just(100, 200 ,300),
                Observable.just(10, 20, 30),
                (a, b) -> a + b)
                // zip()과 같은 동작을 하지만 함수 틈틈히 쓸 수 있다는 장점이 있음
                    .zipWith(Observable.just(1, 2, 3), (ab, c) -> ab + c);
        source4.subscribe(Log::i);
    }
}
/* 결과
main | value = 2
main | value = 6-P
main | value = 4-S
-------------------------
main | value = 111
main | value = 222
main | value = 333
----------------
RxComputationThreadPool-1 | 214 | value = Red
RxComputationThreadPool-1 | 415 | value = Blue
RxComputationThreadPool-1 | 614 | value = GREEN
----------------
main | value = 111
main | value = 222
main | value = 333
 */