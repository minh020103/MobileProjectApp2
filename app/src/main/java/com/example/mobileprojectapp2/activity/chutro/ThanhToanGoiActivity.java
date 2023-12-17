package com.example.mobileprojectapp2.activity.chutro;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceDung;
import com.example.mobileprojectapp2.component.MFCM;
import com.example.mobileprojectapp2.datamodel.Goi;
import com.example.mobileprojectapp2.datamodel.YeuCauDKG;
import com.example.mobileprojectapp2.model.Admin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanGoiActivity extends AppCompatActivity {
    TextView so_phong,so_ngay,gia,so_tai_khoan,ten_admin;
    RoundedImageView imgCK;
    int idGoi,idTaiKhoan, idChuTro;
    private SharedPreferences shared;

    ImageView img_Back;
    Button btn_gui_goi;
    private Uri mUri;

    public static final String TAG = ThanhToanGoiActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        //Du anh lieu tu gallery
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgCK.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_goi_layout);

        so_phong = findViewById(R.id.so_phong);
        so_ngay = findViewById(R.id.so_ngay);
        gia = findViewById(R.id.gia);
        so_tai_khoan = findViewById(R.id.so_tai_khoan);
        ten_admin = findViewById(R.id.ten_admin);
        imgCK = findViewById(R.id.imgCK);
        btn_gui_goi = findViewById(R.id.btn_gui_goi);
        img_Back = findViewById(R.id.img_Back);
        Intent intent = getIntent();
        idGoi = intent.getIntExtra("idGoi",0);


        shared = getApplicationContext().getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = shared.getInt("idTaiKhoan", -1);
        idChuTro = shared.getInt("idChuTro", -1);

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgCK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        btn_gui_goi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDangKiGoiAPI();
            }
        });

    }

    private void uploadDangKiGoiAPI() {
        if (mUri != null){
            String idCT = idChuTro+"";
            String idG = idGoi+"";
            RequestBody idChuTro2 = RequestBody.create(MediaType.parse("multipart/form-data"), idCT);
            RequestBody idGoiDK = RequestBody.create(MediaType.parse("multipart/form-data"), idG);

            String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
            Log.d("test", "uploadDangKiGoiAPI: "+strRealPath);
            File file = new File(strRealPath);

            RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part mulPart = MultipartBody.Part.createFormData("hinhAnhChuyenKhoan", file.getName(), requestBodyImage);
            ApiServiceDung.apiServiceDung.yeucaudangkygoi(idChuTro2,idGoiDK,mulPart).enqueue(new Callback<YeuCauDKG>() {
                @Override
                public void onResponse(Call<YeuCauDKG> call, Response<YeuCauDKG> response) {
                    alertSuccess("Gửi Yêu Cầu Thành Công");
                    Log.d("TAG", "onResponse: "+idChuTro);
                    databaseReference.child("notification_admin").child(response.body().getIdGoi() + "").setValue(new Date().getSeconds()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: PUSH NOTIFICATION REALTIME");
                        }
                    });
                    MFCM.sendNotificationForAccountID(1,new Date().getSeconds(),"Yêu cầu đăng ký gói", "id chủ trọ: "+ idChuTro+" id gói: "+ idG);
                }
                @Override
                public void onFailure(Call<YeuCauDKG> call, Throwable t) {
                    alertFail("Bạn đã gửi 1 yêu cầu trước đó hãy đợi admin xác nhận");
                }
            });
        }else{
            alertFail("Cập nhật ảnh thất bại");
        }
    }

    private void getGoiByIdAPI(int key){
        ApiServiceDung.apiServiceDung.getPakageByIdAPI(key).enqueue(new Callback<Goi>() {
            @Override
            public void onResponse(Call<Goi> call, Response<Goi> response) {
                Goi goi = response.body();
                so_ngay.setText(String.valueOf(goi.getThoiHan()) + " Ngày");
                so_phong.setText(String.valueOf(goi.getSoLuongPhongToiDa())+" Phòng");
                gia.setText(String.valueOf(goi.getGia())+" VND");
            }
            @Override
            public void onFailure(Call<Goi> call, Throwable t) {
                alertFail("Error not call api");
            }
        });
    }

    private void getAdmin(){
        ApiServiceDung.apiServiceDung.layThongTinAdmin(1).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                Admin admin = response.body();
                ten_admin.setText(String.valueOf(admin.getTenChuTaiKhoan()));
                so_tai_khoan.setText(String.valueOf(admin.getSoTaiKhoanNganHang()));
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                alertFail("Error not call api");
            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, MY_REQUEST_CODE);
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] permission = {Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(permission, MY_REQUEST_CODE);
            }
        }
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Seletec Picture"));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            //Khi nguoi dung cho phep
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAdmin();
        getGoiByIdAPI(idGoi);
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setIcon(R.drawable.iconp_fail)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void alertSuccess(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setIcon(R.drawable.iconp_check)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                }).show();
    }
}