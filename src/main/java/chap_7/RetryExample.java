package chap_7;


import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class RetryExample {
    static String url = "https://api.github.com/zen";

    public void retry(){
        CommonUtils.exampleStart();

        Observable<String> source = Observable.just(url)
                .map(OkHttpHelper::getT)
                // 에러가 발생했을 경우 재시도 동작을 정해진 횟수만큼 시킴
                .retry(5)
                .onErrorReturn(e -> CommonUtils.ERROR_CODE);

        source.subscribe(data -> Log.it("result : " + data));
        /* retry() 결과
        main | 1235 | error = api.github.com
        main | 1237 | error = api.github.com
        main | 1238 | error = api.github.com
        main | 1239 | error = api.github.com
        main | 1240 | error = api.github.com
        main | 1241 | error = api.github.com
        main | 1241 | value = result : -500

        // 결과를 보면 요청 시간이 거의 차이가 없는 것을 확인할 수 있음
         */
    }
    public void restyWithDelay(){
        final int RETRY_MAX = 5;
        final int RETRY_DELAY = 1000;

        CommonUtils.exampleStart();

        Observable<String> source = Observable.just(url)
                .map(OkHttpHelper::getT)
                .retry((retryCnt, e) -> {
                    Log.e("retryCnt = " + retryCnt);
                    CommonUtils.sleep(RETRY_DELAY);

                    return retryCnt < RETRY_MAX ? true: false;
                }).onErrorReturn(e -> CommonUtils.ERROR_CODE);

        source.subscribe(data -> Log.it("result : " + data));

        /* retryWithDelay() 결과
        main | 1225 | error = api.github.com
        main | error = retryCnt = 1
        main | 2226 | error = api.github.com
        main | error = retryCnt = 2
        main | 3226 | error = api.github.com
        main | error = retryCnt = 3
        main | 4226 | error = api.github.com
        main | error = retryCnt = 4
        main | 5226 | error = api.github.com
        main | error = retryCnt = 5
        main | 6228 | value = result : -500
         */
    }
    public void retryUntil(){
        CommonUtils.exampleStart();

        Observable<String> source = Observable.just(url)
                .map(OkHttpHelper::getT)
                // 보통 재시도 로직은 별도의 스레드에서 동작하므로 IO 스케쥴러를 사용
                .subscribeOn(Schedulers.io())
                .retryUntil(() -> {
                    // isNetworkAvailableI() 함수는 구글에 접속해서 인터넷이 동작하는지 간접적으로 확인하는 함수
                    if(CommonUtils.isNetworkAvailable())
                        return true;
                    CommonUtils.sleep(1000);
                    return false;
                });
        source.subscribe(Log::i);

        CommonUtils.sleep(5000);

        /* retryUntil() 결과
        RxCachedThreadScheduler-1 | 1269 | error = api.github.com
        RxCachedThreadScheduler-1 | Network is not available
        RxCachedThreadScheduler-2 | 2282 | error = api.github.com
        RxCachedThreadScheduler-2 | Network is not available
        RxCachedThreadScheduler-1 | 3283 | error = api.github.com
        RxCachedThreadScheduler-1 | Network is not available
        RxCachedThreadScheduler-2 | 4283 | error = api.github.com
        RxCachedThreadScheduler-2 | Network is not available
         */
    }
    public void retryWhen(){
        Observable.create((ObservableEmitter<String> emitter) -> {
            emitter.onError(new RuntimeException("always fails"));
        }).retryWhen(attempts -> {
            return attempts.zipWith(Observable.range(1,3), (n, i) -> i)
            .flatMap(i -> {
                Log.d("delay retry by " + i + " seconds");
                return Observable.timer(i, TimeUnit.SECONDS);
            });
        }).blockingForEach(Log::d);

        /* retryWhen() 결과
        main | debug = delay retry by 1 seconds
        RxComputationThreadPool-1 | debug = delay retry by 2 seconds
        RxComputationThreadPool-2 | debug = delay retry by 3 seconds
         */
    }
    public static void main(String[] args) {
        RetryExample demo = new RetryExample();
        //demo.retry();
        //demo.restyWithDelay();
        //demo.retryUntil();
        demo.retryWhen();
    }

}
