package com.hbmcc.wangsen.netsupport.util;

import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hbmcc.wangsen.netsupport.base.EnumHttpQ;
import com.hbmcc.wangsen.netsupport.ui.fragment.fifth.FifthTabFragment;
import com.hbmcc.wangsen.netsupport.ui.fragment.first.FirstTabFragment;
import com.hbmcc.wangsen.netsupport.ui.fragment.forth.LoginFragment;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public static String qresult;
    private static final String MEDIA_TYPE = "application/json;charset=utf-8";
    private static final String METHOD_GET = "GET";
    private static OkHttpClient okHttpClient;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static HttpUtil okHttpUtils = new HttpUtil();

    public HttpUtil() {

    }

    public static HttpUtil init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(3000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(3000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(3000, TimeUnit.MILLISECONDS);
        okHttpClient = builder.build();
        return okHttpUtils;
    }

    public String postJsonRequetSynchronous(String jsonlaln, String url, EnumHttpQ enumHttpQ) {
        // 1.拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建 RequestBody 设置提交类型MediaType+json字符串
        RequestBody requestBody = RequestBody.create(MediaType.parse(MEDIA_TYPE), jsonlaln);
        // 3.构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .post(requestBody)
                .build();
        String result = null;
        //4.创建一个Call对象

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            result = response.body().string();
            System.out.println(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void postJsonRequet(String jsonlaln, String url, final EnumHttpQ enumHttpQ) {
        // 1.拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建 RequestBody 设置提交类型MediaType+json字符串
        RequestBody requestBody = RequestBody.create(MediaType.parse(MEDIA_TYPE), jsonlaln);
        // 3.构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .post(requestBody)
                .build();
        //4.创建一个Call对象
        Call call = okHttpClient.newCall(request);
        //5.异步请求enqueue(Callback)
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.e("TAG", "读取失败=" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                qresult = response.body().string();
//                Log.e("TAG", "读取成功=  qresult = response.body().string();");
                switch (enumHttpQ) {
                    case FIFTH_PROBLEM:
                        FifthTabFragment.fragment.handle();
                        break;
                    case THIRD_WIRELESS:

                        break;


                    case LOGIN_LOGIN:
                        LoginFragment.fragment.handle();
                        break;
                    default:
                        break;

                }

            }
        });
    }

    public void getJsonRequet(String jsonlaln, String url) {

        //  构建okHttpClient，相当于请求的客户端，Builder设计模式
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        // 构建一个请求体，同样也是Builder设计模式
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String lalndata = response.body().string();
                Gson gsonservice = new GsonBuilder().serializeNulls().create();
                //Demo demo = gsonservice.fromJson(lalndata, Demo.class);
                //String lal = demo.getErrorCode12();
            }
        });
    }

}
