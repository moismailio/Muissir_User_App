package com.o058team.hajjuserapp.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Mickey on 3/10/17.
 */

public class LogHttpInterceptor implements Interceptor {

    private HttpLoggingInterceptor interceptor;

    public LogHttpInterceptor() {
        interceptor = new HttpLoggingInterceptor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        RequestBody body = chain.request().body();

        if (body instanceof MultipartBody)
        {
            HttpLoggingInterceptor.Level level = interceptor.getLevel();
            setLevel(HttpLoggingInterceptor.Level.BASIC);
            Response response = interceptor.intercept(chain);
            setLevel(level);
            return response;
        }

        setLevel(HttpLoggingInterceptor.Level.BODY);
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter("lang_id","2").build();
        request = request.newBuilder().url(url).build();

        return chain.proceed(request);

//        return interceptor.intercept(chain);
    }

    public void setLevel(HttpLoggingInterceptor.Level level) {
        interceptor.setLevel(level);
    }



}
