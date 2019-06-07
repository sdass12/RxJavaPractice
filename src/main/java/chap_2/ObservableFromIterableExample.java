package chap_2;

import common.Order;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ObservableFromIterableExample { //fromIterable은 반복자(Iterator)를 반환해줌.
    public static void main(String[] args) {
        //List 사용
        List<String> names = new ArrayList<>();
        names.add("Jerry");
        names.add("William");
        names.add("Bob");

        Observable<String> source = Observable.fromIterable(names);
        source.subscribe(System.out::println);

        //Set 사용
        Set<String> cities = new HashSet<>();
        cities.add("Seoul");
        cities.add("London");
        cities.add("Paris");

        Observable<String> source2 = Observable.fromIterable(cities);
        source2.subscribe(System.out::println);

        //BlockingQueue 사용
        BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(100);
        orderQueue.add(new Order("ORD-1"));
        orderQueue.add(new Order("ORD-2"));
        orderQueue.add(new Order("ORD-3"));

        Observable<Order> source3 = Observable.fromIterable(orderQueue);
        source3.subscribe(System.out::println);

    }


}


