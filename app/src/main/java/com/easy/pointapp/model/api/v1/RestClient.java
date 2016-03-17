package com.easy.pointapp.model.api.v1;

import com.easy.pointapp.model.PointRestService;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Reader;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Igor on 30.06.2015.
 */
public class RestClient {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final Retrofit RETROFIT_SERVICE = new Retrofit.Builder()
            .baseUrl("http://77.37.212.235:3000/").client(new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();

    OkHttpClient client = new OkHttpClient();

    public Reader post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().charStream();
    }

    public static PointRestService getService() {
        return RETROFIT_SERVICE.create(PointRestService.class);
    }
}
