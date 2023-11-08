package com.example.mobileprojectapp2.api.chutro;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.model.ChuTro;
import com.example.mobileprojectapp2.model.PhongTroChuTro2;
import com.example.mobileprojectapp2.model.XacThucChuTro;

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
import retrofit2.http.Part;
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
    Call<Integer> changePassWord(@Query("id") int id, @Query("matKhaucu") String matKhaucu, @Query("matKhaumoi") String matKhaumoi);

    @Multipart
    @POST("api/capnhatthongtinchutrocohinh")
    Call<Integer> editProfileImage(@Part("idTaiKhoan") int idTaiKhoan,
                                   @Part("ten") RequestBody ten,
                                   @Part("soDienThoai") RequestBody soDienThoai,
                                   @Part("soTaiKhoanNganHang") RequestBody soTaiKhoanNganHang,
                                   @Part("tenChuTaiKhoanNganHang") RequestBody tenChuTaiKhoanNganHang,
                                   @Part MultipartBody.Part hinh);
    @Multipart
    @POST("api/capnhatthongtinchutrokhonghinh")
    Call<Integer> editProfileNoImage(@Part("idTaiKhoan") int idTaiKhoan,
                                     @Part("ten") RequestBody ten,
                                     @Part("soDienThoai") RequestBody soDienThoai,
                                     @Part("soTaiKhoanNganHang") RequestBody soTaiKhoanNganHang,
                                     @Part("tenChuTaiKhoanNganHang") RequestBody tenChuTaiKhoanNganHang);
    @GET("api/phongtrochutro/all")
    Call<List<PhongTroChuTro2>> getALlListPhongTro(@Query("idChuTro") int idChuTro);


    @GET("api/xacthucchutro/chitiet")
    Call<XacThucChuTro> getDetailChuTro(@Query("idChuTro") int idChuTro);
}
