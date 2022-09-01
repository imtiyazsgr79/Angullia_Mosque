package com.sg.angulliamosque;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
        private static final String base_url = "http://103.24.4.168:8180/cmms/";//base url live
//    private static final String base_url = "http://192.168.2.20:8089/cmmschecklist4430/";
    private static RetrofitClient instance;
    private final Retrofit retrofit; //retrofit object

    private RetrofitClient() { //constructor8089/cmmschecklist4430

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(new ChuckerInterceptor(context))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()

                .baseUrl(base_url).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;

    }

    public Api getapi() { //defining api function
        return retrofit.create(Api.class);
    }
}
