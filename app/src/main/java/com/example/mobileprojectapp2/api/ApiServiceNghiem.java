package com.example.mobileprojectapp2.api;

import com.example.mobileprojectapp2.datamodel.ChinhSach;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.TinNhan;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServiceNghiem {

    String http = "http://192.168.1.11/3t/laravel/public/api/";
    ApiServiceNghiem apiService =new Retrofit.Builder()
            .baseUrl(http)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServiceNghiem.class);
    @GET("danhsachtinnhan")
    Call<ArrayList<TinNhan>> layDanhSachTinNhan(@Query("idPhong")int idPhong);

    @GET("laytaikhoandoiphuong")
    Call<TaiKhoan> layTaiKhoanDoiPhuong(@Query("idDoiPhuong") int idDoiPhuong);
    @GET("chutro/thongtinchitiet")
    Call<ChuTro> thongTinChiTietChuTro(@Query("idTaiKhoan") int idTaiKhoan);
    @GET("nguoithue/thongtinchitiet")
    Call<NguoiThue> thongTinChiTietNguoiThue(@Query("idTaiKhoan") int idTaiKhoan);

    @Multipart
    @POST("guitinnhan")
    Call<TinNhan> guiTinNhan(@Part("idPhong") int idPhong,
                             @Part("idTaiKhoan") int idTaiKhoan,
                             @Part("noiDung")RequestBody noiDung);


    @GET("danhsachtinnhantheoidtaikhoan")
    Call<ArrayList<PhongTinNhan>> danhSachTinNhanTheoIdTaiKhoan(@Query("idTaiKhoan") int idTaiKhoan);

    @Multipart
    @POST("capnhattinnhanmoinhat")
    Call<Integer> capNhatTinNhanMoiNhat(@Part("id") int idPhong,
                                        @Part("tinNhanMoiNhat")RequestBody noiDung,
                                        @Part("thoiGian")RequestBody thoiGian);
    @Multipart
    @POST("capnhattrangthaidaxem")
    Call<Integer> capNhatTrangThaiDaXem(@Part("idPhong") int idPhong,
                                        @Part("idTaiKhoan")int idTaiKhoan);

    @GET("kiemtradangnhap")
    Call<TaiKhoan> kiemTraDangNhap(@Query("tenTaiKhoan") String tenTaiKhoan, @Query("matKhau") String matKhau );

    @Multipart
    @POST("taotaikhoanchutro")
    Call<ChuTro> taoTaiKhoanChuTro(@Part("ten") RequestBody ten,
                                   @Part("tenTaiKhoan")RequestBody tenTaiKhoan,
                                   @Part("matKhau")RequestBody matKhau,
                                   @Part("email")RequestBody email);
    @Multipart
    @POST("taotaikhoannguoithue")
    Call<NguoiThue> taoTaiKhoanNguoiThue(@Part("ten") RequestBody ten,
                                   @Part("tenTaiKhoan")RequestBody tenTaiKhoan,
                                   @Part("matKhau")RequestBody matKhau,
                                   @Part("email")RequestBody email,
                                    @Part("gioiTinh") RequestBody gioiTinh);
    @GET("chinhsach")
    Call<ChinhSach> xemChinhSach(@Query("id") int id);
    @GET("tatcataikhoan")
    Call<ArrayList<TaiKhoan>> layTatCaTaiKhoan();
}
