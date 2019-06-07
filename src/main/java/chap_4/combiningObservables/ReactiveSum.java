package chap_4.combiningObservables;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;

import java.util.Scanner;

public class ReactiveSum {
    public static void main(String[] args) {
        new ReactiveSum().run();
    }

    public void run(){
        ConnectableObservable<String> source = userInput();
        Observable<Integer> a = source
                // a:로 시작하는 값만 내려보냄
                .filter(str -> str.startsWith("a:"))
                // 내려온 값의 a:부분을 없에줌
                .map(str -> str.replace("a:",""))
                // Integer로 변환
                .map(Integer::parseInt);
        Observable<Integer> b = source
                .filter(str -> str.startsWith("b:"))
                .map(str -> str.replace("b:",""))
                .map(Integer::parseInt);
        // a와 b를 더해줌
        Observable.combineLatest(
                // startWith(0)을 한 이유는 맨 처음 a에 값을 입력했을 때 b에 값이 없어서 값이 생길 때 까지 기다리기 때문에
                // 이를 방지하기 위해 0으로 초기화 시켜서 바로 a에 값이 나오게끔 해줌
                a.startWith(0),
                b.startWith(0),
                (x, y) -> x + y)
                // 위에서 더한 값을 출력해줌
                .subscribe(res -> System.out.println("Result: " + res));
        // connect() 함수를 호출하여 데이터 흐름을 시작
        source.connect();
    }

    public ConnectableObservable<String> userInput(){
        // create()를 이용해 사용자의 console 값을 받아옴
        return Observable.create((ObservableEmitter<String> emitter)-> {
            Scanner sc = new Scanner(System.in);
            while(true){
                System.out.println("Input: ");
                String line = sc.next();
                emitter.onNext(line);

                if(line.indexOf("exit") >= 0){
                    sc.close();
                    break;
                }
            }
        }).publish() ;
    }
}
/* 결과
Result: 0
Input:
a:100
Result: 100
Input:
b:299
Result: 399
Input:
// a의 값을 100에서 1로 바꿈
a:1
// 따라서 299(b) + 1(A) = 300(Result)
Result: 300
Input:
b:1
Result: 2
Input:
exit
 */