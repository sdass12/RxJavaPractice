package chap_7.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class DebounceExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();
        String[] data = {"1", "2", "3", "5"};

        Observable<String> source = Observable.concat(
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[0]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[1]),
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[2]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[3]))
                // 값이 발행되고 200ms안에 다른 값이 발행된다면 그 값을 덮어 씌움.
                // 그 후, 다시 200ms동안 값이 발행되지 않나 기다리다가 값이 발행되지 않으면 최종 값만 발행시킴
                .debounce(200L, TimeUnit.MILLISECONDS);

        source.subscribe(Log::it);
        CommonUtils.sleep(1000);

        /*
        RxComputationThreadPool-2 | 627 | value = 1
        // 1과 3사이에 약 400초에 차이가 있음.
        // 즉, 1이 발행 되고 300ms 후 2가 발행되던 중 100ms만에 3이 발행되서 debounce()는 2에 3을 덮어씌움.
        RxComputationThreadPool-2 | 1030 | value = 3
        RxComputationThreadPool-2 | 1133 | value = 5
         */
    }
}
