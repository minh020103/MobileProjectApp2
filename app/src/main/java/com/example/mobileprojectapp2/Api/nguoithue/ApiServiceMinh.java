package com.example.mobileprojectapp2.api.nguoithue;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.datamodel.Banner;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.datamodel.Quan;

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
    @GET("api/banner/all")
    Call<List<Banner>> layTatCaBanner();
    @GET("api/phongtro/all")
    Call<List<PhongTro>> layTatCaPhongTro(@Query("loaiPhong") int loaiPhong, @Query("arrange") String arrange);
    @GET("api/quan/all")
    Call<List<Quan>> layTatCaQuan();
    @GET("api/phongtro/quan")
    Call<List<PhongTro>> layTatCaPhongTrongQuan(@Query("idQuan") int idQuan,@Query("arrange") String arrange);
    @GET("api/phongtro/random")
    Call<List<PhongTro>> randomRoom();
}
