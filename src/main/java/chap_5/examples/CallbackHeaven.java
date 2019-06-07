package chap_5.examples;

import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static common.CommonUtils.GITHUB_ROOT;

public class CallbackHeaven {
    private static final String FIRST_URL = "https://api.github.com/zen";
    private static final String SECOND_URL = GITHUB_ROOT + "/samples/callback_heaven";

    public static void main(String[] args) {
        CommonUtils.exampleStart();
        Observable<String> source = Observable.just(FIRST_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get)
                .concatWith(Observable.just(SECOND_URL)
                    .map(OkHttpHelper::get));
        source.subscribe(Log::it);

        ///////////////////// 스케줄러를 이용해 스레드를 나눠서 처리 ////////////////////////
        System.out.println("-----------------------------------------------------------");


        Observable<String> first = Observable.just(FIRST_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);
        Observable<String> second = Observable.just(SECOND_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);

        Observable.zip(first, second, (a,b) -> ("\n>> " + a + "\n>> " + b))
            .subscribe(Log::it);


        CommonUtils.sleep(5000);
    }
}
/* 결과  (실제 결과에서는 각 스레드 결과가 섞여서 나오지만 보기 편하게 하기 위해 각각에 실행 결과를 넣음)
RxCachedThreadScheduler-1 | 2076 | value = Approachable is better than simple.
RxCachedThreadScheduler-1 | 2685 | value = WELCOME TO CALLBACK_HEAVEN by RXJAVA!!
-------------------------------------------------------
RxCachedThreadScheduler-2 | 1841 | value =
>> Avoid administrative distraction.
>> WELCOME TO CALLBACK_HEAVEN by RXJAVA!!
 */