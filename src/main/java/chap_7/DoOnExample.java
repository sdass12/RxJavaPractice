package chap_7;

import common.Log;
import io.reactivex.Observable;

public class DoOnExample {
    public static void main(String[] args) {
        String[] orgs = {"1","3","5"};
        Observable<String> source = Observable.fromArray(orgs);

        source.doOnNext(data -> Log.d("onNext()", data))
                .doOnComplete(() -> Log.d("OnComplete()"))
                .doOnError(e -> Log.e("onError()", e.getMessage()))
                .subscribe(Log::i);

        /////////////////// withError ///////////////////
        System.out.println("----------------------------------------------");

        Integer[] divider = {10, 5, 0};

        Observable.fromArray(divider)
                .map(div -> 1000 / div)
                .doOnNext(data -> Log.d("onNext()", data))
                .doOnComplete( () -> Log.d("onComplete()"))
                .doOnError(e-> Log.e("onError()", e.getMessage()))
                .subscribe(Log::i);


    }
}
/* 결과
main | onNext() | debug = 1
main | value = 1
main | onNext() | debug = 3
main | value = 3
main | onNext() | debug = 5
main | value = 5
main | debug = OnComplete()
----------------------------------------------
main | onNext() | debug = 100
main | value = 100
main | onNext() | debug = 200
main | value = 200
main | onError() | error = / by zero
io.reactivex.exceptions.OnErrorNotImplementedException: The exception was not handled due to missing onError handler in the subscribe() method call. Further reading: https://github.com/ReactiveX/RxJava/wiki/Error-Handling | java.lang.ArithmeticException: / by zero
	at io.reactivex.internal.functions.Functions$OnErrorMissingConsumer.accept(Functions.java:704)
 */
