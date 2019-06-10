package chap_7;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class DoOnExample {
    /*
     * 이벤트 로깅 함수들 모음
     */

    static String[] orgs = {"1", "3", "5"};
    static String[] orgs2 = {"1", "3", "5", "2", "6"};

    public void basic(){
        Observable<String> source = Observable.fromArray(orgs);

        source.doOnNext(data -> Log.d("onNext()", data))
                .doOnComplete(() -> Log.d("OnComplete()"))
                .doOnError(e -> Log.e("onError()", e.getMessage()))
                .subscribe(Log::i);
        /* basic() 결과
        main | onNext() | debug = 1
        main | value = 1
        main | onNext() | debug = 3
        main | value = 3
        main | onNext() | debug = 5
        main | value = 5
        main | debug = OnComplete()
         */
    }
    public void withError(){
        Integer[] divider = {10, 5, 0};

        Observable.fromArray(divider)
                .map(div -> 1000 / div)
                .doOnNext(data -> Log.d("onNext()", data))
                .doOnComplete(() -> Log.d("onComplete()"))
                .doOnError(e -> Log.e("onError()", e.getMessage()))
                .subscribe(Log::i);
        /* withError() 결과
        main | onNext() | debug = 100
        main | value = 100
        main | onNext() | debug = 200
        main | value = 200
        main | onError() | error = / by zero
        io.reactivex.exceptions.OnErrorNotImplementedException: The exception was not handled due to missing onError handler in the subscribe() method call. Further reading: https://github.com/ReactiveX/RxJava/wiki/Error-Handling | java.lang.ArithmeticException: / by zero
            at io.reactivex.internal.functions.Functions$OnErrorMissingConsumer.accept(Functions.java:704)...중략
         */
    }
    public void doOnEach(){
        Observable<String> source = Observable.fromArray(orgs);

        // Notification<T> 객체를 전달해서 이벤트별로 구분하여 처리하는 방법
        source.doOnEach(noti -> {
            if (noti.isOnNext()) Log.d("onNext()", noti.getValue());
            if (noti.isOnComplete()) Log.d("onComplete()");
            if (noti.isOnError()) Log.d("onError", noti.getError().getMessage());
        }).subscribe(Log::i);
        /* doOnEach() 결과
        main | onNext() | debug = 1
        main | value = 1
        main | onNext() | debug = 3
        main | value = 3
        main | onNext() | debug = 5
        main | value = 5
        main | debug = onComplete()
         */
    }
    public void doOnEachWithoutNotification(){
        Observable<String> source = Observable.fromArray(orgs);
        source.doOnEach(new Observer<String>() {
            public void onSubscribe(Disposable d) {
                // doOnEach() 에서는 onSubscribe() 함수가 호출되지 않음.
            }
            public void onNext(String value) {
                Log.d("onNext()", value);
            }
            public void onError(Throwable e) {
                Log.e("onError()", e.getMessage());
            }
            public void onComplete() {
                Log.d("onComplete");
            }
        }).subscribe(Log::i);
        /* doOnEachWithoutNotification() 결과
        main | onNext() | debug = 1
        main | value = 1
        main | onNext() | debug = 3
        main | value = 3
        main | onNext() | debug = 5
        main | value = 5
        main | debug = onComplete
         */
    }
    public void doOnSubscribeAndDispose(){
        System.out.println("-----------------doOnSubscribeAndDispose()------------------");


        Observable<String> source = Observable.fromArray(orgs2)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a,b) -> a)
                .doOnSubscribe(d -> Log.d("onSubscribe()"))
                .doOnDispose(() -> Log.d("onDispose()"));
        Disposable d = source.subscribe(Log::i);

        CommonUtils.sleep(200);
        d.dispose();
        // 300ms를 더 기다려줬지만 위에서 구독을 취소했기 때문에 더 이상 값이 발행되지 않음
        CommonUtils.sleep(300);
        /* doOnSubscribeAndDispose() 결과
        main | debug = onSubscribe()
        RxComputationThreadPool-1 | value = 1
        RxComputationThreadPool-1 | value = 3
        main | debug = onDispose()
        // 구독이 취소된 이후로 다른 값이 발행되지 않은걸 확인할 수 있음
         */
    }
    public void doOnLifecycle(){
        Observable<String> source = Observable.fromArray(orgs2)
                .zipWith(Observable.interval(100L,TimeUnit.MILLISECONDS), (a,b) -> a)
                // 결과는 doOnSubscribeAndDispose()와 같지만 둘을 각각 부르는게 아닌 한번에 부른다는게 다름
                .doOnLifecycle(
                        d -> Log.d("onSubscribe()"), () -> Log.d("onDispose()"));
        Disposable d = source.subscribe(Log::i);

        CommonUtils.sleep(200);
        d.dispose();
        CommonUtils.sleep(300);

        // 결과는 doOnSubscribeAndDispose()와 같으므로 생략
    }
    public void doOnTerminate(){
        Observable<String> source = Observable.fromArray(orgs);

        // Observable이 끝나는 조건인 onComplete 혹은 onError 이벤트가 발생했을 때(정확히는 발생하기 직전) 실행하는 함수
        source.doOnTerminate(() -> Log.d("onTerminate"))
                .doOnComplete(() -> Log.d("onComplete()"))
                .doOnError(e -> Log.d("onError()", e.getMessage()))
                .subscribe(Log::i);
        /* doOnTerminate() 결과
        main | value = 1
        main | value = 3
        main | value = 5
        main | debug = onTerminate
        main | debug = onComplete()
         */
    }
    public static void main(String[] args) {
       DoOnExample demo = new DoOnExample();
       demo.basic();
       demo.withError();
       demo.doOnEach();
       demo.doOnEachWithoutNotification();
       demo.doOnSubscribeAndDispose();
       demo.doOnLifecycle();
       demo.doOnTerminate();
    }
}