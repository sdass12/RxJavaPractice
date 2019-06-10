package chap_4.transformingObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class SwitchMapExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();

        String balls[] = {"1","3","5"};

        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                // balls의 크기만큼 balls안에 있는 값들을 꺼냄
                .take(balls.length)
                // 중간 처리과정을 출력해줌
                .doOnNext(Log::dt)
                .switchMap(ball -> Observable.interval(200L,TimeUnit.MILLISECONDS)
                        /*
                         * 0.2초마다 위에서 내려온 값에 ◇를 붙임.
                         * 단, 처리하던 도중 새로운 요청이 온다면 처리하던 일을 무시하고 새로운 요청을 처리함.
                         */
                        .map(notUsed -> ball + "◇")
                        .take(2)
                );
        source.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }
}

/*
 * 100ms마다 ball을 출력하고 200ms마다 ball에 ◇를 붙여줌
 * 첫 번째 interval()에서 1, 3, 5가 각각 생성 된걸 확인 할 수 있음
 * 그 후, 1에 ◇를 추가해주던 과정에서 3에 ◇를 붙이라는 요청이 들어오면서 1의 처리를 중지시킴
 * 3 또한 마찬가지로 중지되고 5만 ◇를 붙여줌(take(2)이기 때문에 2번 반복)
 *
 * 또한 스레드의 이름을 보면 데이터를 발행하는 스레드와 그 값을 전달하는 스레드가 다르다는 것을 알 수 있음
 * concatMap(), flatMap(), switchMap() 함수들의 결과들을 보면 2, 3, 4번만 발생했던 것을 확인할 수 있음
 * 그 이유는 1번 스레드는 데이터를 발행하는데 사영됐기 때문이였음.
결과
RxComputationThreadPool-1 | 261 | debug = 1
RxComputationThreadPool-1 | 362 | debug = 3
RxComputationThreadPool-1 | 463 | debug = 5
RxComputationThreadPool-4 | 665 | value = 5◇
RxComputationThreadPool-4 | 864 | value = 5◇
 */