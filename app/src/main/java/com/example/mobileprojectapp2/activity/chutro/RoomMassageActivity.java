package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.TinNhanAdapter;
import com.example.mobileprojectapp2.api.ApiServiceNghiem;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.TinNhan;
import com.makeramen.roundedimageview.RoundedImageView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomMassageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<TinNhan> arrayList;
    TinNhanAdapter tinNhanAdapter;
    private int senderId;
    private int idDoiPhuong;
    private int idPhong;
    AppCompatImageView ic_back;
    TextView textName;
    EditText inputMess;
    AppCompatImageView sendMess;
    RoundedImageView avt_doiPhuong;
    AppCompatImageView info;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_massage);
        anhXa();
        setDuLieu();
        setSuKienGuiTinNhan();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
    private void capNhatTinNhanNew(int idPhong, String noiDung, String thoiGian){
        RequestBody noiDungAPi = RequestBody.create(MediaType.parse("multipart/form-data"),inputMess.getText().toString());
        RequestBody thoiGianApi = RequestBody.create(MediaType.parse("multipart/form-data"),thoiGian);
        Call<Integer> call = ApiServiceNghiem.apiService.capNhatTinNhanMoiNhat(idPhong,noiDungAPi,thoiGianApi);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                inputMess.setText("");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                thongBao("Lỗi");
            }
        });
    }
    private String formatDate(Timestamp timestamp){
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm");
        return newFormat.format(date);
    }
    private void setSuKienGuiTinNhan(){
        sendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inputMess.getText().toString().isEmpty()){
                    RequestBody noiDung = RequestBody.create(MediaType.parse("multipart/form-data"),inputMess.getText().toString());
                    Call<TinNhan> call = ApiServiceNghiem.apiService.guiTinNhan(idPhong,senderId,noiDung);
                    call.enqueue(new Callback<TinNhan>() {
                        @Override
                        public void onResponse(Call<TinNhan> call, Response<TinNhan> response) {

                        capNhatTinNhanNew(idPhong,inputMess.getText().toString(),formatDate(response.body().getCreated_at()));
                        }

                        @Override
                        public void onFailure(Call<TinNhan> call, Throwable t) {
                            thongBao("Không Thể Gửi");
                        }
                    });
                }
            }
        });
    }
    private void capNhatTrangThaiDaXem(){
        Call<Integer> call = ApiServiceNghiem.apiService.capNhatTrangThaiDaXem(idPhong,senderId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
    private void setDuLieu(){
        sharedPreferences = getApplicationContext().getSharedPreferences(Const.PRE_LOGIN,MODE_PRIVATE);
        senderId = sharedPreferences.getInt("idTaiKhoan", -1);
        idDoiPhuong= getIntent().getIntExtra("key",-1);
        idPhong=getIntent().getIntExtra("phong",-1);;
        capNhatTrangThaiDaXem();
        arrayList = new ArrayList<>();
        tinNhanAdapter = new TinNhanAdapter(this,arrayList,R.layout.cardview_send_message,R.layout.cardview_recieve_message,senderId);
        Call<ArrayList<TinNhan>> call = ApiServiceNghiem.apiService.layDanhSachTinNhan(idPhong);
        call.enqueue(new Callback<ArrayList<TinNhan>>() {
            @Override
            public void onResponse(Call<ArrayList<TinNhan>> call, Response<ArrayList<TinNhan>> response) {
                if(response.body().size()!=0){
                    arrayList.addAll(response.body());
                    tinNhanAdapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(arrayList.size()-1);
                    layThongTinDoiPhuong(idDoiPhuong);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TinNhan>> call, Throwable t) {
                thongBao("Sai");
            }
        });


        recyclerView.setAdapter(tinNhanAdapter);
        layThongTinDoiPhuong(idDoiPhuong);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void layThongTinDoiPhuong(int idDoiPhuongApi){
        Call<TaiKhoan> taiKhoanCall = ApiServiceNghiem.apiService.layTaiKhoanDoiPhuong(idDoiPhuong);
        taiKhoanCall.enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                if(response.body().getLoaiTaiKhoan()==0){
                    layThongTinNguoiThue(response.body().getId());
                }else{
                    layThongTinChuTro(response.body().getId());
                }
            }
            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {

            }
        });
    }
    private void layThongTinChuTro(int id){
        Call<ChuTro> chuTroCall = ApiServiceNghiem.apiService.thongTinChiTietChuTro(id);
        chuTroCall.enqueue(new Callback<ChuTro>() {
            @Override
            public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                tinNhanAdapter.setAnhDoiPhuong(response.body().getHinh());
                Glide.with(getApplicationContext()).load(Const.DOMAIN+response.body().getHinh()).into(avt_doiPhuong);
                tinNhanAdapter.notifyDataSetChanged();
                setTextName(response.body().getTen());

            }

            @Override
            public void onFailure(Call<ChuTro> call, Throwable t) {

            }
        });
    }
    private void layThongTinNguoiThue(int id){
        Call<NguoiThue> nguoiThueCall = ApiServiceNghiem.apiService.thongTinChiTietNguoiThue(id);
        nguoiThueCall.enqueue(new Callback<NguoiThue>() {
            @Override
            public void onResponse(Call<NguoiThue> call, Response<NguoiThue> response) {
                setTextName(response.body().getTen());
                tinNhanAdapter.setAnhDoiPhuong(response.body().getHinh());
                Glide.with(getApplicationContext()).load(Const.DOMAIN+response.body().getHinh()).into(avt_doiPhuong);
                tinNhanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NguoiThue> call, Throwable t) {

            }
        });
    }
    private void setTextName(String textNameApi){
        textName.setText(textNameApi);
    }
    private void thongBao(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }

    private void anhXa(){
        recyclerView = findViewById(R.id.chatRecyclerView);
        ic_back = findViewById(R.id.ic_back);
        textName = findViewById(R.id.textName);
        inputMess= findViewById(R.id.inputMessage);
        sendMess = findViewById(R.id.sendMess);
        avt_doiPhuong = findViewById(R.id.avt_doiphuong);
        info = findViewById(R.id.infor_doiPhuong);
    }
}