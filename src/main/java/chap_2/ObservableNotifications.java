package chap_2;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ObservableNotifications {
    public static void main(String[] args) {
        Observable<String> source = Observable.just("Red","Green","Yellow");

        Disposable d = source.subscribe(
                v -> System.out.println("onNext() : " + v),
                err -> System.err.println("onError() : err : " + err.getMessage()),
                () -> System.out.println("onComplete()")
        );

        System.out.println("isDisposed() : " + d.isDisposed());
    }
}

/*  결과
onNext() : Red
onNext() : Green
onNext() : Yellow
onComplete()
isDisposed() : true
 */
