package chap_5;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ComputationSchedulerExample {
    public static void main(String[] args) {
        String[] orgs = {"1", "3", "5"};
        Observable<String> source = Observable.fromArray(orgs)
                // zipWith() 함수를 이용해서 데이터와 시간을 합칠 수 있음(앞에 예제에서도 많이 쓰임)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        // 사실 이 예제같은 경우는 interval() 함수가 기본적으로 계산 스케쥴러를 사용하기 때문에
        // 굳이 subscribeOn()을 안 써도 결과는 같음

        // 구독 #1
        source.map(item -> "<<" + item + ">>")
                .subscribeOn(Schedulers.computation())
                .subscribe(Log::i);

        // 구독 #2
        source.map(item -> "##" + item + "##")
                .subscribeOn(Schedulers.computation())
                .subscribe(Log::i);


        CommonUtils.sleep(1000);

    }
}
/* 결과
 * 잘 보면 순서가 섞여있음
 * 즉 처리가 거의 동시에 이루어진다는걸 알 수 있음
RxComputationThreadPool-3 | value = <<1>>
RxComputationThreadPool-4 | value = ##1##
RxComputationThreadPool-3 | value = <<3>>
RxComputationThreadPool-4 | value = ##3##
RxComputationThreadPool-4 | value = ##5##
RxComputationThreadPool-3 | value = <<5>>
 */