package chap_5;

import common.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExcutorSchedulerExample {
    public static void main(String[] args) {
        final int THREAD_NUM = 10;
        /*
         * java.util.current 패키지에서 제공하는 실행자(Executor)를 변환하여 스케줄러를 생성할 수 있음
         * 하지만 Executor 클래스와 스케줄러의 동작 방식과 다르므로 추천 방법은 아님
         * 기존에 사용하던 Executor 클래스를 재사용할 때만 한정적으로 활용하는 것이 좋음
         */
        String[] data = {"1", "3", "5"};
        Observable<String> source = Observable.fromArray(data);
        Executor excutor = Executors.newFixedThreadPool(THREAD_NUM);

        source.subscribeOn(Schedulers.from(excutor))
                .subscribe(Log::i);
        source.subscribeOn(Schedulers.from(excutor))
                .subscribe(Log::i);
    }
}
/* 결과
pool-1-thread-2 | value = 1
pool-1-thread-2 | value = 3
pool-1-thread-2 | value = 5
pool-1-thread-1 | value = 1
pool-1-thread-1 | value = 3
pool-1-thread-1 | value = 5
 */