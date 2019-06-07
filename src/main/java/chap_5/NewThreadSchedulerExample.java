package chap_5;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class NewThreadSchedulerExample {
    public static void main(String[] args) {
        String[] orgs = {"1", "3", "5"};
        Observable.fromArray(orgs)
                .doOnNext(data -> Log.v("Original data : " + data))
                .map(data -> "<<" + data + ">>")
                .subscribeOn(Schedulers.newThread())
                .subscribe(Log::i);
        // 이 sleep()을 없에면 두 스레드의 값이 섞여서 발행됨
        CommonUtils.sleep(500);

        Observable.fromArray(orgs)
                .doOnNext(data -> Log.v("Original data : " + data))
                .map(data -> "##" + data + "##")
                .subscribeOn(Schedulers.newThread())
                .subscribe(Log::i);
        // 이 sleep()을 없에면 두 번째 스레드의 값이 발행되다 말고 메인스레드가 끝남
        CommonUtils.sleep(500);
    }
}
/*
RxNewThreadScheduler-1 | Original data : 1
RxNewThreadScheduler-1 | value = <<1>>
RxNewThreadScheduler-1 | Original data : 3
RxNewThreadScheduler-1 | value = <<3>>
RxNewThreadScheduler-1 | Original data : 5
RxNewThreadScheduler-1 | value = <<5>>
RxNewThreadScheduler-2 | Original data : 1
RxNewThreadScheduler-2 | value = ##1##
RxNewThreadScheduler-2 | Original data : 3
RxNewThreadScheduler-2 | value = ##3##
RxNewThreadScheduler-2 | Original data : 5
RxNewThreadScheduler-2 | value = ##5##
 */