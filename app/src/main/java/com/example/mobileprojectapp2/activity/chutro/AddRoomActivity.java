package com.example.mobileprojectapp2.activity.chutro;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.TienIch;
import com.example.mobileprojectapp2.path.RealPathUtil;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.ImagesAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.UtilitiesAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.UtilitiesSeletedAdapter;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRoomActivity extends AppCompatActivity {
    //EditText
    private EditText edtSoPhong, edtGia, edtDienTich, edtMota, edtDiaChiChiTiet;
    //TextView
    private TextView tvChonHinh, tvQuan, tvPhuong, tvXacNhan,tvChonTienIch;
    //Recycleview
    private RecyclerView rcvChoosedImages;
    private RecyclerView rcvTienIchDaChon;
    private RecyclerView rcvChonTienIch;
    //ImageView
    private ImageView imgBack;
    //Final
    private final int RQ = 10001;
    //List
    private List<String> pathList;
    private List<Bitmap> bitmapList;
    private List<TienIch> listTienIchSeleted;
    private List<TienIch> listTienIch;
    //Uri
    private Uri imgUri;
    //Context
    private Context context = AddRoomActivity.this;

    //Adapter
    private ImagesAdapter adapter;
    private UtilitiesAdapter utilitiesAdapter;
    private UtilitiesSeletedAdapter utilitiesSeletedAdapter;
    //Linearlayoutmanager
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chutro_add_room_layout);
        anhXa();
        imgBack = findViewById(R.id.imgBack);
        rcvChoosedImages = findViewById(R.id.rcvChoosedImages);

        // Khởi tạo
        pathList = new LinkedList<>();
        bitmapList = new LinkedList<>();
        listTienIch = new LinkedList<>();
        listTienIchSeleted = new LinkedList<>();

        // layout manager of recyclerview
        // 1 recyclerview select images
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager = new GridLayoutManager(this, 3);
        // adpater seleted images
        adapter = new ImagesAdapter(this, bitmapList, R.layout.chutro_choose_images_layout);
        rcvChoosedImages.setLayoutManager(layoutManager);
        rcvChoosedImages.setAdapter(adapter);
        // 2 recyclerview tiện ích
        utilitiesAdapter = new UtilitiesAdapter(AddRoomActivity.this, listTienIch, listTienIchSeleted, R.layout.chutro_cardview_item_utilities_layout);
        // 3 recyclerview tiện ích đã chọn
        utilitiesSeletedAdapter = new UtilitiesSeletedAdapter(this, listTienIchSeleted, R.layout.chutro_cardview_item_utilities_seleted_layout);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rcvTienIchDaChon = findViewById(R.id.rcvTienIchDaChon);
        rcvTienIchDaChon.setLayoutManager(layoutManager2);
        rcvTienIchDaChon.setAdapter(utilitiesSeletedAdapter);

        batSuKien();
    }

    private void anhXa() {
        //EditText
        edtSoPhong = findViewById(R.id.edtSoPhong);
        edtGia = findViewById(R.id.edtGia);
        edtDienTich = findViewById(R.id.edtDienTich);
        edtMota = findViewById(R.id.edtMota);
        edtDiaChiChiTiet = findViewById(R.id.edtDiaChiChiTiet);
        //TextView
        tvChonHinh = findViewById(R.id.tvChonHinh);
        tvQuan = findViewById(R.id.tvQuan);
        tvPhuong = findViewById(R.id.tvPhuong);
        tvChonTienIch = findViewById(R.id.tvChonTienIch);
        tvXacNhan = findViewById(R.id.tvXacNhan);
    }


    private void batSuKien() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPesmission();
            }
        });
        tvQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvPhuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvChonTienIch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = getLayoutInflater();
                View viewDialog = inflater.inflate(R.layout.chutro_dialog_choose_utilities_layout, null);
                builder.setView(viewDialog);
                AlertDialog dialog = builder.create();
                dialog.show();

                getListTienIch();


                rcvChonTienIch = viewDialog.findViewById(R.id.rcvChonTienIch);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(AddRoomActivity.this);
                layoutManager1.setOrientation(RecyclerView.VERTICAL);
                rcvChonTienIch.setLayoutManager(layoutManager1);
                rcvChonTienIch.setAdapter(utilitiesAdapter);
            }
        });
        tvXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getListTienIch() {
        ApiServiceMinh.apiService.layTatCaTienIch().enqueue(new Callback<List<TienIch>>() {
            @Override
            public void onResponse(Call<List<TienIch>> call, Response<List<TienIch>> response) {
                if (response.code() == 200){
                    listTienIch.clear();
                    listTienIch.addAll(response.body());
                }
                utilitiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TienIch>> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t);
            }
        });
    }

    private void checkPesmission(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,RQ);
        }
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Select Picture"));
    }
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data.getClipData()!=null){
//                            imgUri = data.getData();
                            for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                                imgUri = data.getClipData().getItemAt(i).getUri();
                                bitmapList.add(BitmapFactory.decodeFile(RealPathUtil.getRealPath(context, imgUri)));
                                pathList.add(RealPathUtil.getRealPath(context, imgUri));
                            }
                        }
                        else {
                            imgUri = data.getData();
                            bitmapList.add(BitmapFactory.decodeFile(RealPathUtil.getRealPath(context, imgUri)));
                            pathList.add(RealPathUtil.getRealPath(context, imgUri));
                        }
                        adapter.notifyDataSetChanged();



                    }
                }
            }
    );
}