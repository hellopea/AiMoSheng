package com.app.aimosheng.utils;

import android.app.Activity;

import android.os.Build;
import android.util.Log;
import android.view.textclassifier.ConversationActions;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;

public class HttpUtil {
    private static OkHttpClient client;

    private static final HttpUtil httpUtil = new HttpUtil();

    public static HttpUtil getInstance() {
        return httpUtil;
    }

    private HttpUtil() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .followSslRedirects(true)
                .connectionSpecs((Arrays.asList(ConnectionSpec.CLEARTEXT)))

                .build();
    }
    private static ConnectionSpec getConnectionSpec() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).cipherSuites(CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA).build();
        return spec;
    }

    public interface ICallBack {
        void onResponse(JsonResult result) throws JSONException;
    }

    /*-------------------------------------------------------------------------------------------------------
    提供给外部调用的方法*/

    /**
     * post方式提交表单（已自动添加personToken）
     *
     * @param activity  Activity
     * @param url       根路径后面的子路径
     * @param map       要发送的数据Map
     * @param iCallBack 请求成功的回调
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void formPost(Activity activity, String url, Map<String, String> map, final ICallBack iCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        //从SharedPreference中取出personToken添加到表单中
        builder.add("personToken", activity.getSharedPreferences("user", Activity.MODE_PRIVATE).getString("personToken", ""));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                //根路径拼接子路径
                .url(Interface.SERVER_ADDRESS + url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JsonResult result = new Gson().fromJson(response.body().string(), JsonResult.class);
                requestSuccess(activity, result, iCallBack);
            }
        });
    }

    /**
     * get请求（已自动添加personToken）
     *
     * @param activity  Activity
     * @param url       根路径后面的子路径
     * @param map       要发送的数据Map
     * @param iCallBack 请求成功的回调
     */
    public void get(Activity activity, String url, Map<String, String> map, final ICallBack iCallBack) {
        StringBuilder builder = new StringBuilder(url);
        //从SharedPreference中取出personToken拼接到url后面
        builder.append("?personToken=");
        builder.append(activity.getSharedPreferences("user", Activity.MODE_PRIVATE).getString("personToken", ""));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append("&");
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
        }
        Log.d("", builder.toString());
        Request request = new Request.Builder()
                //根路径拼接子路径
                .url(Interface.SERVER_ADDRESS + builder.toString())
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JsonResult result = new Gson().fromJson(response.body().string(), JsonResult.class);
                requestSuccess(activity, result, iCallBack);
            }
        });
    }

    /*-------------------------------------------------------------------------------------------------------*/

    /**
     * 请求成功后，将获取的数据传递给回调方法
     * 回调方法中的内容直接运行在主线程中
     *
     * @param activity  Activity
     * @param result    JsonResult 拿到的数据
     * @param iCallBack 回调接口
     */
    private static void requestSuccess(Activity activity, final JsonResult result, final ICallBack iCallBack) {
        activity.runOnUiThread(() -> {
            if (iCallBack != null) {
                try {
                    iCallBack.onResponse(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


