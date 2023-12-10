package com.example.mobileprojectapp2.api.nguoithue;

import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.ChinhSach;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.TinNhan;
import com.example.mobileprojectapp2.datamodel.VideoReview;
import com.example.mobileprojectapp2.datamodels.PhongTro;

import java.util.ArrayList;

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

public interface ApiServiceNghiem {


    String doman = Const.DOMAIN+"api/";

    ApiServiceNghiem apiService =new Retrofit.Builder()
            .baseUrl(doman)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiServiceNghiem.class);
    @GET("nguoithue/danhsachphonggoiy")
    Call<ArrayList<PhongTro>> danhSachPhongGoiY(@Query("idTaiKhoan") int idtaiKhoan);

    @GET("getvideoreview")
    Call<VideoReview> getVideoReview(@Query("idPhong") int idPhong);

    @Multipart
    @POST("uploadvideoreview")
    Call<Integer> uploadVideoReview(@Part("idPhong")int idPhong,
                                    @Part("loaiVideo")int loadVideo,
                                    @Part MultipartBody.Part file
                                    );
    @Multipart
    @POST("uploadvideoreviewyoutube")
    Call<Integer> uploadVideoReviewYoutube(@Part("idPhong")int idPhong,
                                            @Part("loaiVideo")int loadVideo,
                                           @Part("linkVideo")RequestBody linkVideo
    );
    @Multipart
    @POST("deletevideoreview")
    Call<Integer> deleteVideoReviewYoutube(@Part("idPhong")int idPhong
    );
  }
