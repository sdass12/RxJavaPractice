package chap_5;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SingleThreadSchedulerExample {
    public static void main(String[] args) {
        Observable<Integer> numbers = Observable.range(100,5);
        Observable<String> chars = Observable.range(0,5)
                // range()는 Integer타입을 반환하기 때문에 String으로 변환해줌
                .map(CommonUtils::numberToAlphabet);

        numbers.subscribeOn(Schedulers.single())
                .subscribe(Log::i);
        chars.subscribeOn(Schedulers.single())
                .subscribe(Log::i);
        CommonUtils.sleep(500);
    }
}
/* 결과
// 트램펄린 스케줄러와 유사해보이지만 스레드가 새로 만들어진 스레드인 것을 알 수 있음
// 싱글 스케줄러는 이름처럼 한 개의 스레드만 생성해서 구독요청이 여러번 오면 스레드를 중복 사용해서 처리함
RxSingleScheduler-1 | value = 100
RxSingleScheduler-1 | value = 101
RxSingleScheduler-1 | value = 102
RxSingleScheduler-1 | value = 103
RxSingleScheduler-1 | value = 104
RxSingleScheduler-1 | value = A
RxSingleScheduler-1 | value = B
RxSingleScheduler-1 | value = C
RxSingleScheduler-1 | value = D
RxSingleScheduler-1 | value = E
 */