package com.example.mobileprojectapp2.api.nguoithue;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.datamodel.Banner;
import com.example.mobileprojectapp2.datamodel.FirebaseCloudMessaging;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.datamodel.Quan;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.ThongBao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
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
    @PATCH("api/phongtro/hoatdong")
    Call<Integer> updateHoatDong(@Query("idPhong") int idPhong, @Query("hoatDong") int hoatDong,  @Query("idChuTro") int idChuTro);
    // Khi đăng nhập thành công thì lưu token device lên trên database rồi mới đăng nhập vào ứng dụng
    @POST("api/fcm/savetoken")
    Call<FirebaseCloudMessaging> saveTokenDeviceOfAccount(@Query("token") String token, @Query("idTaiKhoan") int idTaiKhoan);
    // Khi đăng xuất thì phải dùng hàm này khi xóa thành công trên database rồi mới được xóa idTaiKhoan trong thiết bị và đăng xuất
    @DELETE("api/fcm/delete")
    Call<Integer> deleteTokenDeviceOfAccount(@Query("token") String token, @Query("idTaiKhoan") int idTaiKhoan);

    @GET("api/taikhoan/all/type")
    Call<List<TaiKhoan>> layTatCaTaiKhoanTheoLoaiTaiKhoan(@Query("loaiTaiKhoan") int loaiTaiKhoan);

    @GET("api/fcm/gettoken")
    Call<List<FirebaseCloudMessaging>> layTatCaTokenCuaTaiKhoan(@Query("idTaiKhoan") int idTaiKhoan);
}
