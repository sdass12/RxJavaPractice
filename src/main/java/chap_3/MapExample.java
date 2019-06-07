package chap_3;


import common.Log;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MapExample {
    public static void main(String[] args) {
        String[] balls = {"1", "2", "3", "5"};
        Observable<String> source = Observable.fromArray(balls)
                .map(ball -> ball + "◇");
        source.subscribe(Log::i);  //Log.i => System.out.println(getThreadName() + " | value = " + obj);

        //////////////////Function<T,R>을 이용해서 인터페이스를 분리//////////////////

        Function<String, String> getDiamond = ball -> ball + "◇";  //ball의 값을 입력받아 ball◇ 형태로 반환
                                                  //{ball + "◇"};원래는 이런식으로 {}안에 써야하지만 문장이 한 줄일 경우는 생략 가능
        Observable<String> source2 = Observable.fromArray(balls)
                .map(getDiamond); //getDiamond 함수로 매핑
        source2.subscribe(System.out::println);

        ////////////////데이터 타입 추론////////////////

        Function<String, Integer> ballToIndex = ball -> {
            switch(ball){
                case "RED":     return 1;
                case "YELLOW":  return 2;
                case "GREEN":   return 3;
                case "BLUE":    return 5;
                default:        return -1;
            }
        };

        String[] colorBalls = {"RED","YELLOW","GREEN","BLUE"};
        Observable<Integer> source3 = Observable.fromArray(colorBalls)
                .map(ballToIndex);
        source3.subscribe(System.out::println);  //컴파일러가 타입을 추론해서 타입 변환을 따로 명시하지 않아도
                                                 //알아서 String이 Integer로 변환 됨.
    }
}
/* 결과
main | value = 1◇ => Log.i를 사용해서 좀 더 보기 좋게 출력
main | value = 2◇
main | value = 3◇
main | value = 5◇
1◇  => System.out::println 만으로 출력
2◇
3◇
5◇
1
2
3
5
 */