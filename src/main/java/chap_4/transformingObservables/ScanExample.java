package chap_4.transformingObservables;

import common.Log;
import io.reactivex.Observable;

public class ScanExample {
    public static void main(String[] args) {
        String[] balls = {"1", "3", "5"};
        Observable<String> source = Observable.fromArray(balls)
                // scan() 함수는 실행할 때마다 입력값에 맞는 중간 결과 및 최종 결과를 구독자에게 발행함
                .scan((ball1, ball2) -> ball2 + "(" + ball1 + ")");
        source.subscribe(Log::i);
    }
}
/*
 * 결과가 reduce() 함수 때와 비슷한 것을 알 수 있음
 * reduce()와 scan()의 큰 차이는 reduce()는 Maybe<T>인 반면 scan()은 Observable<T>라는 점이다.
 * 그 이유는 reduce() 함수의 경우 마지막 값이 입력되지 않거나 onComplete 이벤트가 발생하지 않으면 값을 발행하지 않음
 * 반면 scan() 함수는 값이 입력될 때마다 구독자에게 값을 발행한다. 따라서 Maybe가 아닌 Observable임.
 */
/* 결과
main | value = 1
main | value = 3(1)
main | value = 5(3(1))
 */
