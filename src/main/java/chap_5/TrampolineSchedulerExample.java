package chap_5;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TrampolineSchedulerExample {
    public static void main(String[] args) {
        String[] orgs = {"1", "3", "5"};
        Observable<String> source =Observable.fromArray(orgs);

        // 트램펄린 스케줄러는 새로운 스레드를 생성하지 않고 현재 스레드에 무한한 크기의 대기 행렬(Queue)을 생성하는 스케줄러임
        // 새로운 스레드를 생성하지 않는다는 것과 대기 행렬을 자동으로 만들어 준다는 것이 다른 스케줄러들과의 차이임

        // 구독 #1
        source.subscribeOn(Schedulers.trampoline())
                .map(data -> "<<" + data + ">>")
                .subscribe(Log::i);
        // 구독 #2
        source.subscribeOn(Schedulers.trampoline())
                .map(data -> "##" + data + "##")
                .subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
/* 결과
// 큐에 작업을 넣은 후 1개씩 꺼내어 동작하므로 첫 번째 구독과 두 번째 구독의 실행 순서가 바뀌는 경우는 발생하지 않음
main | value = <<1>>
main | value = <<3>>
main | value = <<5>>
main | value = ##1##
main | value = ##3##
main | value = ##5##
 */