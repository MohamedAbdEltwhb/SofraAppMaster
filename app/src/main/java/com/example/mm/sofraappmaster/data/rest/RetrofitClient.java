package com.example.mm.sofraappmaster.data.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "http://ipda3.com/sofra/api/v1/";
    private static Retrofit retrofit = null;

    private static RetrofitClient mInstance;

    private RetrofitClient (){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public ApiServices getApiServices(){
        return retrofit.create(ApiServices.class);
    }
}
