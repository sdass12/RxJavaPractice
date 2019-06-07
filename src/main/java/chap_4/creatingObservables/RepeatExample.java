package chap_4.creatingObservables;

import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class RepeatExample {
    public static void main(String[] args) {
        String[] balls = {"1","3","5"};

        Observable<String>  source  = Observable.fromArray(balls)
                // 3번 반복시켜줌 ( repeat()라고만 하면 무한 반복)
                .repeat(3);

        source.doOnComplete(() -> Log.d("onComplete"))
                .subscribe(Log::i);


        //////////////heartbeat.Ver1/////////////

        CommonUtils.exampleStart();
        String serverUrl = "https://api.github.com/zen";

        // 2초 간격으로 서버에 ping 보내기
        Observable.timer(2, TimeUnit.SECONDS)
                .map(val -> serverUrl)
                .map(OkHttpHelper::get)
                .repeat()
                .subscribe(res -> Log.it("Ping Result : " + res));
        CommonUtils.sleep(10000);

    }

}
