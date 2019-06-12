package chap_8;

import common.Shape;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

        /* =============== TestObserver 클래스의 주요 함수 ===============
         * assertResult() - 예상된(expected) 결과와 실제(actual) 결과를 비교하는 메소드. JUnit의 assertEquals()와 같음.
         * assertFailure() - Observable에서 기대했던 에러(onError)가 발생하는지 확인하는 메소드.
                             기대했던 에러가 나지 않으면 테스트 코드 실행은 실패함.
         * assertFailureAndMessage() - 기대했던 에러 발생 시 에러 메시지까지 확인할 수 있음.
         * awaitDone() - interval() 함수처럼 비동기로 동작하는 Observable 코드를 테스트할 수 있음.
         * assertComplete() - Observable을 정상적으로 완료(onComplete)했는지 확인함.
         */

@RunWith(JUnitPlatform.class)
public class TestObservableExample {
    @Test
    @DisplayName("#1: using TestObserver for Shape.getShape()")
    public void testGetShapeObservable(){
        String[] data = {"1", "2-R", "3-T"};
        Observable<String> source = Observable.fromArray(data)
                .map(Shape::getShape);

        String[] expected = {Shape.BALL, Shape.RECTANGLE, Shape.TRIANGLE};

        source.test().assertResult(expected);
    }

    @Test
    @DisplayName("assertFailure() example")
    public void assertFailureExample(){
        String[] data = {"100", "200", "%300"};
        Observable<Integer> source = Observable.fromArray(data)
                .map(Integer::parseInt);

        source.test().assertFailure(NumberFormatException.class, 100, 200);
    }

    @Test
    @DisplayName("assertFailureAndMessage() example")
    public void assertFailureAndMessageExample(){
        String[] data = {"100", "200", "%300"};
        Observable<Integer> source = Observable.fromArray(data)
                .map(Integer::parseInt);

        source.test().assertFailureAndMessage(NumberFormatException.class,
                "For input string: \"%300\"", 100, 200);
    }

    @Test
    @DisplayName("assertComplete() example")
    public void assertComplete(){
        Observable<String> source = Observable.create(
                (ObservableEmitter<String> emitter) -> {
                    emitter.onNext("Hello RxJava");
                    emitter.onComplete();
                }
        );
        source.test().assertComplete();
    }
}
