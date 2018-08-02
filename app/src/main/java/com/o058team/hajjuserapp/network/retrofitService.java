package com.o058team.hajjuserapp.network;

import java.net.CookieHandler;
import java.net.CookieManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

/**
 * Created by Mickey on 3/2/17.
 */

public class retrofitService {

//    private final apiRest mApiRest;
//    private final Retrofit mRetrofit;


    public retrofitService() {

//        mRetrofit=new Retrofit.Builder()
//                .baseUrl(BuildConfig.SERVICE_URL)
//                .client(createInterceptor())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();

//        mApiRest=createRestApi();
    }

    public OkHttpClient createInterceptor(){

//        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();

//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        LogHttpInterceptor in=new LogHttpInterceptor();


        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient client=new OkHttpClient.Builder()
                                .addNetworkInterceptor(in)
                                .cookieJar(new JavaNetCookieJar(cookieHandler))
                                .build();


        return client;
    }



//    public apiRest createRestApi(){
//        return mRetrofit.create(apiRest.class);
//    }
//
//    public Retrofit getRetrofit(){
//        return mRetrofit;
//    }
//
//    public apiRest getRestApi(){
//        return mApiRest;
//    }


}
