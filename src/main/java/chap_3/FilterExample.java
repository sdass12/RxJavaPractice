package chap_3;

import io.reactivex.Observable;
import io.reactivex.Single;

public class FilterExample {
    public static void main(String[] args) {
        String[] objs = {"1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND","5 CIRCLE","6 HEXAGON"};

        Observable<String> source = Observable.fromArray(objs)
                .filter(obj -> obj.endsWith("CIRCLE"));
        source.subscribe(System.out::println);

        ///////////////////////////////////////////

        Integer[] nums = {100, 34, 27, 99, 50};

        Observable<Integer> source2 = Observable.fromArray(nums)
                //짝수인 값만 필터링 됨(홀수는 걸러짐)
                .filter(data -> data % 2 == 0);
        source2.subscribe(System.out::println);

        ////////////함수정리//////////////

        Integer[] numbers = {100, 200, 300, 400, 500};
        Single<Integer> single;
        Observable<Integer> source3;

        // 1. first(default) - Observable의 첫 번째 값을 필터함. 만약 값이 없으면 기본 값을 리턴함.
        single = Observable.fromArray(numbers).first(-999);
        single.subscribe(data -> System.out.println("first() value = " + data));  //100

        // 2. last(default) - Observable의 마지막 값(onComplete() 전)을 필터함. 만약 값이 없으면 기본 값을 리턴함.
        single = Observable.fromArray(numbers).last(999);
        single.subscribe(data -> System.out.println("last() value = " + data));   //500

        // 3. take(N) - 최초 N개 값만 필터함.
        source3 = Observable.fromArray(numbers).take(3);
        source3.subscribe(data -> System.out.println("take(3) value = " + data));  //100, 200, 300

        // 4. takeLast(N) - 마지막 N개 값만 필터함.
        source3 = Observable.fromArray(numbers).takeLast(3);
        source3.subscribe(data -> System.out.println("takeLast(3) value = " + data));  //500, 400, 300

        // 5. skip(N) - 최초 N개 값을 건너뜀.
        source3 = Observable.fromArray(numbers).skip(2);
        source3.subscribe(data -> System.out.println("skip(2) value = " + data));  //300, 400, 500

        // 6. skipLast(N) - 마지막 N개 값을 건너뜀.
        source3 = Observable.fromArray(numbers).skipLast(2);
        source3.subscribe(data -> System.out.println("skipLast(2) value = " + data));  //100, 200, 300
    }
}