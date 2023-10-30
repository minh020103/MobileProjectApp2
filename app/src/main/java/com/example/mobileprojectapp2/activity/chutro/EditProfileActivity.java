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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.api.ApiServicePhuc;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.model.ChuTro;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imgBack;
    private AppCompatButton btnCancel, btnAcceptEditProfile;

    private RoundedImageView imgViewProfileEdit;
    private EditText edtName, edtPhone, edtStkBank, edtNameBank;
    private Uri mUri;

    Handler handler;

    public static final String TAG = EditProfileActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;

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
                            imgViewProfileEdit.setImageBitmap(bitmap);
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
        setContentView(R.layout.edit_profile_layout);

        anhXa();
        getDataFormApi();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgViewProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
    }

    private void getDataFormApi() {
        Call<ChuTro> call = ApiServicePhuc.apiService.getChuTroById(5);
        call.enqueue(new Callback<ChuTro>() {
            @Override
            public void onResponse(Call<ChuTro> call, Response<ChuTro> response) {
                ChuTro chuTro = response.body();
                Glide.with(EditProfileActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getHinh()).into(imgViewProfileEdit);
                edtName.setText(response.body().getTen());
                edtPhone.setText(response.body().getSoDienThoai());
                edtNameBank.setText(response.body().getTenChuTaiKhoanNganHang());
                edtStkBank.setText(response.body().getSoTaiKhoanNganHang());
                editPrFile(chuTro);
            }

            @Override
            public void onFailure(Call<ChuTro> call, Throwable t) {
                alertFail("Error not call api");
            }
        });
    }

    private boolean checkEmpty() {
        if (!edtName.getText().toString().isEmpty() && !edtPhone.getText().toString().isEmpty() &&
                !edtNameBank.getText().toString().isEmpty() && !edtStkBank.getText().toString().isEmpty()
        ) {
            return true;
        }
        return false;
    }

    private void editPrFile(ChuTro admin) {
        btnAcceptEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmpty()) {
                    String name = edtName.getText().toString();
                    String phone = edtPhone.getText().toString();
                    String numberBank = edtStkBank.getText().toString();
                    String nameBank = edtNameBank.getText().toString();

                    RequestBody nameChuTro = RequestBody.create(MediaType.parse("multipart/form-data"), name);
                    RequestBody phoneChuTro = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
                    RequestBody numberBankChuTro = RequestBody.create(MediaType.parse("multipart/form-data"), numberBank);
                    RequestBody nameBankChuTro = RequestBody.create(MediaType.parse("multipart/form-data"), nameBank);
                    if (mUri == null) {
                        Call<Integer> call = ApiServicePhuc.apiService.editProfileNoImage(5, nameChuTro, phoneChuTro, numberBankChuTro, nameBankChuTro);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                edtName.setText("");
                                edtPhone.setText("");
                                edtStkBank.setText("");
                                edtNameBank.setText("");
                                alertSuccess("Cập nhật thông tin thành công");

                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        handler.postDelayed(this, 2000);
                                        finish();
                                    }
                                }, 2000);
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                alertFail("Cập nhật thông tin thất bại");
                            }
                        });
                    } else {
                        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
                        File file = new File(strRealPath);
                        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part mulPart = MultipartBody.Part.createFormData("hinh", file.getName(), requestBodyImage);
                        Call<Integer> call = ApiServicePhuc.apiService.editProfileImage(5, nameChuTro, phoneChuTro, numberBankChuTro, nameBankChuTro, mulPart);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                edtName.setText("");
                                edtPhone.setText("");
                                edtStkBank.setText("");
                                edtNameBank.setText("");
                                alertSuccess("Cập nhật thông tin thành công");

                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        handler.postDelayed(this, 2000);
                                        finish();
                                    }
                                }, 2000);
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                alertFail("Cập nhật thông tin thất bại 2");
                            }
                        });
                    }
                } else {
                    alertFail("Thiếu Dữ Liệu!");
                }
            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        //Xin cap phep
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();

        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
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
        imgBack = findViewById(R.id.img_back);
        btnCancel = findViewById(R.id.btn_cancel);
        btnAcceptEditProfile = findViewById(R.id.btn_accept_edit_profile);
        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtNameBank = findViewById(R.id.edt_name_user_bank);
        edtStkBank = findViewById(R.id.edt_number_bank);
        imgViewProfileEdit = findViewById(R.id.imgView_profile_edit);
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
                    }
                }).show();
    }

}