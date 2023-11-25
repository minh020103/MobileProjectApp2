package com.example.mobileprojectapp2.api.chutro;

import com.example.mobileprojectapp2.api.Const;
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

    String doman = Const.DOMAIN+"api/";
    ApiServiceNghiem apiService =new Retrofit.Builder()
            .baseUrl(doman)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServiceNghiem.class);
    @GET("danhsachtinnhan")
    Call<ArrayList<TinNhan>> layDanhSachTinNhan(@Query("idPhong")int idPhong);
    @GET("phongtinnhan")
    Call<Integer> layIdPhongTinNhan(@Query("idTaiKhoan1")int idTaiKhoan1,@Query("idTaiKhoan2")int idTaiKhoan2 );

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
    Call<Integer> capNhatTinNhanMoiNhat(@Part("idTaiKhoan") int idTaiKhoan,
                                        @Part("id") int idPhong,
                                        @Part("tinNhanMoiNhat")RequestBody noiDung,
                                        @Part("thoiGian")RequestBody thoiGian);
    @Multipart
    @POST("capnhattrangthaidaxem")
    Call<Integer> capNhatTrangThaiDaXem(@Part("idPhong") int idPhong,
                                        @Part("idTaiKhoan")int idTaiKhoan);

    @GET("kiemtradangnhap")
    Call<TaiKhoan> kiemTraDangNhap(@Query("tenTaiKhoan") String tenTaiKhoan, @Query("matKhau") String matKhau );

    @GET("taikhoan/dangnhap")
    Call<TaiKhoan> dangNhap(@Query("tenTaiKhoan") String tenTaiKhoan, @Query("matKhau") String matKhau );
    @GET("taikhoan/dangnhapfb")
    Call<TaiKhoan> dangNhapFB(@Query("email") String tenTaiKhoan );
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
    @Multipart
    @POST("taophongtinnhan")
    Call<PhongTinNhan> taoPhongTinNhan(@Part("idTaiKhoan1") RequestBody idTaiKhoan1,
                                         @Part("idTaiKhoan2")RequestBody idTaiKhoan2
                                         );

    @GET("layanhvatendoiphuong")
    Call<TaiKhoan> callThongTinDoiPhuong(@Query("idSender") int idSender,@Query("idPhong") int idPhong,@Query("idDoiPhuong") int idDoiPhuong   );
}
