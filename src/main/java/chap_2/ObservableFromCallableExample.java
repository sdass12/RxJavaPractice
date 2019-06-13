package chap_2;

import io.reactivex.Observable;

import java.util.concurrent.Callable;

public class ObservableFromCallableExample {
    public static void main(String[] args) {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return "Hello Callable";
        };

        Observable<String> source = Observable.fromCallable(callable);
        source.subscribe(System.out::println);

    }
}
// 결과
// Hello Callable