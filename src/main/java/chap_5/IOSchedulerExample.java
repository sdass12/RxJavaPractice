package chap_5;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.io.File;

public class IOSchedulerExample {
    public static void main(String[] args) {
        String root = "c:\\";
        File[] files = new File(root).listFiles();
        Observable<String> source = Observable.fromArray(files)
        // 디렉토리가 아닌 결과들만 통과시킴
        .filter(f -> !f.isDirectory())
        // 절대경로를 표시해줌
        .map(f -> f.getAbsolutePath())
        // 새로운 스레드를 생성(이걸 안해주면 main 스레드로 결과가 나옴)
        .subscribeOn(Schedulers.io());

        source.subscribe(Log::i);
        CommonUtils.sleep(500);
    }
}
/* 결과
RxCachedThreadScheduler-1 | value = c:\hiberfil.sys
RxCachedThreadScheduler-1 | value = c:\pagefile.sys
RxCachedThreadScheduler-1 | value = c:\swapfile.sys
 */