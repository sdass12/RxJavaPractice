package chap_2;

import io.reactivex.Observable;

public class Just {
    public void emit() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) //just에는 최대 10개의 값이 들어갈 수 있다.
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        Just just = new Just();
        just.emit();
    }
}