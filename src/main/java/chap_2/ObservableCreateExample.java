package chap_2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.functions.Consumer;

public class ObservableCreateExample {
    public static void main(String[] args) {
        Observable<Integer> source = Observable.create(
                (ObservableEmitter<Integer> emitter) -> {
                    emitter.onNext(100);    //onNext() 함수를 호출해 차례로 100, 200, 300이라는 데이터를 발행
                    emitter.onNext(200);
                    emitter.onNext(300);
                    emitter.onComplete();   //onComplete() 함수를 호출해 발행을 완료
                }
        );
        //메서드 레퍼런스를 이용해서 소스 코드를 줄임(가장 짧음)
        source.subscribe(System.out::println);

        //람다식을 이용해서 소스 코드를 줄임
        source.subscribe(data -> System.out.println(data));

        //소스 코드를 줄이지 않은 원형
        source.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer data) throws Exception {
                System.out.println(data);
            }
        });
        /*
         * source 는 차가운 Observable임.
         * 첫 번째 문장을 실행시켜도 데이터를 발행시키지 않고,
         * 이 문장이 실행될 때 100, 200, 300의 값을 발행함.
         * 즉, subscribe() 함수를 호출하지 않으면 아무것도 발행되지 않음.
         */
    }
}

/*  결과
100
200
300
100
200
300
100
200
300
 */
