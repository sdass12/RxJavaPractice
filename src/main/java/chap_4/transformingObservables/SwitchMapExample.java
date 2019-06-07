package chap_4.transformingObservables;

import common.CommonUtils;
import common.Log;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class SwitchMapExample {
    public static void main(String[] args) {
        CommonUtils.exampleStart();

        String balls[] = {"1","3","5"};

        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                // balls�� ũ�⸸ŭ balls�ȿ� �ִ� ������ ����
                .take(balls.length)
                // �߰� ó�������� �������
                .doOnNext(Log::dt)
                .switchMap(ball -> Observable.interval(200L,TimeUnit.MILLISECONDS)
                        /*
                         * 0.2�ʸ��� ������ ������ ���� �޸� ����.
                         * ��, ó���ϴ� ���� ���ο� ��û�� �´ٸ� ó���ϴ� ���� �����ϰ� ���ο� ��û�� ó����.
                         */
                    .map(notUsed -> ball + "��")
                    .take(2)
                );
        source.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }
}

/*
 * 100ms���� ball�� ����ϰ� 200ms���� ball�� �޸� �ٿ���
 * ù ��° interval()���� 1, 3, 5�� ���� ���� �Ȱ� Ȯ�� �� �� ����
 * �� ��, 1�� �޸� �߰����ִ� �������� 3�� �޸� ���̶�� ��û�� �����鼭 1�� ó���� ������Ŵ
 * 3 ���� ���������� �����ǰ� 5�� �޸� �ٿ���(take(2)�̱� ������ 2�� �ݺ�)
 *
 * ���� �������� �̸��� ���� �����͸� �����ϴ� ������� �� ���� �����ϴ� �����尡 �ٸ��ٴ� ���� �� �� ����
 * concatMap(), flatMap(), switchMap() �Լ����� ������� ���� 2, 3, 4���� �߻��ߴ� ���� Ȯ���� �� ����
 * �� ������ 1�� ������� �����͸� �����ϴµ� �翵�Ʊ� �����̿���.
���
RxComputationThreadPool-1 | 261 | debug = 1
RxComputationThreadPool-1 | 362 | debug = 3
RxComputationThreadPool-1 | 463 | debug = 5
RxComputationThreadPool-4 | 665 | value = 5��
RxComputationThreadPool-4 | 864 | value = 5��
 */