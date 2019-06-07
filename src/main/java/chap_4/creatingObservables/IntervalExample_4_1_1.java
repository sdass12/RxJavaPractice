package chap_4.creatingObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class IntervalExample_4_1_1 {
    public static void main(String[] args) {

        /*
         * 4챕터의 첫 예제이므로 여기서 반복적으로 나오는 메소드들을 설명하고 이후에는 설명하지 않음.
         * CommonUtils.exampleStart() - 시작 시간을 표시해주는 메소드(없을 경우 결과 값의 시간이 1559119955670 이런식으로 나옴)
         * Log::it - 결과를 보기 좋게 출력해줌. i는 information, t는 time의 줄임말임
         * CommonUtils.sleep() - 단순한 Thread.sleep과 같음(try, catch문을 생략해줌)
         */

        CommonUtils.exampleStart();
        // 100ms마다 0부터 +1씩 값을 발행시킴
        Observable<Long> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                //발행된 값에 1을 더한 후 100을 곱해줌(보기 편하게 하기 위해)
                .map(data -> (data + 1) * 100)
                //앞에서 부터 15개만 발행함(이외의 값은 무시)
                //어차피 sleep(1000)이고 period가 100L이여서 15개의 값을 발행하지도 못함
                .take(15);
        source.subscribe(Log::it);

        ///////////////initialDelay////////////////////

        //전체적인 코드는 위와 동일하지만 initialDelay를 0L을 주면서 초기 지연 값이 100ms -> 0ms가 되면서 시작시간이 빨라짐
        Observable<Long> source2 = Observable.interval(0L,100L, TimeUnit.MILLISECONDS)
                .map(data -> (data + 1) * 100)
                .take(15);
        source2.subscribe(Log::it);
        // sleep을 시키는 이유는 슬립을 안 시키면 메인 스레드가 interval()이 동작할 시간을 주지 않고 종료되기 때문임
        // 결과를 보면 알겠지만 interval()은 메인 스레드가 아닌 별도의 스레드를 생성해서 거기서 처리해줌
        CommonUtils.sleep(1000);
    }
}
/* 결과 (앞에 *이 있는건 주석)
* RxComputationThreadPool-2(줄여서 Pool-2)가 스레드 이름임
* 즉 메인스레드가 아닌 별도의 스레드(스케쥴러)에서 처리 됨
* Pool-2는 시작시간이 188인걸 볼 수 있음(약 100ms 빠름)
RxComputationThreadPool-2 | 188 | value = 100
RxComputationThreadPool-1 | 297 | value = 100
RxComputationThreadPool-2 | 297 | value = 200
RxComputationThreadPool-1 | 406 | value = 200
RxComputationThreadPool-2 | 406 | value = 300
RxComputationThreadPool-1 | 500 | value = 300
RxComputationThreadPool-2 | 500 | value = 400
RxComputationThreadPool-2 | 609 | value = 500
RxComputationThreadPool-1 | 609 | value = 400
RxComputationThreadPool-1 | 702 | value = 500
RxComputationThreadPool-2 | 702 | value = 600
RxComputationThreadPool-2 | 796 | value = 700
RxComputationThreadPool-1 | 796 | value = 600
RxComputationThreadPool-2 | 905 | value = 800
RxComputationThreadPool-1 | 905 | value = 700
RxComputationThreadPool-1 | 1006 | value = 800
RxComputationThreadPool-2 | 1006 | value = 900
RxComputationThreadPool-1 | 1099 | value = 900
* Pool-2만 혼자서 value를 1000까지 뽑은걸 볼 수 있음
RxComputationThreadPool-2 | 1099 | value = 1000
 */