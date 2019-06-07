package chap_5.examples;

import java.io.IOException;

import common.CommonUtils;
import common.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpGetExample {
    private final OkHttpClient client = new OkHttpClient();

    private static final String URL_README =
            "https://raw.githubusercontent.com/sdass12/RxJavaPractice/master/README.md";

    public void run() {
        Request request = new Request.Builder()
                .url(URL_README)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(response.body().string());
            }
        });
        CommonUtils.exampleComplete();
    }

    public static void main(String[] args) {
        HttpGetExample demo = new HttpGetExample();
        demo.run();
    }
}
/*
OkHttp https://raw.githubuserc... | value = Hello RxJava!


예제들은 RxJava프로그래밍 - 유동환,박정준 책을 바탕으로 추가적인 주석이나 코드에 약간에 변형을 줘서 만들었습니다.
 */