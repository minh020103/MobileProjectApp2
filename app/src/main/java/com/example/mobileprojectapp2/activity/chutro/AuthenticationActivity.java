package com.example.mobileprojectapp2.activity.chutro;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.ApiFCMService;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh;
import com.example.mobileprojectapp2.component.MFCM;
import com.example.mobileprojectapp2.datamodel.FirebaseCloudMessaging;
import com.example.mobileprojectapp2.datamodel.ResultFCM;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.fcm.Notification;
import com.example.mobileprojectapp2.datamodel.fcm.PushNotification;
import com.example.mobileprojectapp2.model.XacThucChuTro;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {


    private static final int CHUA_XAC_THUC = 0;
    private static final int DA_XAC_THUC = 1;
    private ImageView imgViewBack, imageViewMatTruocCCCD, imageViewMatSauCCCD;
    private TextView tvNotAuthencation, tvOkAuthencation, tvDangChoAuthencation;
    private AppCompatButton btnAcceptYeuCauXacThuc;
    private SharedPreferences sharedPreferences;
    private int idTaiKhoan;
    private int idChuTro;
    private int trangThaiXacThuc;
    public static final String TAG = AuthenticationActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;
    private Uri cccdMT = null;
    private Uri cccdMS = null;
    private int viewID = -1;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

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
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            if (viewID == R.id.imgView_mat_truoc_cccd) {
                                cccdMT = uri;
                                imageViewMatTruocCCCD.setImageBitmap(bitmap);
                            }
                            if (viewID == R.id.imgView_mat_sau_cccd) {
                                cccdMS = uri;
                                imageViewMatSauCCCD.setImageBitmap(bitmap);
                            }
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
        setContentView(R.layout.chutro_authencation_layout);
        sharedPreferences = AuthenticationActivity.this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        idChuTro = sharedPreferences.getInt("idChuTro", -1);
        trangThaiXacThuc = sharedPreferences.getInt("trangThaiXacThuc", -1);

        anhXa();

        getDetailChuTro();
        onClickCanDuLieu(trangThaiXacThuc);

        btnAcceptYeuCauXacThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToApi();

            }
        });

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendDataToApi() {
        File fileMT;
        File fileMS;
        if (cccdMT != null && cccdMS != null) {

            fileMT = new File(RealPathUtil.getRealPath(this, cccdMT));
            fileMS = new File(RealPathUtil.getRealPath(this, cccdMS));

            Log.d(TAG, "sendDataToApi: " + fileMT.getName());
            Log.d(TAG, "sendDataToApi: " + fileMS.getName());
            RequestBody requestBodyIdChuTro = RequestBody.create(MediaType.parse("multipart/form-data"), idChuTro + "");

            RequestBody requestBodycccdMT = RequestBody.create(MediaType.parse("multipart/form-data"), fileMT);
            MultipartBody.Part partCccdMT = MultipartBody.Part.createFormData("cccdMatTruoc", fileMT.getName(), requestBodycccdMT);

            RequestBody requestBodycccdMS = RequestBody.create(MediaType.parse("multipart/form-data"), fileMS);
            MultipartBody.Part partCccdMS = MultipartBody.Part.createFormData("cccdMatSau", fileMS.getName(), requestBodycccdMS);


            Call<XacThucChuTro> call = ApiServicePhuc.apiService.guiYeuCauXacThucChuTro(requestBodyIdChuTro, partCccdMT, partCccdMS);
            call.enqueue(new Callback<XacThucChuTro>() {
                @Override
                public void onResponse(Call<XacThucChuTro> call, Response<XacThucChuTro> responseXTCT) {
                    if (responseXTCT.body()!=null) {
                        alertSuccess("Gửi yêu cầu xác nhận chủ trọ thành công");
                        tvNotAuthencation.setVisibility(View.GONE);
                        tvOkAuthencation.setVisibility(View.GONE);
                        btnAcceptYeuCauXacThuc.setVisibility(View.GONE);
                        tvDangChoAuthencation.setVisibility(View.VISIBLE);

                        imageViewMatTruocCCCD.setEnabled(false);
                        imageViewMatSauCCCD.setEnabled(false);
                        databaseReference.child("notification_admin").child(idChuTro + "").setValue(new Date().getSeconds()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "onSuccess: PUSH NOTIFICATION REALTIME");
                            }
                        });
                        ApiServiceMinh.apiService.layTatCaTaiKhoanTheoLoaiTaiKhoan(Const.ADMIN).enqueue(new Callback<List<TaiKhoan>>() {
                            @Override
                            public void onResponse(Call<List<TaiKhoan>> call, Response<List<TaiKhoan>> responseTaiKhoan) {
                                if (responseTaiKhoan.code() == 200) {
                                    if (responseTaiKhoan.body() != null) {
                                        for (TaiKhoan taikhoan :
                                                responseTaiKhoan.body()) {
                                            Log.d(TAG, "onResponse: " + taikhoan.getId());
                                            MFCM.sendNotificationForAccountID(taikhoan.getId(), responseXTCT.body().getId(), "Xác thực chủ trọ", "Yêu cầu xác thực chủ trọ.");
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<TaiKhoan>> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<XacThucChuTro> call, Throwable t) {
                    alertFail("Gửi yêu cầu xác nhận chủ trọ thất bại");
                    tvNotAuthencation.setVisibility(View.VISIBLE);
                    tvOkAuthencation.setVisibility(View.GONE);
                    tvDangChoAuthencation.setVisibility(View.GONE);
                }
            });
        } else {
            Toast.makeText(this, "Chưa chọn ảnh Căn cước công dân", Toast.LENGTH_SHORT).show();
            tvDangChoAuthencation.setVisibility(View.GONE);
            tvNotAuthencation.setVisibility(View.VISIBLE);

        }
    }

    private void getDetailChuTro() {
        Call<XacThucChuTro> call = ApiServicePhuc.apiService.getDetailChuTro(idChuTro);
        call.enqueue(new Callback<XacThucChuTro>() {
            @Override
            public void onResponse(Call<XacThucChuTro> call, Response<XacThucChuTro> response) {
               if (response.code() == 200){
                   if (response.body() != null){
                       if (response.body().getTrangThaiXacThuc() == 0) {
                           Glide.with(AuthenticationActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getCccdMatTruoc()).into(imageViewMatTruocCCCD);
                           Glide.with(AuthenticationActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getCccdMatSau()).into(imageViewMatSauCCCD);
                           tvDangChoAuthencation.setVisibility(View.VISIBLE);
                           tvNotAuthencation.setVisibility(View.GONE);
                           tvOkAuthencation.setVisibility(View.GONE);
                           btnAcceptYeuCauXacThuc.setVisibility(View.GONE);
                           imageViewMatTruocCCCD.setEnabled(false);
                    	   imageViewMatSauCCCD.setEnabled(false);
                           onClickCanDuLieu(trangThaiXacThuc);
                       }
                       if (response.body().getTrangThaiXacThuc() == 1) {
                           Glide.with(AuthenticationActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getCccdMatTruoc()).into(imageViewMatTruocCCCD);
                           Glide.with(AuthenticationActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getCccdMatSau()).into(imageViewMatSauCCCD);
                           tvNotAuthencation.setVisibility(View.GONE);
                           tvOkAuthencation.setVisibility(View.VISIBLE);
                           btnAcceptYeuCauXacThuc.setVisibility(View.GONE);
                           imageViewMatTruocCCCD.setEnabled(false);
                           imageViewMatSauCCCD.setEnabled(false);
                       }
                   }
               }



            }

            @Override
            public void onFailure(Call<XacThucChuTro> call, Throwable t) {
                tvNotAuthencation.setVisibility(View.VISIBLE);
                tvOkAuthencation.setVisibility(View.GONE);
                btnAcceptYeuCauXacThuc.setVisibility(View.VISIBLE);
                imageViewMatTruocCCCD.setEnabled(true);
                imageViewMatSauCCCD.setEnabled(true);

            }

        });
    }

    private void onClickCanDuLieu(int xacThuc) {
        if (xacThuc == CHUA_XAC_THUC) {
            imageViewMatTruocCCCD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: " + v.toString());
                    onClickRequestPermission();
                    viewID = v.getId();
                }
            });
            imageViewMatSauCCCD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: " + v.toString());
                    onClickRequestPermission();
                    viewID = v.getId();
                }
            });
        }
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
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

    private void anhXa() {
        imgViewBack = findViewById(R.id.img_back);
        imageViewMatTruocCCCD = findViewById(R.id.imgView_mat_truoc_cccd);
        imageViewMatSauCCCD = findViewById(R.id.imgView_mat_sau_cccd);
        tvNotAuthencation = findViewById(R.id.tv_not_authencation);
        tvOkAuthencation = findViewById(R.id.tv_ok_authencation);
        btnAcceptYeuCauXacThuc = findViewById(R.id.btn_accept_yeu_cau_xac_thuc_nguoi_thue);
        tvDangChoAuthencation = findViewById(R.id.tv_dangcho_authencation);
    }

    private void alertFail(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(s)
                .setIcon(R.drawable.iconp_fail)
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
                .setMessage(s)
                .setIcon(R.drawable.iconp_check)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}