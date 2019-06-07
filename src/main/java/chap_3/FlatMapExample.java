package chap_3;

import io.reactivex.Observable;

public class FlatMapExample {
    public static void main(String[] args) {
        String[] balls = {"1","2","3","5"};

        Observable<String> source = Observable.fromArray(balls)
                .flatMap(ball -> Observable.just(ball + "◇", ball + "◇"));
        source.subscribe(System.out::println);
    }
}
/* 결과
1◇
1◇
2◇
2◇
3◇
3◇
5◇
5◇

 */
