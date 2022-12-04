package org.hse.baseproject;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpRequestSender{
    private final static String TAG = "RequestSender";
    private final OkHttpClient client = new OkHttpClient();

    public Future<String> get(String requestString){
        Request request = new Request.Builder().url(requestString).build();
        Call call = client.newCall(request);

        CompletableFuture<String> f = new CompletableFuture<>();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"error in request", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())
                    throw new IOException(String.format(
                            "Unexpected code: %s",
                            response.code()
                    ));

                assert response.body() != null;
                String bodyString = response.body().string();

                f.complete(bodyString);
            }
        });

        return f;
    }
}
