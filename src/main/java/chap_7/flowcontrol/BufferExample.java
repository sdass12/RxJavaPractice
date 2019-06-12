package chap_7.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BufferExample {
    public static void main(String[] args) {
        String[] data = {"1", "2", "3", "4", "5", "6"};

        CommonUtils.exampleStart();

        Observable<String> earlySource = Observable.fromArray(data)
                .take(3)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
        Observable<String> middleSource = Observable.just(data[3])
                .zipWith(Observable.timer(300L,TimeUnit.MILLISECONDS), (a, b) -> a);
        Observable<String> lateSource = Observable.just(data[4],data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<List<String>> source = Observable.concat(earlySource,middleSource,lateSource)
                //.buffer(3);
                // 2개씩 묶고 1개를 스킵시킴
                //.buffer(2,3);
                // 2개씩 묶고 2개를 스킵시킴
                .buffer(2,4);
        source.subscribe(Log::it);
        CommonUtils.sleep(1000);

        /*
        RxComputationThreadPool-1 | 597 | value = [1, 2, 3]
        RxComputationThreadPool-3 | 1103 | value = [4, 5, 6]

        =================buffer(2,3)=================
        RxComputationThreadPool-1 | 524 | value = [1, 2]  // 2개를 묶고 3번 째(3)를 스킵시킴
        RxComputationThreadPool-3 | 1001 | value = [4, 5]

        ==================buffer(2,4)================
        RxComputationThreadPool-1 | 759 | value = [1, 2]   //2개를 묶고 3,4번 째(3,4)를 스킵시킴
        RxComputationThreadPool-3 | 1404 | value = [5, 6]
         */
    }
}
