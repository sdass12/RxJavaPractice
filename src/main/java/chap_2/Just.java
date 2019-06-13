package chap_2;

import io.reactivex.Observable;

public class Just {
    public void emit() {
        // just()에는 최대 10개의 값이 들어갈 수 있음.
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        Just just = new Just();
        just.emit();

        // 위 처럼 메소드로 만들어서 사용할 수도 있고
        // 이런식으로 main안에서도 쓸 수 있음
        Observable.just(1, 2, 3 ,4)
                .subscribe(System.out::println);


    }
}