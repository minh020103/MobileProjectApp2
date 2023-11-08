package com.example.mobileprojectapp2.api.chutro;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.datamodel.Phuong;
import com.example.mobileprojectapp2.datamodel.Quan;
import com.example.mobileprojectapp2.datamodel.TienIch;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServiceMinh {
    ApiServiceMinh apiService = new Retrofit.Builder()
            .baseUrl(Const.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceMinh.class);
    @GET("api/phongtrochutro/phantrang")
    Call<List<PhongTroChuTro>> layTatCaPhongTroQuanLy(
            @Query("idChuTro") int idChuTro,
            @Query("page") int page,
            @Query("quantity") int quantity
    );
    @GET("api/laytatcatienich")
    Call<List<TienIch>> layTatCaTienIch();
    @GET("api/quan/all")
    Call<List<Quan>> layTatCaQuan();
    @GET("api/phuong/layphuongtheoquan")
    Call<List<Phuong>> layTatCaPhuongTheoQuan(@Query("idQuan") int idQuan);
    @Multipart
    @POST("api/phongtro/create")
    Call<Integer> themPhongTroTheoIdChuTro(
            // Thông tin phòng
            @Part("soPhong") RequestBody soPhong,
            @Part("gia") RequestBody gia,
            @Part("dienTich") RequestBody dienTich,
            @Part("moTa") RequestBody moTa,
            @Part("idQuan") RequestBody idQuan,
            @Part("idPhuong") RequestBody idPhuong,
            @Part("gioiTinh") RequestBody gioiTinh,
            @Part("diaChiChiTiet") RequestBody diaChiChiTiet,
//            @Part("loaiPhong") RequestBody loaiPhong,
            @Part("soLuongToiDa") RequestBody soLuongToiDa,
            @Part("tienCoc") RequestBody tienCoc,
//            @Part("gioiTinh") RequestBody gioiTinh, thêm mặc định là tất cả
            @Part("tienDien") RequestBody tienDien,
            @Part("tienNuoc") RequestBody tienNuoc,
            //Hình ảnh của phòng
            @Part List<MultipartBody.Part> hinh,
            //Tiện ích của phòng
            @Part("tienIch") RequestBody listTienIch,
            //ID chủ phòng
            @Part("idChuTro") RequestBody idChuTro);
    @GET("api/phongbinhluan/all")
    Call<List<PhongBinhLuan>> layTatCaBinhLuanCuaPhong(
            @Query("idPhong") int idPhong,
            @Query("page") int page,
            @Query("quantity") int quantity
    );
    @PUT("api/phongbinhluan/create")
    Call<PhongBinhLuan> themBinhLuanChoPhong(@Query("idPhong") int idPhong, @Query("idTaiKhoan")int idTaiKhoan, @Query("noiDungBinhLuan") String noiDungBinhLuan);

}
