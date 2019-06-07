package chap_2.subjects;

import io.reactivex.subjects.PublishSubject;

public class PublisherSubjectExample {
    public static void main(String[] args) {
        PublishSubject<String> subject = PublishSubject.create();   //값이 생길 때 마다 바로바로 발행함(기본 값은 없음).
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        subject.onNext("5");
        subject.onComplete();
    }
}
