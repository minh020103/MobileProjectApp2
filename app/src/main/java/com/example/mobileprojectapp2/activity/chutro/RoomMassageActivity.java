package com.example.mobileprojectapp2.activity.chutro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.TinNhanAdapter;
import com.example.mobileprojectapp2.api.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.TinNhan;

import java.util.ArrayList;

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
                            inputMess.setText("");
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
    private void setDuLieu(){
        senderId = 2;
        idDoiPhuong= 3;
        idPhong=1;
        arrayList = new ArrayList<>();
        tinNhanAdapter = new TinNhanAdapter(this,arrayList,R.layout.cardview_send_message,R.layout.cardview_recieve_message,senderId);
        Call<ArrayList<TinNhan>> call = ApiServiceNghiem.apiService.layDanhSachTinNhan(idPhong);
        call.enqueue(new Callback<ArrayList<TinNhan>>() {
            @Override
            public void onResponse(Call<ArrayList<TinNhan>> call, Response<ArrayList<TinNhan>> response) {
                arrayList.addAll(response.body());
                tinNhanAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(arrayList.size()-1);
                layThongTinDoiPhuong(idDoiPhuong);
            }

            @Override
            public void onFailure(Call<ArrayList<TinNhan>> call, Throwable t) {
                thongBao("Sai");

            }
        });


        recyclerView.setAdapter(tinNhanAdapter);
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
    }
}