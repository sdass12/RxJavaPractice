package chap_4.creatingObservables;

import common.Log;
import io.reactivex.Observable;

public class RangeExample {
    public static void main(String[] args) {
        // 1부터 10까지 값을 발행함
        Observable<Integer> source = Observable.range(1,10)
                //값들을 제곱
                .map(data -> data*data)
                //제곱 된 값들 중 짝수인 값들만 발행
                .filter(data -> data % 2 == 0);
        source.subscribe(Log::i);
    }
}
/* 결과
 * 스레드 이름이 main인걸로 보아 range()는 메인 스레드에서 실행됨을 알 수 있음
main | value = 4
main | value = 16
main | value = 36
main | value = 64
main | value = 100
 */