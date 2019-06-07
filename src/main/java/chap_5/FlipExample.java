package chap_5;

import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class FlipExample {
    public static void main(String[] args) {
        String[] objs = {"1-S", "2-T", "3-P"};

        Observable<String> source = Observable.fromArray(objs)
                // onNext() 이벤트가 발생하면 원래의 데이터 값을 확인해줌
                .doOnNext(data -> Log.v("Original data = " + data))
                // 구독자가 Observable에 subscribe() 함수를 호출하여 구독할 때 실행되는 스레드를 지정
                // 만약 subscribeOn()으로 지정해주지 않으면 결과에 main스레드가 나옴
                .subscribeOn(Schedulers.newThread())
                // Observable에서 데이터 흐름이 발생하는 스레드를 지정
                // 만약 observeOn()으로 지정해주지 않으면 결과가 모두 뉴 스레드 스케쥴러 1에서 처리가 됨
                .observeOn(Schedulers.newThread())
                .map(Shape::flip);
        source.subscribe(Log::i);
        CommonUtils.sleep(500);
    }
}
/* 결과
// 스케쥴러1 스레드는 위에서 doOnNext()를 해줬기 때문에 출력이 됨
RxNewThreadScheduler-1 | Original data = 1-S
RxNewThreadScheduler-1 | Original data = 2-T
RxNewThreadScheduler-1 | Original data = 3-P
// 위에서 subscribeOn()으로 지정해줬기 때문에 main대신 스케쥴러로 만들어준 스레드가 구독됨
RxNewThreadScheduler-2 | value = (flipped)1-S
RxNewThreadScheduler-2 | value = (flipped)2-T
RxNewThreadScheduler-2 | value = (flipped)3-P
 */