package chap_7;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ExceptionHandling {
    static String[] grades = {"70", "80", "$100", "93", "12"};

    public void onErrorReturn(){
        Observable<Integer> source = Observable.fromArray(grades)
                .map(data -> Integer.parseInt(data))
                // 에러가 났을 때 미리 정해놓은 기본값을 발행시키고 onComplete()를 발생시킴(에러 난 이후의 값은 발행 X)
                .onErrorReturn(e -> {
                    if(e instanceof NumberFormatException) {
                        e.printStackTrace();
                    }
                    return -1;
                });
        source.subscribe(data -> {
            if(data < 0){
                Log.e("Wrong Data found!");
                // return을 안해주면 음수도 같이 발행됨
                return;
            }
            Log.i("Grade is " + data);
        });
        /* onErrorReturn() 결과
        main | value = Grade is 70
        main | value = Grade is 80
        java.lang.NumberFormatException: For input string: "$100"
            at java.base/java.lang.NumberFormatException.forInputString...생략
        main | error = Wrong Data found!
         */
    }
    public void onError(){
        Observable<Integer> source = Observable.fromArray(grades)
                .map(data -> Integer.parseInt(data));

        // subscribe()에서 두 번째 인자는 ON_ERROR_MISSING 임. 즉 밑에 e는 에러 메시지임
        source.subscribe(
                data -> Log.i("Grade is " + data),
                e -> {
                    if(e instanceof NumberFormatException) {
                        e.printStackTrace();
                    }
                    Log.e("Wrong Data found!!");
                }
        );
        // 결과는 onErrorReturn()과 같으므로 생략
    }
    public void onErrorReturnItem(){
        Observable<Integer> source = Observable.fromArray(grades)
                .map(data -> Integer.parseInt(data))
                // onErrorReturn()과 흡사하지만 Throwable 객체를 인자로 전달하지 않기 때문에 코드가 간결해짐
                // 하지만 예외의 종류는 확인할 수 없음
                .onErrorReturnItem(-1);

        source.subscribe(data -> {
            if(data < 0){
                Log.e("Wrong data found!");
                return;
            }
            Log.i("Grade is " + data);
        });
        /* onErrorReturnItem() 결과
        main | value = Grade is 70
        main | value = Grade is 80
        main | error = Wrong data found!
         */
    }
    public void onErrorResumeNext(){
        String[] saleData = {"100", "200", "A300"};
        // 에러가 발생했을 때 대체시킬 Observable
        Observable<Integer> onParseError = Observable.defer(() -> {
            Log.d("send email to administrator");
            return Observable.just(-1);
        }).subscribeOn(Schedulers.io());

        Observable<Integer> source = Observable.fromArray(saleData)
                .map(data -> Integer.parseInt(data))
                // 에러가 발생했을 때 위에서 선언해논 Observable로 대체
                .onErrorResumeNext(onParseError);

        source.subscribe(data -> {
            if(data < 0){
                Log.e("Wrong Data found!");
                return;
            }
            Log.i("Sales data : " + data);
        });
        CommonUtils.sleep(200);
        /* onErrorResumeNext() 결과
        main | value = Sales data : 100
        main | value = Sales data : 200
        RxCachedThreadScheduler-1 | debug = send email to administrator
        RxCachedThreadScheduler-1 | error = Wrong Data found!
         */
    }
    public static void main(String[] args) {
        ExceptionHandling demo = new ExceptionHandling();

        //demo.onErrorReturn();
        //demo.onError();
        //demo.onErrorReturnItem();
        demo.onErrorResumeNext();
    }
}
