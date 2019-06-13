package chap_8;

import common.GsonHelper;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@RunWith(JUnitPlatform.class)
public class TestAsyncExample {
    @Test
    @DisplayName("test Observable.interval() wrong")
    // 테스트 코드를 비활성화 시킬 경우 @Ignore를 추가함
    //@Ignore
    public void testIntervalWrongWay(){
        Observable<Integer> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(Long::intValue);

        source.doOnNext(Log::d)
                .test()
                // awaitDone()을 이용해서 비동기 코드를 테스트할 수 있음(안 쓰면 에러남)
                .awaitDone(1L, TimeUnit.SECONDS)
                .assertResult(0, 1, 2, 3, 4);
    }

    @Test
    @DisplayName("test Github API on HTTP")
    public void testHttp(){
        final String url = "https://api.github.com/users/sdass12";
        Observable<String> source = Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get)
                .doOnNext(Log::d)
                .map(json -> GsonHelper.parseValue(json,"name"))
                .observeOn(Schedulers.newThread());

        String expected = "sdass";
        source.doOnNext(Log::i)
                .test()
                .awaitDone(3, TimeUnit.SECONDS)
                .assertResult(expected);
    }
}
