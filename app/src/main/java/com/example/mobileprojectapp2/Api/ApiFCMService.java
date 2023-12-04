package com.example.mobileprojectapp2.api;

import com.example.mobileprojectapp2.datamodel.ResultFCM;
import com.example.mobileprojectapp2.datamodel.fcm.PushNotification;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiFCMService {
    ApiFCMService apiService = new Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiFCMService.class);

        @Headers({"Authorization: key=" + Const.SERVER_KEY, "Content-Type:" + Const.CONTENT_TYPE})
        @POST("/fcm/send")
        Call<ResultFCM> postNotification(@Body PushNotification data);
}
