package chap_8;

import common.CommonUtils;
import common.Log;
import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class BackpressureExample {
    public void usingBuffer(){
        CommonUtils.exampleStart();

        Flowable.range(1, 50_000_000)
                .onBackpressureBuffer(128, () -> {}, BackpressureOverflowStrategy.DROP_OLDEST)
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100);
                    Log.it(data);
                }, err -> Log.e(err.toString()));

        /* usingBuffer() 결과
                    <전략>
        RxComputationThreadPool-1 | 1881 | value = 10
        RxComputationThreadPool-1 | 2054 | value = 11
        RxComputationThreadPool-1 | 2274 | value = 12
        RxComputationThreadPool-1 | 2407 | value = 13
        RxComputationThreadPool-1 | 2525 | value = 14
        RxComputationThreadPool-1 | 2625 | value = 15
        RxComputationThreadPool-1 | 2809 | value = 16
        RxComputationThreadPool-1 | 2959 | value = 17
         */
    }
    public void usingDrop(){
        CommonUtils.exampleStart();

        Flowable.range(1, 50_000_000)
                .onBackpressureDrop()
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100);
                    Log.it(data);
                }, err -> Log.e(err.toString()));
        CommonUtils.sleep(18_000);
        /* usingDrop() 결과
                        <전략>
        RxComputationThreadPool-1 | 13627 | value = 123
        RxComputationThreadPool-1 | 13727 | value = 124
        RxComputationThreadPool-1 | 13827 | value = 125
        RxComputationThreadPool-1 | 13927 | value = 126
        RxComputationThreadPool-1 | 14027 | value = 127
        RxComputationThreadPool-1 | 14127 | value = 128
         */
    }
    public void usingLatest(){
        CommonUtils.exampleStart();

        Flowable.range(1, 50_000_000)
                .onBackpressureLatest()
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100);
                    Log.it(data);
                }, err -> Log.e(err.toString()));
        CommonUtils.sleep(18_000);

        /* usingLatest() 결과
                    <전략>
        RxComputationThreadPool-1 | 12681 | value = 123
        RxComputationThreadPool-1 | 12781 | value = 124
        RxComputationThreadPool-1 | 12881 | value = 125
        RxComputationThreadPool-1 | 12981 | value = 126
        RxComputationThreadPool-1 | 13081 | value = 127
        RxComputationThreadPool-1 | 13181 | value = 128
        RxComputationThreadPool-1 | 13281 | value = 50000000
         */
    }


    public static void main(String[] args) {
        BackpressureExample demo = new BackpressureExample();

        //demo.usingBuffer();
        demo.usingDrop();
        //demo.usingLatest();
    }
}
