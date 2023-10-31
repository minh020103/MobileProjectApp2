package com.example.mobileprojectapp2.api.chutro;

import com.example.mobileprojectapp2.api.Const;
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
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServiceMinh {
    ApiServiceMinh apiService = new Retrofit.Builder()
            .baseUrl(Const.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceMinh.class);
    @GET("api/phongtrochutro/all")
    Call<List<PhongTroChuTro>> layTatCaPhongTroQuanLy(@Query("idChuTro") int idChuTro);
    @GET("api/laytatcatienich")
    Call<List<TienIch>> layTatCaTienIch();
    @GET("api/quan/all")
    Call<List<Quan>> layTatCaQuan();
    @GET("api/phuong/layphuongtheoquan")
    Call<List<Phuong>> layTatCaPhuongTheoQuan(@Query("idQuan") int idQuan);
    @Multipart
    @POST("api/phongtro/create")
    Call<Integer> themPhongTroTheoIdChuTro(
            @Part("soPhong") RequestBody soPhong,
            @Part("gia") RequestBody gia,
            @Part("dienTich") RequestBody dienTich,
            @Part("moTa") RequestBody moTa,
            @Part("idQuan") RequestBody idQuan,
            @Part("idPhuong") RequestBody idPhuong,
            @Part("diaChiChiTiet") RequestBody diaChiChiTiet,
            @Part("loaiPhong") RequestBody loaiPhong,
            @Part("soLuongToiDa") RequestBody soLuongToiDa,
            @Part("tienCoc") RequestBody tienCoc,
            @Part("gioiTinh") RequestBody gioiTinh,
            @Part("hinh") List<MultipartBody.Part> hinh,
            @Part("idChuTro") RequestBody idChuTro);
}
