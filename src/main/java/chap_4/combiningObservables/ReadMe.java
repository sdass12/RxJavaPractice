package chap_4.combiningObservables;

public class ReadMe {
    /*
     * 결합 연산자에 들어가기 전 함수들을 먼저 조금씩 알아보고 들어가는게 좋을듯함.
     * 결합 연산자는 여러개의 Observable을 내가 원하는 Observable로 결합해줌. 종류는 다음과 같음
     * zip() 함수 - 입력 Observable에서 데이터를 모두 새로 방핼했을 때 그것을 합해줌
     * combineLatest() 함수 - 처음에 각 Observable에서 발행한 후에는 어디에서 값을 발행하던 최신 값을 갱신함
     * marge() 함수 - 최신 데이터 여부와 상관없이 각 Observable에서 발행하는 데이터를 그대로 출력함
     * concat() 함수 - 입력된 Observable을 Observable 단위로 이어 붙여줌
     */
}
