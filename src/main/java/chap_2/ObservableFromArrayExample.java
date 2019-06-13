package chap_2;

import io.reactivex.Observable;

import java.util.stream.IntStream;

public class ObservableFromArrayExample {
    public static void main(String[] args) {
        Integer[] arr = {100, 200, 300};
        Observable<Integer> source = Observable.fromArray(arr);
        //100, 200, 300 잘 나옴.
        source.subscribe(System.out::println);

        int[] intArr = {400, 500, 600};

        //결과 값이 [I@e45f292이 나옴.
        Observable.fromArray(intArr).subscribe(System.out::println);

        //intArr을 Integer로 변환시켜주면 400, 500, 600이 잘 나옴.
        Observable.fromArray(toIntegerArray(intArr)).subscribe(System.out::println);
    }
    private static Integer[] toIntegerArray(int[] intArr){
        //스트림을 이용해서 int형 배열을 Integer형 배열로 변환 시켜줌.
        return IntStream.of(intArr).boxed().toArray(Integer[]::new);
    }
}
/* 결과
100
200
300
[I@e45f292
400
500
600
 */