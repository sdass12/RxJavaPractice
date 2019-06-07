package chap_4.MathematicalAndAggregateOperators;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Timed;

public class TimeIntervalExample {
    public static void main(String[] args) {
        String[] data = {"!", "3", "7"};

        CommonUtils.exampleStart();
        Observable<Timed<String>> source = Observable.fromArray(data)
                .delay(item ->{
                    // doSomething()은 100ms 미만으로 무작위 Thread.sleep()을 시킴
                    CommonUtils.doSomething();
                    return Observable.just(item);
                })
                .timeInterval();

        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
}
/*
main | 198 | value = Timed[time=50, unit=MILLISECONDS, value=!]
main | 224 | value = Timed[time=26, unit=MILLISECONDS, value=3]
main | 236 | value = Timed[time=12, unit=MILLISECONDS, value=7]
 */