package chap_4.transformingObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ConcatMapExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();

        String[] balls = {"1","3","5"};

        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                // Long Ÿ���� int�� ��ȯ
                .map(Long::intValue)
                // interval()�� ����� ���� balls�� idx������ Ȱ��(Ex - balls[0], balls[1], ...)
                .map(idx -> balls[idx])
                // balls�� ����(3)��ŭ ������ �ƴٸ� onComplete() �̺�Ʈ�� �߻���Ŵ
                .take(balls.length)
                // ������ ����� �� ���� ������ 0.2�ʸ��� �޸� �ٿ���
                .concatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                    .map(notUsed -> ball + "��")
                    // concatMap()�� 2�� ����Ǹ� onComplete() �̺�Ʈ�� �߻���Ŵ
                    .take(2)
                );
        //source.subscribe(Log::it);

        ////////////////// interleaving /////////////////
        Observable<String> source2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .flatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                    .map(notUsed -> ball + "��")
                    .take(2)
                );
        source2.subscribe(Log::it);

        CommonUtils.sleep(2000);
    }
}
/* ���1 - interleaving X
 * concatMap()�� ���͸���(������)�� ������� �ʱ� ������
 * ����� �ð����� ���� 2�� �����尡 ó���� ������ ���� 3�� �����尡 ������ ����
 * �� ���� ���� ������ ������� ó���ؼ� ����� �� �� �ֵ��� ��������
RxComputationThreadPool-2 | 404 | value = 1��
RxComputationThreadPool-2 | 601 | value = 1��
RxComputationThreadPool-3 | 817 | value = 3��
RxComputationThreadPool-3 | 1004 | value = 3��
RxComputationThreadPool-4 | 1210 | value = 5��
RxComputationThreadPool-4 | 1411 | value = 5��
 */

/* ���2 - interleaving O
 * ���1���� �ٸ��� ��������� ���� ��������
 * �ݸ� ó���Ϸ� ������ ���� ������
RxComputationThreadPool-2 | 422 | value = 1��
RxComputationThreadPool-3 | 519 | value = 3��
RxComputationThreadPool-4 | 618 | value = 5��
RxComputationThreadPool-2 | 620 | value = 1��
RxComputationThreadPool-3 | 718 | value = 3��
RxComputationThreadPool-4 | 819 | value = 5��
 */