package com.example.mobileprojectapp2.api.nguoithue;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongNguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongTroChuTro;
import com.example.mobileprojectapp2.model.KetQuaGuiYeuCauDatPhong;
import com.example.mobileprojectapp2.model.Quan;
import com.example.mobileprojectapp2.datamodels.PhongTro;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.model.YeuCauDatPhong;

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

public interface ApiServicePhuc2 {

    ApiServicePhuc2 apiService = new Retrofit.Builder()
            .baseUrl(Const.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2.class);

    @GET("api/phongnguoithue/all")
    Call<List<PhongNguoiThue>> getNguoiThueTheoPhong(@Query("idPhong") int idPhong);

    @GET("api/nguoithue/danhsachphonggoiytheoquan")
    Call<List<PhongTro>> getDanhSachPhongGoiYTheoQuan(@Query("idTaiKhoan") int idTaiKhoan);

    @GET("api/nguoithue/thongtinchitiet")
    Call<NguoiThue> getThongTinNguoiThue(@Query("idTaiKhoan") int idTaiKhoan);

    @Multipart
    @POST("api/capnhatthongtinnguoithuecohinh")
    Call<Integer> editProfileCoHinh(@Part("idTaiKhoan") int idTaiKhoan,
                                    @Part("ten") RequestBody ten,
                                    @Part("soDienThoai") RequestBody soDienThoai,
                                    @Part MultipartBody.Part hinh);

    @Multipart
    @POST("api/capnhatthongtinnguoithuekhonghinh")
    Call<Integer> editProfileKhongHinh(@Part("idTaiKhoan") int idTaiKhoan,
                                       @Part("ten") RequestBody ten,
                                       @Part("soDienThoai") RequestBody soDienThoai);

    @POST("api/yeucaudatphong/them")
    Call<KetQuaGuiYeuCauDatPhong> yeuCauDatPhong(@Query("idTaiKhoanGui") int idTaiKhoanGui,
                                                 @Query("idTaiKhoanNhan") int idTaiKhoanNhan,
                                                 @Query("idPhong") int idPhong);

    @Multipart
    @POST("api/nguoithue/capnhatphonggoiy")
    Call<Integer> capNhatPhongGoiY(@Part("idTaiKhoan") RequestBody idTaiKhoan,
                                   @Part("idQuan") RequestBody idQuan,
                                   @Part("tienCoc") RequestBody tienCoc,
                                   @Part("gioiTinh") RequestBody gioiTinh);

    @GET("api/phongtrochutro/all")
    Call<List<PhongTroChuTro>> getALlListPhongTroTheoChuTro(@Query("idChuTro") int idChuTro);

    @GET("api/tienich/all/hoatdong")
    Call<List<TienIch>> getAllListTienIch();

    @GET("api/quan/all/hoatdong")
    Call<List<Quan>> getAllListQuan();

    @GET("api/layquantheoid")
    Call<Quan> getQuanById(@Query("id") int id);

    @GET("api/kiemtrayeuthich")
    Call<Integer> checkYeuThich(@Query("idPhong") int idPhong,
                                @Query("idTaiKhoan") int idTaiKhoan);

    @Multipart
    @POST("api/capnhatyeuthichphongtro")
    Call<Integer> updateLike(@Part("idPhong") RequestBody idPhong,
                             @Part("idTaiKhoan") RequestBody idTaiKhoan);

    @GET("api/laydanhsachphongtroyeuthich")
    Call<List<PhongTro>> getAllPhongYeuThich(@Query("idTaiKhoan") int idTaiKhoan);

    @GET("api/timkiemtheonhucau")
    Call<List<com.example.mobileprojectapp2.model.PhongTro>> getDanhSachPhongTimKiemBoLoc(@Query("quan") int quan,
                                                                                          @Query("giaBatDau") int giaBatDau,
                                                                                          @Query("giaKetThuc") int giaKetThuc,
                                                                                          @Query("loaiPhong") int loaiPhong,
                                                                                          @Query("gioiTinh") int gioiTinh,
                                                                                          @Query("tienIch") String tienIch);
}
