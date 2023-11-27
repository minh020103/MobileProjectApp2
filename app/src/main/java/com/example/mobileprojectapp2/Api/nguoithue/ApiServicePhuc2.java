package com.example.mobileprojectapp2.api.nguoithue;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongNguoiThue;
import com.example.mobileprojectapp2.model.PhongTro;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServicePhuc2 {

    ApiServicePhuc2 apiService = new Retrofit.Builder()
            .baseUrl(Const.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2.class);

    @GET("api/phongnguoithue/all")
    Call<List<PhongNguoiThue>> getNguoiThueTheoPhong(@Query("idPhong") int idPhong);

    @GET("api/nguoithue/danhsachphonggoiy")
    Call<List<PhongTro>> getDanhSachPhongGoiY(@Query("idTaiKhoan") int idTaiKhoan);
}
