package chap_2;

import common.Order;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SingleExample {
    public static void main(String[] args) {
        //Single은 데이터를 한 번 발행하면 바로 종료(onSuccess)됨.
        //onSuccess()는 onNext()와 onComplete()를 통합해 놓은 함수임
        Single<String> source = Single.just("Hello Single");
        source.subscribe(System.out::println);

        //////////////  Observable 클래스에서 Single 클래스 활용 ////////////////

        //1. 기존 Observable에서 Single 객체로 변환하기.
        Observable<String> source2 = Observable.just("1. Hello Single");
        Single.fromObservable(source2)  //Single.fromObservable - 기존 Observable에서 값을 하나 발행하면 onSuccess 이벤트를 호출 후 종료
                .subscribe(System.out::println);

        //2. single() 함수를 호출해 Single 객체 생성하기.
        Observable.just("2. Hello Single")           //Observable.just를 통해서 Observable을 생성
                .single("default item")  //위에서 생성한 Observable에 single() 함수를 호출
                .subscribe(System.out::println);     //single() 함수는 default item을 갖고있다가 Observable에서 값이 발행되지 않을 때 기본 값을 대신 발행함

        //3. first() 함수를 호출해 Single 객체 생성하기.
        String[] colors = {"3. Red","3. Blue","3. Yellow"};
        Observable.fromArray(colors)                 //여러 값을 발행할 수 있는 Observable을 생성
                .first("default value")  //first() 함수를 호출 해 Observable을 Single 객체로 변환 시킴
                .subscribe(System.out::println);     //Observable이 값을 몇 개 발행시킬 수 있든 첫 값만 발행 시키고 onSuccess를 호출한 후 종료

        //4. empty Observable에서 String 객체 생성하기.
        Observable.empty()                              //empty() 함수를 통해 빈 Observable을 생성
                .single("4. default value") //2번과 마찬가지로 single() 함수를 호출
                .subscribe(System.out::println);        //Observable이 발행할 값이 없으니 default value가 발행 되고 onSuccess 됨.

        //5. take() 함수에서 Single 객체 생성하기.
        Observable.just(new Order("5. ORD-1"), new Order("5. ORD-2"))
                .take(1)
                .single(new Order("5. default order"))
                .subscribe(System.out::println);    //Order 클래스에 있는 toString을 불러와서 println 시킴.
    }
}
