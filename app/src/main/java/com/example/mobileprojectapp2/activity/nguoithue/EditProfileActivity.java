package com.example.mobileprojectapp2.activity.nguoithue;

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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.RealPathUtil;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;
import com.example.mobileprojectapp2.datamodel.NguoiThue;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView imgBackNguoiThue;
    private AppCompatButton btnCancelNguoiThue, btnAcceptEditProfileNguoiThue;

    private ImageView imgViewProfileEditNguoiThue;
    private EditText edtNameNguoiThue, edtPhoneNguoiThue;
    private SharedPreferences sharedPreferences;
    private int idTaiKhoan = 17;
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
                            imgViewProfileEditNguoiThue.setImageBitmap(bitmap);
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
        setContentView(R.layout.nguoithue_edit_profile_layout);
        sharedPreferences = EditProfileActivity.this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);
        anhXa();
        getDataFormApi();

        imgBackNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancelNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgViewProfileEditNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
    }

    private void getDataFormApi() {
        Call<NguoiThue> call = ApiServicePhuc2.apiService.getThongTinNguoiThue(idTaiKhoan);
        call.enqueue(new Callback<NguoiThue>() {
            @Override
            public void onResponse(Call<NguoiThue> call, Response<NguoiThue> response) {
                if (response.body()!=null) {
                    NguoiThue nguoiThue = response.body();
                    if (response.body().getHinh() != null)
                        Glide.with(EditProfileActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getHinh()).into(imgViewProfileEditNguoiThue);
                    else {
                        imgViewProfileEditNguoiThue.setImageResource(R.drawable.khongcoanh);
                    }
                    edtNameNguoiThue.setText(response.body().getTen());
                    edtPhoneNguoiThue.setText(response.body().getSoDienThoai());
                    editPrFile(nguoiThue);
                }
            }

            @Override
            public void onFailure(Call<NguoiThue> call, Throwable t) {
                alertFail("Error not call api");
            }
        });
    }

    private boolean checkEmpty() {
        if (!edtNameNguoiThue.getText().toString().isEmpty() && !edtPhoneNguoiThue.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    private void editPrFile(NguoiThue admin) {
        btnAcceptEditProfileNguoiThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmpty()) {
                    String name = edtNameNguoiThue.getText().toString();
                    String phone = edtPhoneNguoiThue.getText().toString();

                    RequestBody nameNguoiThue = RequestBody.create(MediaType.parse("multipart/form-data"), name);
                    RequestBody phoneNguoiThue = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
                    if (mUri == null) {
                        Call<Integer> call = ApiServicePhuc2.apiService.editProfileKhongHinh(idTaiKhoan, nameNguoiThue, phoneNguoiThue);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.body()!=null) {
                                    edtNameNguoiThue.setText("");
                                    edtPhoneNguoiThue.setText("");
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
                        Call<Integer> call = ApiServicePhuc2.apiService.editProfileCoHinh(idTaiKhoan, nameNguoiThue, phoneNguoiThue, mulPart);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.body()!=null) {
                                    edtNameNguoiThue.setText("");
                                    edtPhoneNguoiThue.setText("");
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
        imgBackNguoiThue = findViewById(R.id.img_back_nguoi_thue);
        btnCancelNguoiThue = findViewById(R.id.btn_cancel_nguoi_thue);
        btnAcceptEditProfileNguoiThue = findViewById(R.id.btn_accept_edit_profile_nguoi_thue);
        edtPhoneNguoiThue = findViewById(R.id.edt_phone_nguoi_thue);
        imgViewProfileEditNguoiThue = findViewById(R.id.imgView_profile_edit_nguoi_thue);
        edtNameNguoiThue = findViewById(R.id.edt_name_nguoi_thue);
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