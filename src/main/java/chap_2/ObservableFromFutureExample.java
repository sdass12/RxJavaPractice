package chap_2;

import io.reactivex.Observable;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ObservableFromFutureExample {
    public static void main(String[] args) {
        Future<String> future = Executors.newSingleThreadExecutor().submit(() -> {
            Thread.sleep(1000);
            return "Hello Future";
        });

        Observable<String> source = Observable.fromFuture(future);
        source.subscribe(System.out::println);
    }
}
