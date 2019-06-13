package chap_2.subjects;

import io.reactivex.Observable;
import io.reactivex.subjects.AsyncSubject;

public class AsyncSubjectExample {
    public static void main(String[] args) {
        AsyncSubject<String> subject = AsyncSubject.create();
        //onComplete()가 실행되기 전 마지막 값을 발행함
        subject.subscribe(data -> System.out.println("subscriber #1 => " + data));
        subject.onNext("1");
        subject.onNext("3");
        //onComplete()가 실행되기 전 마지막 값을 발행함
        subject.subscribe(data -> System.out.println("subscriber #2 => " + data));
        subject.onNext("5");
        subject.onComplete();


        ///////// Observable의 구독자로 동작 예제 /////////

        Float[] temperature = {10.1f, 13.4f, 12.5f};
        Observable<Float> source = Observable.fromArray(temperature);

        AsyncSubject<Float> subject2 = AsyncSubject.create();
        subject2.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        source.subscribe(subject2);

        ///////// afterComplete /////////
        AsyncSubject<Integer> subject3 = AsyncSubject.create();
        subject3.onNext(10);
        subject3.onNext(11);
        subject3.subscribe(data -> System.out.println("Subscriber2 #1 => " + data));
        subject3.onNext(12);
        subject3.onComplete();      //모든 data의 값은 12임.
        subject3.onNext(13);    //onComplete() 함수 호출 이후에는 onNext 이벤트를 무시함.
        subject3.subscribe(data -> System.out.println("Subscriber2 #2 => " + data));
        subject3.subscribe(data -> System.out.println("Subscriber2 #3 => " + data));

    }
}
