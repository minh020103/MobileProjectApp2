package com.example.mobileprojectapp2.Api;

import com.example.mobileprojectapp2.model.ChuTro;
import com.example.mobileprojectapp2.model.TaiKhoan;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface ApiServicePhuc {
    ApiServicePhuc apiService = new Retrofit.Builder()
            .baseUrl(Const.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServicePhuc.class);

    @GET("api/chutro/chitiet")
    Call<ChuTro> getChuTroById(@Query("idTaiKhoan") int idTaiKhoan);

    @PATCH("api/taikhoan/doimatkhau")
    Call<Integer> capNhatMatKhau(@Query("id") int id,@Query("matKhaucu") String matKhaucu,@Query("matKhaumoi") String matKhaumoi);
}
