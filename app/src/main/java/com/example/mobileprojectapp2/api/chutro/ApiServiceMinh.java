package com.example.mobileprojectapp2.api.chutro;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceMinh {
    ApiServiceMinh apiService = new Retrofit.Builder()
            .baseUrl(Const.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceMinh.class);
    @GET("api/phongtrochutro/all")
    Call<List<PhongTroChuTro>> layTatCaPhongTroQuanLy(@Query("idChuTro") int idChuTro);
}
