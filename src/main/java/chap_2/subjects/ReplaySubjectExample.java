package chap_2.subjects;

import io.reactivex.subjects.ReplaySubject;

public class ReplaySubjectExample {
    public static void main(String[] args) {
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));  //지금까지 발행됐던 값들(1,3)도 가져옴
        subject.onNext("5");
        subject.onComplete();
    }
}
/* 결과
Subscriber #1 => 1
Subscriber #1 => 3
Subscriber #2 => 1
Subscriber #2 => 3
Subscriber #1 => 5
Subscriber #2 => 5
 */