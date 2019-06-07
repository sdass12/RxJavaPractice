package chap_3;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public class ReduceExample {
    public static void main(String[] args) {
        String[] balls = {"1","3","5"};

        Maybe<String> source = Observable.fromArray(balls)
                // Observable에서 모든 데이터가 입력된 후 그것을 종합하여 마지막 1개의 데이터만 구독자에게 발행함.
                .reduce((ball1, ball2) -> ball2 + "(" + ball1 + ")");
        Maybe<String> source2 = Observable.fromArray(balls)
                .reduce((ball1, ball2) -> ball1 + "(" + ball2 + ")");


        source.subscribe(System.out::println);
        source2.subscribe(System.out::println);
    }
}
// 5(3(1))
// 1(3)(5)