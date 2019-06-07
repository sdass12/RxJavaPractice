package chap_4.creatingObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimerExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();

        //interval()과 유사하지만 interval()은 지속적으로 값을 발행하고
        //timer()는 값을 하나 발행한 후 onComplete() 이벤트를 발생시킨다는 점이 다름.
        Observable<String> source = Observable.timer(500L, TimeUnit.MILLISECONDS)
                //timer()도 interval()과 마찬가지로 0L(Long타입 0)이라는 값을 발행하지만
                //map()을 통해서 nowTime이라는 람다 표현식의 인자 이름을 주고 현재 시각을 발행하게끔 바꿔줌
                .map(nowTime ->{
                    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                            .format(new Date());
                        });
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);

    }
}

/* 결과
RxComputationThreadPool-1 | 826 | value = 2019/05/29 18:00:23
 */