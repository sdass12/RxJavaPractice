package chap_4.transformingObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ConcatMapExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();

        String[] balls = {"1","3","5"};

        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                // Long 타입을 int로 변환
                .map(Long::intValue)
                // interval()로 발행된 값을 balls의 idx값으로 활용(Ex - balls[0], balls[1], ...)
                .map(idx -> balls[idx])
                // balls의 길이(3)만큼 발행이 됐다면 onComplete() 이벤트를 발생시킴
                .take(balls.length)
                // 위에서 발행된 세 값을 가지고 0.2초마다 ◇를 붙여줌
                .concatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                    .map(notUsed -> ball + "◇")
                    // concatMap()이 2번 실행되면 onComplete() 이벤트를 발생시킴
                    .take(2)
                );
        //source.subscribe(Log::it);

        ////////////////// interleaving /////////////////
        Observable<String> source2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .flatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                    .map(notUsed -> ball + "◇")
                    .take(2)
                );
        source2.subscribe(Log::it);

        CommonUtils.sleep(2000);
    }
}
/* 결과1 - interleaving X
 * concatMap()은 인터리빙(끼어들기)를 허용하지 않기 때문에
 * 결과의 시간들을 보면 2번 스레드가 처리가 끝나기 전에 3번 스레드가 들어오지 못함
 * 즉 먼저 들어온 데이터 순서대로 처리해서 결과를 낼 수 있도록 보장해줌
RxComputationThreadPool-2 | 404 | value = 1◇
RxComputationThreadPool-2 | 601 | value = 1◇
RxComputationThreadPool-3 | 817 | value = 3◇
RxComputationThreadPool-3 | 1004 | value = 3◇
RxComputationThreadPool-4 | 1210 | value = 5◇
RxComputationThreadPool-4 | 1411 | value = 5◇
 */

/* 결과2 - interleaving O
 * 결과1과는 다르게 스레드들이 마구 섞여있음
 * 반면 처리완료 시점은 거의 절반임
RxComputationThreadPool-2 | 422 | value = 1◇
RxComputationThreadPool-3 | 519 | value = 3◇
RxComputationThreadPool-4 | 618 | value = 5◇
RxComputationThreadPool-2 | 620 | value = 1◇
RxComputationThreadPool-3 | 718 | value = 3◇
RxComputationThreadPool-4 | 819 | value = 5◇
 */