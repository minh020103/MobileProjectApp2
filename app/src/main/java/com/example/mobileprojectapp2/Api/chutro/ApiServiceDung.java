package com.example.mobileprojectapp2.api.chutro;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.Goi;
import com.example.mobileprojectapp2.datamodel.Phuong;
import com.example.mobileprojectapp2.datamodel.YeuCauDKG;
import com.example.mobileprojectapp2.model.Admin;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServiceDung {
    ApiServiceDung apiServiceDung = new Retrofit.Builder()
            .baseUrl(Const.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceDung.class);

    /* Pakage */
    @GET("api/goi/all")
    Call<List<Goi>> getListPakageAPI();
    @GET("api/goi/chitiet")
    Call<Goi> getPakageByIdAPI(@Query("id") int id);
    @PATCH("api/chutro/xoadichvu")
    Call<ChuTro> xoagoidangdung(@Query("idTaiKhoan") int id);
    @GET("api/thongtinadmin")
    Call<Admin> layThongTinAdmin(@Query("id") int id);
    @Multipart
    @POST("api/yeucaudangkygoi/create")
    Call<YeuCauDKG> yeucaudangkygoi(@Part("idChuTro") RequestBody idChuTro,
                                    @Part("idGoi") RequestBody idGoi,
                                    @Part MultipartBody.Part hinhAnhChuyenKhoan);

    @GET("api/phuong/thongtin")
    Call<Phuong> layPhuongTheoIDAPI(@Query("id") int id);
}
    