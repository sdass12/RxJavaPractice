package chap_4.creatingObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class IntervalRangeExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();

        // 이름처럼 interval()과 range()를 함쳐놓음
        // 일정시간마다 값을 발행하지만 range()가 정해놓은 횟수 만큼만 발행
        Observable<Long> source = Observable.intervalRange(
                // 50ms 마다 발행하되 1부터 10까지 발행 됐다면 onComplete() 이벤트를 발생시킴
                1,
                10,
                0L,
                50L,
                TimeUnit.MILLISECONDS)
                // 위에서 발행된 값이 짝수일 경우만 통과시킴
                .filter(data -> data % 2 == 0)
                // 필터링이 된 값들에 1을 더한 후 101을 곱해줌
                .map(data -> (data + 1) * 101);

        source.subscribe(Log::it);

        CommonUtils.sleep(1000);

    }
}
/* 결과
 * value를 보면 알 수 있지만 값들이 홀수임
 * 즉 코드의 실행 순서는 filter()를 한 후 map()을 했다는걸 알 수 있음
 * 또한 마지막 값(1111/101-1 = 10)이 10이지만 시간을 보면 1000ms 까지 여유가 있는데도 다음 값이 없음
 * 즉 range()로 인해서 10까지 발행되고 onComplete() 이벤트가 발생됨
RxComputationThreadPool-1 | 239 | value = 303
RxComputationThreadPool-1 | 339 | value = 505
RxComputationThreadPool-1 | 439 | value = 707
RxComputationThreadPool-1 | 539 | value = 909
RxComputationThreadPool-1 | 639 | value = 1111
 */