package chap_3;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.util.Scanner;

public class Gugudan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("숫자를 입력해 주세요.");
        Integer dan = sc.nextInt();

        ///////////구구단 reactive Ver.1///////////

        Observable<Integer> source = Observable.range(1, 9); //range(1,9)로 for문을 대체
        //row는 1~9까지 반복 됨
        source.subscribe(row -> System.out.println("ver.1 | " + dan + "*" + row + "=" + dan * row));

        System.out.println("===============================");

        ///////////구구단 reactive Ver.2///////////

        /*
         * Function<T,R> gugudan의 Return은 9개의 줄을 가진 String이므로 Observable<String>으로 함(flatMap()을 이용하기 때문)
         * 즉 Integer로 값을 받아서 그 값을 9줄로 이루어진 String으로 바꿔서 리턴해주는 함수(gugudan)
         */
        Function<Integer, Observable<String>> gugudan = num ->    //gugudan에 Integer로 넣은 값이 num이 됨.
        /*
         * 원래 밑에 줄은 {}안에 있어야 하지만 1줄이므로 생략된거임
         * num은 밑에 줄에서 1~9까지 반복되는 row에 곱해짐
         */
                Observable.range(1, 9).map(row -> num + "*" + row + "=" + dan * row); //num과 dan은 같음(num을 추천)
        /*
         * dan을 flatMap(gugudan)을 이용해서 9줄로 바꾸고 그 값은 source2에 저장 됨.
         */
        Observable<String> source2 = Observable.just(dan).flatMap(gugudan);
        //source2를 구독해서 source2 안에 있는 값을 발행시킴.
        source2.subscribe(data -> System.out.println("ver.2 | " + data));

        System.out.println("===============================");

        ///////////구구단 reactive Ver.3///////////

        Observable<String> source3 = Observable.just(dan)           //3. 밑에서 처리된 값들이 source3로 들어감
                .flatMap(num -> Observable.range(1, 9)  //1. dan을 flatMap()에 넣고 dan은 num이 됨
                .map(row -> num + "*" + row + "=" + dan * row));    //2. Observable.range(1,9)를 row로 바꾼 후 구구단을 만듦
        //4. source3에 들어 있는 값을 구독해서 발행시킴.
        source3.subscribe(data -> System.out.println("ver.3 | " + data));

        System.out.println("===============================");

        ///////////구구단 reactive Ver.4 (resultSelector)///////////

        Observable<String> source4 = Observable.just(dan)
                .flatMap(num -> Observable.range(1,9),
                (gugu, row) -> gugu + "*" + row + "=" + gugu*row); //resultSelector 부분(map()을 호출하지 않고 flatMap()으로 처리)
        source4.subscribe(data -> System.out.println("ver.4 | " + data));

        sc.close();
    }
}
