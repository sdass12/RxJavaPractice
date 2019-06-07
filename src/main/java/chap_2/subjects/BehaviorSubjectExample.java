package chap_2.subjects;

import io.reactivex.subjects.BehaviorSubject;

public class BehaviorSubjectExample {
    public static void main(String[] args) {
        BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data)); //onNext()가 실행되기 전이므로 디폴트 값인 6이 발행 됨.
        subject.onNext("1");                                                    //onNext()가 실행 돼서 1이 발행 됨.
        subject.onNext("3");                                                    //onNext()가 실행 돼서 3이 발행 됨.
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));  //가장 최근인 onNext는 3이므로 3이 발행 됨.
        subject.onNext("5");                                                    //Subscriber #1,2가 함께 5를 방행 함.
        subject.onComplete();
    }
}

/* 결과
Subscriber #1 => 6
Subscriber #1 => 1
Subscriber #1 => 3
Subscriber #2 =>3
Subscriber #1 => 5
Subscriber #2 =>5
 */
