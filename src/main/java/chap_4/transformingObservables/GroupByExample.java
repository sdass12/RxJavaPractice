package chap_4.transformingObservables;

import common.Shape;
import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;

public class GroupByExample {
    public static void main(String[] args) {
        String[] objs = {"6", "4", "2-T", "2", "6-T", "4-T"};
        Observable<GroupedObservable<String, String>> source =
                /*
                 * getShape()는 문자열의 끝을 보고 해당하는 문자열을 리턴해주는 함수임
                 * ex) -T -> TRIANGLE, -H -> HEXAGON 등등으로 반환해줌. 마땅한 모양이 없으면 BALL을 리턴
                 * 즉 objs에 있는 값들을 모양에 맞게 그룹으로 묶어줌
                 */
                Observable.fromArray(objs).groupBy(Shape::getShape);

        source.subscribe(obj -> {
            // obj는 GroupedObservable 객체임. 그룹별로 1개씩 생성되므로 obj도 subscribe()를 해줘야 됨
            obj.subscribe(
                    // getKey()는 구분된 그룹을 반환하고, val은 그룹 안에서 각 Observable이 발행한 데이터을 의미함
           val -> System.out.println("GROUP:" + obj.getKey() + "\t Value:" + val));
        });

        /////////////위와 동일하지만 filter()가 추가 됨/////////////
        System.out.println("--------------------------------");

        Observable<GroupedObservable<String, String>> source2 =
                Observable.fromArray(objs).groupBy(Shape::getShape);

        source2.subscribe(obj -> {
            obj.filter(val -> obj.getKey().equals("BALL"))
                    .subscribe(
                    val -> System.out.println("GROUP:" + obj.getKey() + "\t Value:" + val));
        });
    }
}
///////// map(), flatMap(), groupBy() 비교 /////////
//  map() 함수는 1개의 데이터를 다른 값이나 다른 타입으로 변환해줌.
//  flatMap() 함수는 1개의 값을 받아서 여러 개의 데이터(Observable)로 확장해줌.
//  groupBy() 함수는 값들을 받아서 어떤 기준에 맞는 새로운 Observable 다수를 생성함.
/////////////////
/* 결과
GROUP:BALL	 Value:6
GROUP:BALL	 Value:4
GROUP:TRIANGLE	 Value:2-T
GROUP:BALL	 Value:2
GROUP:TRIANGLE	 Value:6-T
GROUP:TRIANGLE	 Value:4-T
---------------------------------
GROUP:BALL	 Value:6
GROUP:BALL	 Value:4
GROUP:BALL	 Value:2
 */
