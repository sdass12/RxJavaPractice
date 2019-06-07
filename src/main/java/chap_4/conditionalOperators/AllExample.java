package chap_4.conditionalOperators;

import common.Log;
import common.Shape;
import io.reactivex.Observable;
import io.reactivex.Single;

public class AllExample {
    public static void main(String[] args) {
        String[] data = {"1", "2", "3","4"};

        Single<Boolean> source = Observable.fromArray(data)
                // 전부 BALL로 변환 됨
                .map(Shape::getShape)
                // 모든 값들이 BALL이 맞는지 확인함 (맞으면 true 아니면 false 반환)
                .all(Shape.BALL::equals);

        source.subscribe(System.out::println);
    }
}
/*  결과
true
 */