package com.example.mobileprojectapp2.api.chutro;

import com.example.mobileprojectapp2.adapter.chutro.ThongBaoYeuCauDatPhongAdapter;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongNguoiThue;
import com.example.mobileprojectapp2.datamodel.ThongBao;
import com.example.mobileprojectapp2.datamodel.YeuCauDatPhong;
import com.example.mobileprojectapp2.datamodel.fcm.FCMThongBaoDatPhong;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiServiceKiet {
    ApiServiceKiet apiServiceKiet = new Retrofit.Builder()
            .baseUrl(Const.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceKiet.class);

    /* Nguoi Thue */
    @GET("api/phongnguoithue/all")
    Call<List<PhongNguoiThue>> getListNguoiThueTheoIdPhong(@Query("idPhong") int idPhong);
    @GET("api/nguoithue/chitiet ")
    Call<NguoiThue> getChiTietNguoiThueTheoIdPhong(@Query("id") int id);

    /* Thong Bao */
    @GET("api/thongbao/all")
    Call<List<ThongBao>> getListThongBaoTheoIdTaiKhoan(@Query("idTaiKhoanNhan") int idTaiKhoanNhan);
    @GET("api/thongbao/chitiet")
    Call<ThongBao> getChiTietThongBaoTheoId(@Query("id") int id);
    @GET("api/thongbao/xoa")
    Call<ThongBao> xoaThongBaoTheoId(@Query("id") int id);
    @GET("api/thongbao/demthongbaocuataikhoan")
    Call<Integer> demThongBaoKQCuaTaiKhoan(@Query("idTaiKhoan") int idTaiKhoan);
    @GET("api/thongbao/demyeucaudatphong")
    Call<Integer> demThongBaoYCCuaTaiKhoan(@Query("idTaiKhoan") int idTaiKhoan);
    @GET("api/yeucaudatphong/all")
    Call<List<YeuCauDatPhong>> getListYeuCauDangKiPhong(@Query("idTaiKhoan") int idTaiKhoan);
    @GET("api/yeucaudatphong/chitiet")
    Call<YeuCauDatPhong> getYeuCauDangKiPhongById(@Query("id") int id);

    @PUT("api/yeucaudatphong/xacnhandatphong")
    Call<FCMThongBaoDatPhong> xacNhanDatPhong(@Query("id") int id, @Query("idTaiKhoanGui") int idTaiKhoanGui, @Query("idNguoiThue") int idNguoiThue, @Query("myIdTaiKhoan") int myIdTaiKhoan, @Query("idPhong") int idPhong);

    @PUT("api/yeucaudatphong/tuchoi")
    Call<ThongBao> tuChoiDatPhong(@Query("id") int id, @Query("idTaiKhoanGui") int idTaiKhoanGui, @Query("myIdTaiKhoan") int myIdTaiKhoan);
}
