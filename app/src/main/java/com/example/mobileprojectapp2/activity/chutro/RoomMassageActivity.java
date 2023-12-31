package com.example.mobileprojectapp2.activity.chutro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.adapter.TinNhanAdapter;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.NguoiThue;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.TinNhan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
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
    private int trangThai;
    AppCompatImageView ic_back;
    TextView textName;
    EditText inputMess;
    AppCompatImageView sendMess;
    CircleImageView avt_doiPhuong;
    AppCompatImageView info;
    SharedPreferences sharedPreferences;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_massage);
        anhXa();
        arrayList = new ArrayList<>();
        khoiTaoFireBase();
        sharedPreferences = getApplicationContext().getSharedPreferences(Const.PRE_LOGIN,MODE_PRIVATE);
        senderId = sharedPreferences.getInt("idTaiKhoan", -1);
        idDoiPhuong= getIntent().getIntExtra("key",-1);
        idPhong=getIntent().getIntExtra("phong",-1);;
        trangThai = getIntent().getIntExtra("trangThai",-1);
        tinNhanAdapter = new TinNhanAdapter(this,arrayList,R.layout.cardview_send_message,R.layout.cardview_recieve_message,senderId);
        if(idDoiPhuong==-1||idPhong==-1){
            thongBao("Xảy Ra Lỗi!");
        }else{
            setDuLieu();
        }
        reloadDuLieu();

    }

    private void khoiTaoFireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void thongBaoReset(String mes){
        databaseReference.child("thongBaoReset").child(idDoiPhuong+"").setValue(mes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        databaseReference.child("phongTinNhan").child(idPhong+"").setValue(mes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void reloadDuLieu(){
        databaseReference.child("phongTinNhan").child(idPhong+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setLaiDuLieu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setLaiDuLieu(){
        arrayList.clear();
        Call<ArrayList<TinNhan>> call = ApiServiceNghiem.apiService.layDanhSachTinNhan(idPhong);
        call.enqueue(new Callback<ArrayList<TinNhan>>() {
            @Override
            public void onResponse(Call<ArrayList<TinNhan>> call, Response<ArrayList<TinNhan>> response) {
                if (response.body()!=null) {
                    if (response.body().size() != 0) {
                        arrayList.addAll(response.body());
                        tinNhanAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(arrayList.size() - 1);
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<TinNhan>> call, Throwable t) {

            }
        });

    }
    private void capNhatTinNhanNew(int idPhong, String noiDung, String thoiGian){
        RequestBody noiDungAPi = RequestBody.create(MediaType.parse("multipart/form-data"),inputMess.getText().toString());
        RequestBody thoiGianApi = RequestBody.create(MediaType.parse("multipart/form-data"),thoiGian);
        Call<Integer> call = ApiServiceNghiem.apiService.capNhatTinNhanMoiNhat(senderId,idPhong,noiDungAPi,thoiGianApi);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                thongBaoReset(inputMess.getText().toString());
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
                            if (response.body()!=null) {
                                Date currentTime = Calendar.getInstance().getTime();
                                Timestamp tsTemp = new Timestamp(currentTime.getTime());
                                Date date = new Date(tsTemp.getTime());
                                SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy");
                                if (arrayList.size() != 0) {
                                    TinNhan tinNhan = new TinNhan(arrayList.get(arrayList.size() - 1).getId() + 1, idPhong, senderId, inputMess.getText().toString(), tsTemp);
                                    arrayList.add(tinNhan);
                                    tinNhanAdapter.notifyDataSetChanged();
//                                recyclerView.smoothScrollToPosition(arrayList.size()-1);
                                } else {
                                    TinNhan tinNhan = new TinNhan(1, idPhong, senderId, inputMess.getText().toString(), tsTemp);
                                    arrayList.add(tinNhan);
                                    tinNhanAdapter.notifyDataSetChanged();
//                                recyclerView.smoothScrollToPosition(arrayList.size()-1);
                                }
                                recyclerView.smoothScrollToPosition(arrayList.size() - 1);
                                capNhatTinNhanNew(idPhong, inputMess.getText().toString(), formatDate(response.body().getCreated_at()));
                            }
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
        if(trangThai!=-1&&trangThai==0){
            capNhatTrangThaiDaXem();
        }
        arrayList.clear();
        Call<ArrayList<TinNhan>> call = ApiServiceNghiem.apiService.layDanhSachTinNhan(idPhong);
        call.enqueue(new Callback<ArrayList<TinNhan>>() {
            @Override
            public void onResponse(Call<ArrayList<TinNhan>> call, Response<ArrayList<TinNhan>> response) {
                if(response.body() != null){
                    if(response.body().size()!=0){
                        arrayList.addAll(response.body());
                        tinNhanAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(arrayList.size()-1);
                        recyclerView.setAdapter(tinNhanAdapter);
                        layThongTinDoiPhuong(idDoiPhuong);
                        setSuKienGuiTinNhan();
//                    handleLienTuc(arrayList);
                    }else{
                        tinNhanAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(tinNhanAdapter);
                        layThongTinDoiPhuong(idDoiPhuong);
                        setSuKienGuiTinNhan();
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<TinNhan>> call, Throwable t) {
                thongBao("Sai");
            }
        });



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
                if (response.body()!=null) {
                    if (response.body().getLoaiTaiKhoan() == 0) {
                        layThongTinNguoiThue(response.body().getId());
                    } else {
                        layThongTinChuTro(response.body().getId());
                    }
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
                if (response.body()!=null) {
                    tinNhanAdapter.setAnhDoiPhuong(response.body().getHinh());
                    Glide.with(getApplicationContext()).load(Const.DOMAIN + response.body().getHinh()).into(avt_doiPhuong);
                    tinNhanAdapter.notifyDataSetChanged();
                    setTextName(response.body().getTen());
                }
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
                if (response.body()!=null) {
                    setTextName(response.body().getTen());
                    tinNhanAdapter.setAnhDoiPhuong(response.body().getHinh());
                    Glide.with(getApplicationContext()).load(Const.DOMAIN + response.body().getHinh()).into(avt_doiPhuong);
                    tinNhanAdapter.notifyDataSetChanged();
                }
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