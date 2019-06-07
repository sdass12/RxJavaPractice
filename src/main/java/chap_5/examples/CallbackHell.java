package chap_5.examples;

import common.Log;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static common.CommonUtils.GITHUB_ROOT;

public class CallbackHell {
    private static final String FIRST_URL = "https://api.github.com/zen";
    private static final String SECOND_URL = GITHUB_ROOT + "/samples/callback_hell";

    private final OkHttpClient client = new OkHttpClient();

    private Callback onSuccess = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i(response.body().string());
        }
    };

    public void run() {
        Request request = new Request.Builder()
                .url(FIRST_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(response.body().string());

                //콜백을 다시 추가
                Request request = new Request.Builder()
                        .url(SECOND_URL)
                        .build();
                client.newCall(request).enqueue(onSuccess);
            }
        });
    }

    public static void main(String[] args) {
        CallbackHell demo = new CallbackHell();
        demo.run();
    }
}
/*
OkHttp https://api.github.com/... | value = Keep it logically awesome.
OkHttp https://raw.githubuserc... | value = Welecome to callback_hell!
 */