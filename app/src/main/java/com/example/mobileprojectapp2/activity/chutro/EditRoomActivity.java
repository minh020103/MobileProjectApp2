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
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.GioiTinh;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodel.PhongTro;
import com.example.mobileprojectapp2.datamodel.Phuong;
import com.example.mobileprojectapp2.datamodel.Quan;
import com.example.mobileprojectapp2.datamodel.TienIch;
import com.example.mobileprojectapp2.path.RealPathUtil;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.DistrictAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.GenderAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.ImagesAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.LoaiPhongAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.UtilitiesAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.UtilitiesSeletedAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.WardAdapter;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRoomActivity extends AppCompatActivity {
    private final int idChuTro = 2;
    private int idPhong;
    //EditText
    private EditText edtSoPhong, edtGia, edtDienTich, edtMota, edtDiaChiChiTiet, edtTienDien, edtTienNuoc, edtSoLuongToiDa, edtTienCoc;
    //TextView
    private TextView tvChonHinh, tvQuan, tvPhuong, tvXacNhanSua, tvChonTienIch, tvChonGioiTinh, tvChonLoaiPhong;
    //Recycleview
    private RecyclerView rcvChoosedImages;
    private RecyclerView rcvTienIchDaChon;
    private RecyclerView rcvChonTienIch;
    private RecyclerView rcvChonQuan;
    private RecyclerView rcvChonPhuong;
    private RecyclerView rcvGioiTinh;
    private RecyclerView rcvLoaiPhong;
    //ImageView
    private ImageView imgBack;
    //Final
    private final int RQ = 10001;
    //List
    private List<String> pathList;
    private List<Bitmap> bitmapList;
    private List<Uri> uriList;
    private List<TienIch> listTienIchSeleted;
    private List<TienIch> listTienIch;
    private List<Quan> lisQuan;
    private List<Phuong> listPhuong;
    private List<GioiTinh> gioiTinhs;
    private List<Integer> listLoaiPhong;
    //Uri
    private Uri imgUri;
    //Context
    private Context context = EditRoomActivity.this;

    //Adapter
    private ImagesAdapter imagesAdapter;
    private UtilitiesAdapter utilitiesAdapter;
    private UtilitiesSeletedAdapter utilitiesSeletedAdapter;
    private DistrictAdapter districtAdapter;
    private WardAdapter wardAdapter;
    private GenderAdapter genderAdapter;
    private LoaiPhongAdapter loaiPhongAdapter;
    //Linearlayoutmanager
    private LinearLayoutManager layoutManager;
    //position
    // 1 Sử lý lựa chọn cho list quận
    private int positionSeletedQuan = -1;
    private int backColorQuan;
    private LinearLayout previousItemGroundQuan;
    private int idQuan = -1;
    // 2 Sử lý lựa chọn phường
    private int positionSeletedPhuong = -1;
    private int backColorPhuong;
    private LinearLayout previousItemGroundPhuong;
    private int idPhuong = -1;
    // 3 Sử lý lựa chọn giới tính
    private int positionSeletedGioiTinh = -1;
    private int backColorGioiTinh;
    private LinearLayout previousItemGroundGioiTinh;
    private int gioiTinh = -1;
    // 3 Sử lý lựa chọn loai phòng
    private int positionSeletedLoaiPhong = -1;
    private int backColorLoaiPhong;
    private LinearLayout previousItemGroundLoaiPhong;
    private int loaiPhong = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chutro_edit_room_layout);

        Intent intent = getIntent();
        idPhong = intent.getIntExtra("idPhong", -1);
        anhXa();

        rcvChoosedImages = findViewById(R.id.rcvChoosedImages);

        // Khởi tạo
        pathList = new LinkedList<>();
        bitmapList = new LinkedList<>();
        uriList = new LinkedList<>();
        listTienIch = new LinkedList<>();
        listTienIchSeleted = new LinkedList<>();
        lisQuan = new LinkedList<>();
        listPhuong = new LinkedList<>();
        // Create list and data
        gioiTinhs = new LinkedList<>();
        gioiTinhs.clear();
        gioiTinhs.add(new GioiTinh(Const.ALL_GENDERS, "Tất cả"));
        gioiTinhs.add(new GioiTinh(Const.MALE_GENDERS, "Nam"));
        gioiTinhs.add(new GioiTinh(Const.FEMALE_GENDERS, "Nữ"));
        listLoaiPhong = new LinkedList<>();
        listLoaiPhong.add(Const.PHONG_TRONG);
        listLoaiPhong.add(Const.PHONG_DON);
        listLoaiPhong.add(Const.PHONG_GHEP);

        // layout manager of recyclerview
        // 1 recyclerview select images
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager = new GridLayoutManager(this, 3);
        // adpater seleted images
        imagesAdapter = new ImagesAdapter(this, bitmapList, R.layout.chutro_choose_images_layout);
        rcvChoosedImages.setLayoutManager(layoutManager);
        rcvChoosedImages.setAdapter(imagesAdapter);
        imagesAdapter.setOnCLick(new ImagesAdapter.OnCLick() {
            @Override
            public void delete(int position, View v) {
                bitmapList.remove(bitmapList.get(position));
                pathList.remove(pathList.get(position));
                imagesAdapter.notifyDataSetChanged();
            }
        });
        // 2 recyclerview tiện ích đã chọn
        utilitiesSeletedAdapter = new UtilitiesSeletedAdapter(this, listTienIchSeleted, R.layout.chutro_cardview_item_utilities_seleted_layout);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rcvTienIchDaChon = findViewById(R.id.rcvTienIchDaChon);
        rcvTienIchDaChon.setLayoutManager(layoutManager2);
        rcvTienIchDaChon.setAdapter(utilitiesSeletedAdapter);
        utilitiesSeletedAdapter.setOnClick(new UtilitiesSeletedAdapter.OnClick() {
            @Override
            public void deleteImage(int position, View v) {
                listTienIchSeleted.remove(listTienIchSeleted.get(position));
                utilitiesSeletedAdapter.notifyDataSetChanged();
            }
        });
        // 3 recyclerview tiện ích
        utilitiesAdapter = new UtilitiesAdapter(EditRoomActivity.this, listTienIch, listTienIchSeleted, R.layout.chutro_cardview_item_utilities_layout);
        // 4 recyclerview quận
        districtAdapter = new DistrictAdapter(EditRoomActivity.this, lisQuan, R.layout.chutro_cardview_item_quan_layout);
        // 5 recyclerview phường
        wardAdapter = new WardAdapter(EditRoomActivity.this, listPhuong, R.layout.chutro_cardview_item_phuong_layout);
        // 6 recyclerview giới tính
        genderAdapter = new GenderAdapter(EditRoomActivity.this, gioiTinhs, R.layout.chutro_cardview_item_gender_layout);
        // 7 recyclerview loại phòng
        loaiPhongAdapter = new LoaiPhongAdapter(EditRoomActivity.this, listLoaiPhong, R.layout.chutro_cardview_item_loai_phong_layout);
        // get data
        getListTienIch();
        getQuan();
        imgBack = findViewById(R.id.imgBack);
        // Bắt sự kiện
        batSuKien();

        getChiTietPhong();
    }

    private void getChiTietPhong() {
        if (idPhong != -1) {
            ApiServiceMinh.apiService.layChiTietPhongTro(idPhong).enqueue(new Callback<PhongTro>() {
                @Override
                public void onResponse(Call<PhongTro> call, Response<PhongTro> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            PhongTro phongTro = response.body();
                            edtSoPhong.setText(phongTro.getSoPhong() + "");
                            edtGia.setText(phongTro.getGia() + "");
                            edtDienTich.setText(phongTro.getDienTich() + "");
                            edtMota.setText(phongTro.getMoTa() + "");
                            edtDiaChiChiTiet.setText(phongTro.getDiaChiChiTiet() + "");
                            edtTienDien.setText(phongTro.getTienDien() + "");
                            edtTienNuoc.setText(phongTro.getTienNuoc() + "");
                            edtSoLuongToiDa.setText(phongTro.getSoLuongToiDa() + "");
                            edtTienCoc.setText(phongTro.getTienCoc() + "");
                            tvQuan.setText(phongTro.getQuan().getTenQuan() + "");
                            idQuan = phongTro.getIdQuan();
                            tvPhuong.setText(phongTro.getPhuong().getTenPhuong() + "");
                            idPhuong = phongTro.getIdPhuong();
                            tvChonGioiTinh.setText(phongTro.getGioiTinh() == Const.ALL_GENDERS ? "Tất cả" : phongTro.getGioiTinh() == Const.MALE_GENDERS ? "Nam" : "Nữ");
                            gioiTinh = phongTro.getGioiTinh();
                            tvChonLoaiPhong.setText(phongTro.getLoaiPhong() == Const.PHONG_TRONG? "Phòng trống":phongTro.getLoaiPhong() == Const.PHONG_DON?"Phòng đơn":"Phòng ghép");
                            loaiPhong = phongTro.getLoaiPhong();
                            listTienIchSeleted.addAll(phongTro.getTienIch());
                            utilitiesSeletedAdapter.notifyDataSetChanged();
                            for (HinhAnh hinhAnh : phongTro.getHinhAnhPhongTro()) {
                                Glide.with(EditRoomActivity.this)
                                        .asBitmap()
                                        .load(Const.DOMAIN + hinhAnh.getHinh())
                                        .into(new SimpleTarget<Bitmap>() {
                                                  @Override
                                                  public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                      bitmapList.add(resource);
//                                                      String path = RealPathUtil.getRealPath(context, Uri.parse(Const.DOMAIN+hinhAnh.getHinh()));
//                                                      pathList.add(path);
                                                      imagesAdapter.notifyDataSetChanged();
                                                  }
                                              }
                                        );
                            }

                        }
                        imagesAdapter.notifyDataSetChanged();

                    }
                }


                @Override
                public void onFailure(Call<PhongTro> call, Throwable t) {

                }
            });
        }
    }


    private void anhXa() {
        //EditText
        edtSoPhong = findViewById(R.id.edtSoPhong);
        edtGia = findViewById(R.id.edtGia);
        edtDienTich = findViewById(R.id.edtDienTich);
        edtMota = findViewById(R.id.edtMota);
        edtDiaChiChiTiet = findViewById(R.id.edtDiaChiChiTiet);
        edtTienDien = findViewById(R.id.edtTienDien);
        edtTienNuoc = findViewById(R.id.edtTienNuoc);
        edtSoLuongToiDa = findViewById(R.id.edtSoLuongToiDa);
        edtTienCoc = findViewById(R.id.edtTienCoc);
        //TextView
        tvChonLoaiPhong = findViewById(R.id.tvChonLoaiPhong);
        tvChonHinh = findViewById(R.id.tvChonHinh);
        tvQuan = findViewById(R.id.tvQuan);
        tvPhuong = findViewById(R.id.tvPhuong);
        tvChonTienIch = findViewById(R.id.tvChonTienIch);
        tvXacNhanSua = findViewById(R.id.tvXacNhan);
        tvChonGioiTinh = findViewById(R.id.tvChonGioiTinh);
    }

    private void getListTienIch() {
        ApiServiceMinh.apiService.layTatCaTienIch().enqueue(new Callback<List<TienIch>>() {
            @Override
            public void onResponse(Call<List<TienIch>> call, Response<List<TienIch>> response) {
                if (response.code() == 200) {
                    listTienIch.clear();
                    listTienIch.addAll(response.body());
                }
                utilitiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TienIch>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
            }
        });
    }

    private void getQuan() {
        ApiServiceMinh.apiService.layTatCaQuan().enqueue(new Callback<List<Quan>>() {
            @Override
            public void onResponse(Call<List<Quan>> call, Response<List<Quan>> response) {
                if (response.code() == 200) {
                    lisQuan.clear();
                    lisQuan.addAll(response.body());
                    Log.d("TAG", "onResponse quan: " + lisQuan);
                }
                districtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Quan>> call, Throwable t) {

            }
        });
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = getLayoutInflater();
                View viewDialog = inflater.inflate(R.layout.chutro_dialog_choose_district_layout, null);
                builder.setView(viewDialog);
                AlertDialog dialog = builder.create();

                TextView tvXacNhan = viewDialog.findViewById(R.id.tvXacNhan);
                tvXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.hide();
                    }
                });
                rcvChonQuan = viewDialog.findViewById(R.id.rcvChonQuan);
                LinearLayoutManager layoutManagerQ = new LinearLayoutManager(context);
                layoutManagerQ.setOrientation(RecyclerView.VERTICAL);
                rcvChonQuan.setLayoutManager(layoutManagerQ);
                rcvChonQuan.setAdapter(districtAdapter);

                districtAdapter.setOnClick(new DistrictAdapter.OnClick() {
                    @Override
                    public void onClickItemListener(int position, View view) {
                        // Chưa chọn
                        if (positionSeletedQuan == -1) {
                            positionSeletedQuan = position;

                            LinearLayout bgrItem = view.findViewById(R.id.llQuan);
                            // lưu lại màu trước đó (lưu màu mặc định)
                            backColorQuan = bgrItem.getSolidColor();
                            bgrItem.setBackgroundColor(getResources().getColor(R.color.button_fb, getTheme()));
                            previousItemGroundQuan = bgrItem;
                            tvQuan.setText(lisQuan.get(position).getTenQuan());
                            getDataForListPhuong(position);
                        }
                        // đã chọn
                        else {
                            // chọn lại thì sẽ tắt màu
                            if (positionSeletedQuan == position) {
                                positionSeletedQuan = -1;
                                previousItemGroundQuan.setBackgroundColor(backColorQuan);
                                tvQuan.setText("Quận");
                            }
                            // chọn cái khác thì sẽ đổi màu cái mới chọn và cho cái trước đó về màu mặc định
                            else {
                                positionSeletedQuan = position;
                                previousItemGroundQuan.setBackgroundColor(backColorQuan);

                                LinearLayout bgrItem = view.findViewById(R.id.llQuan);
                                backColorQuan = bgrItem.getSolidColor();
                                bgrItem.setBackgroundColor(getResources().getColor(R.color.button_fb, getTheme()));
                                previousItemGroundQuan = bgrItem;
                                tvQuan.setText(lisQuan.get(position).getTenQuan());
                                getDataForListPhuong(position);
                            }
                        }

                    }
                });

                dialog.show();
            }
        });
        tvPhuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionSeletedQuan != -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = getLayoutInflater();
                    View viewDialog = inflater.inflate(R.layout.chutro_dialog_choose_ward_layout, null);
                    builder.setView(viewDialog);
                    AlertDialog dialog = builder.create();

                    TextView tvXacNhan = viewDialog.findViewById(R.id.tvXacNhan);
                    tvXacNhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.hide();
                        }
                    });
                    rcvChonPhuong = viewDialog.findViewById(R.id.rcvChonPhuong);
                    LinearLayoutManager layoutManagerP = new LinearLayoutManager(context);
                    layoutManagerP.setOrientation(RecyclerView.VERTICAL);
                    rcvChonPhuong.setLayoutManager(layoutManagerP);
                    rcvChonPhuong.setAdapter(wardAdapter);
                    dialog.show();
                    wardAdapter.setOnClick(new WardAdapter.OnClick() {
                        @Override
                        public void onClickItemListener(int position, View view) {
                            // Chưa chọn
                            if (positionSeletedPhuong == -1) {
                                positionSeletedPhuong = position;

                                LinearLayout bgrItemPhuong = view.findViewById(R.id.llPhuong);
                                // lưu lại màu trước đó (lưu màu mặc định)
                                backColorPhuong = bgrItemPhuong.getSolidColor();
                                bgrItemPhuong.setBackgroundColor(getResources().getColor(R.color.button_fb, getTheme()));
                                previousItemGroundPhuong = bgrItemPhuong;
                                tvPhuong.setText(listPhuong.get(position).getTenPhuong());
                            }
                            // đã chọn
                            else {
                                // chọn lại thì sẽ tắt màu
                                if (positionSeletedPhuong == position) {
                                    positionSeletedPhuong = -1;
                                    previousItemGroundPhuong.setBackgroundColor(backColorPhuong);
                                    tvPhuong.setText("Phường");
                                }
                                // chọn cái khác thì sẽ đổi màu cái mới chọn và cho cái trước đó về màu mặc định
                                else {
                                    positionSeletedPhuong = position;
                                    previousItemGroundPhuong.setBackgroundColor(backColorPhuong);

                                    LinearLayout bgrItemPhuong = view.findViewById(R.id.llPhuong);
                                    backColorPhuong = bgrItemPhuong.getSolidColor();
                                    bgrItemPhuong.setBackgroundColor(getResources().getColor(R.color.button_fb, getTheme()));
                                    previousItemGroundPhuong = bgrItemPhuong;
                                    tvPhuong.setText(listPhuong.get(position).getTenPhuong());
                                }
                            }
                        }
                    });
                }
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


                TextView tvXacNhan = viewDialog.findViewById(R.id.tvXacNhan);
                tvXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.hide();
                    }
                });
                rcvChonTienIch = viewDialog.findViewById(R.id.rcvChonTienIch);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(EditRoomActivity.this);
                layoutManager1.setOrientation(RecyclerView.VERTICAL);
                rcvChonTienIch.setLayoutManager(layoutManager1);
                rcvChonTienIch.setAdapter(utilitiesAdapter);

                utilitiesAdapter.setOnClick(new UtilitiesAdapter.OnClick() {
                    @Override
                    public void checkBox(int position, View v) {
                        utilitiesSeletedAdapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });
        tvChonGioiTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = getLayoutInflater();
                View viewDialog = inflater.inflate(R.layout.chutro_dialog_choose_gender_layout, null);
                builder.setView(viewDialog);
                AlertDialog dialog = builder.create();

                TextView tvXacNhan = viewDialog.findViewById(R.id.tvXacNhan);
                tvXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.hide();
                    }
                });
                rcvGioiTinh = viewDialog.findViewById(R.id.rcvChonGioiTinh);
                LinearLayoutManager layoutManagerGT = new LinearLayoutManager(EditRoomActivity.this);
                layoutManagerGT.setOrientation(RecyclerView.VERTICAL);
                rcvGioiTinh.setLayoutManager(layoutManagerGT);
                rcvGioiTinh.setAdapter(genderAdapter);

                genderAdapter.setOnClick(new GenderAdapter.OnClick() {
                    @Override
                    public void onClickGender(int position, View view) {
                        // Chưa chọn
                        if (positionSeletedGioiTinh == -1) {
                            positionSeletedGioiTinh = position;

                            LinearLayout bgrItemGioiTinh = view.findViewById(R.id.llGioiTinh);
                            // lưu lại màu trước đó (lưu màu mặc định)
                            backColorGioiTinh = bgrItemGioiTinh.getSolidColor();
                            bgrItemGioiTinh.setBackgroundColor(getResources().getColor(R.color.button_fb, getTheme()));
                            previousItemGroundGioiTinh = bgrItemGioiTinh;
                            tvChonGioiTinh.setText(gioiTinhs.get(position).getGioiTinh());
                        }
                        // đã chọn
                        else {
                            // chọn lại thì sẽ tắt màu
                            if (positionSeletedGioiTinh == position) {
                                positionSeletedGioiTinh = -1;
                                previousItemGroundGioiTinh.setBackgroundColor(backColorGioiTinh);
                                tvPhuong.setText("Chọn giới tính");
                            }
                            // chọn cái khác thì sẽ đổi màu cái mới chọn và cho cái trước đó về màu mặc định
                            else {
                                positionSeletedGioiTinh = position;
                                previousItemGroundGioiTinh.setBackgroundColor(backColorGioiTinh);

                                LinearLayout bgrItemGioiTinh = view.findViewById(R.id.llGioiTinh);
                                backColorGioiTinh = bgrItemGioiTinh.getSolidColor();
                                bgrItemGioiTinh.setBackgroundColor(getResources().getColor(R.color.button_fb, getTheme()));
                                previousItemGroundGioiTinh = bgrItemGioiTinh;
                                tvChonGioiTinh.setText(gioiTinhs.get(position).getGioiTinh());
                            }
                        }
                    }
                });

                dialog.show();
            }
        });
        tvChonLoaiPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = getLayoutInflater();
                View viewDialog = inflater.inflate(R.layout.chutro_dialog_choose_loai_phong_layout, null);
                builder.setView(viewDialog);
                AlertDialog dialog = builder.create();

                TextView tvXacNhan = viewDialog.findViewById(R.id.tvXacNhan);
                tvXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.hide();
                    }
                });
                rcvLoaiPhong = viewDialog.findViewById(R.id.rcvLoaiPhong);
                LinearLayoutManager layoutManagerLP = new LinearLayoutManager(EditRoomActivity.this);
                layoutManagerLP.setOrientation(RecyclerView.VERTICAL);
                rcvLoaiPhong.setLayoutManager(layoutManagerLP);

                rcvLoaiPhong.setAdapter(loaiPhongAdapter);
                loaiPhongAdapter.setOnClick(new LoaiPhongAdapter.OnClick() {
                    @Override
                    public void onClickItemListener(int position, View view) {
                        // Chưa chọn
                        if (positionSeletedLoaiPhong == -1) {
                            positionSeletedLoaiPhong = position;

                            LinearLayout bgrItemLoaiPhong = view.findViewById(R.id.llLoaiPhong);
                            // lưu lại màu trước đó (lưu màu mặc định)
                            backColorLoaiPhong = bgrItemLoaiPhong.getSolidColor();
                            bgrItemLoaiPhong.setBackgroundColor(getResources().getColor(R.color.button_fb, getTheme()));
                            previousItemGroundLoaiPhong = bgrItemLoaiPhong;
                            tvChonLoaiPhong.setText(listLoaiPhong.get(position) == Const.PHONG_TRONG? "Phòng trống":listLoaiPhong.get(position) == Const.PHONG_DON?"Phòng đơn":"Phòng ghép");
                        }
                        // đã chọn
                        else {
                            // chọn lại thì sẽ tắt màu
                            if (positionSeletedLoaiPhong == position) {
                                positionSeletedLoaiPhong = -1;
                                previousItemGroundLoaiPhong.setBackgroundColor(backColorLoaiPhong);
                                tvChonLoaiPhong.setText("Chọn loại phòng");
                            }
                            // chọn cái khác thì sẽ đổi màu cái mới chọn và cho cái trước đó về màu mặc định
                            else {
                                positionSeletedLoaiPhong = position;
                                previousItemGroundLoaiPhong.setBackgroundColor(backColorLoaiPhong);

                                LinearLayout bgrItemLoaiPhong = view.findViewById(R.id.llLoaiPhong);
                                backColorLoaiPhong = bgrItemLoaiPhong.getSolidColor();
                                bgrItemLoaiPhong.setBackgroundColor(getResources().getColor(R.color.button_fb, getTheme()));
                                previousItemGroundLoaiPhong = bgrItemLoaiPhong;
                                tvChonLoaiPhong.setText(listLoaiPhong.get(position) == Const.PHONG_TRONG? "Phòng trống":listLoaiPhong.get(position) == Const.PHONG_DON?"Phòng đơn":"Phòng ghép");
                            }
                        }
                    }
                });


                dialog.show();
            }
        });
        tvXacNhanSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSoPhong.getText().toString().trim().equals("") == false && edtGia.getText().toString().trim().equals("") == false && edtDienTich.getText().toString().trim().equals("") == false && edtMota.getText().toString().trim().equals("") == false && edtDiaChiChiTiet.getText().toString().trim().equals("") == false && edtTienDien.getText().toString().trim().equals("") == false && edtTienNuoc.getText().toString().trim().equals("") == false && edtSoLuongToiDa.getText().toString().trim().equals("") == false && edtTienCoc.getText().toString().trim().equals("") == false ) {
                    if (positionSeletedQuan != -1) {
                        idQuan = lisQuan.get(positionSeletedQuan).getId();
                    }
                    if (positionSeletedPhuong != -1) {
                        idPhuong = listPhuong.get(positionSeletedPhuong).getId();
                    }
                    if (positionSeletedGioiTinh != -1) {
                        gioiTinh = gioiTinhs.get(positionSeletedGioiTinh).getId();
                    }
                    if (positionSeletedLoaiPhong != -1){
                        loaiPhong = listLoaiPhong.get(positionSeletedLoaiPhong);
                    }
                    //Sửa phòng
                    List<MultipartBody.Part> partList = new LinkedList<>();
                    List<MultipartBody.Part> partTienIch = new LinkedList<>();
                    RequestBody requestBodyIDPhong = RequestBody.create(MediaType.parse("miltipart/form-data"), idPhong + "");
                    RequestBody requestBodySoPhong = RequestBody.create(MediaType.parse("miltipart/form-data"), edtSoPhong.getText().toString().trim());
                    RequestBody requestBodyGia = RequestBody.create(MediaType.parse("miltipart/form-data"), edtGia.getText().toString().trim());
                    RequestBody requestBodyDienTich = RequestBody.create(MediaType.parse("miltipart/form-data"), edtDienTich.getText().toString().trim());
                    RequestBody requestBodyMoTa = RequestBody.create(MediaType.parse("miltipart/form-data"), edtMota.getText().toString().trim());
                    RequestBody requestBodyDiaChiChiTiet = RequestBody.create(MediaType.parse("miltipart/form-data"), edtDiaChiChiTiet.getText().toString().trim());
                    RequestBody requestBodyTienDien = RequestBody.create(MediaType.parse("miltipart/form-data"), edtTienDien.getText().toString().trim());
                    RequestBody requestBodyTienNuoc = RequestBody.create(MediaType.parse("miltipart/form-data"), edtTienNuoc.getText().toString().trim());
                    RequestBody requestBodyIDQuan = RequestBody.create(MediaType.parse("miltipart/form-data"), idQuan + "");
                    RequestBody requestBodyIDPhuong = RequestBody.create(MediaType.parse("miltipart/form-data"), idPhuong + "");
                    RequestBody requestBodyGioiTinh = RequestBody.create(MediaType.parse("miltipart/form-data"), gioiTinh + "");
                    RequestBody requestBodySoLuongToiDa = RequestBody.create(MediaType.parse("miltipart/form-data"), edtSoLuongToiDa.getText().toString().trim());
                    RequestBody requestBodyTienCoc = RequestBody.create(MediaType.parse("miltipart/form-data"), edtTienCoc.getText().toString().trim());

                    for (String path : pathList) {
                        File file = new File(path);
                        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("miltipart/form-data"), file);
                        MultipartBody.Part multiPartImage = MultipartBody.Part.createFormData("hinh[]", file.getName(), requestBodyImage);
                        partList.add(multiPartImage);
                    }
                    Gson gson = new Gson();
                    Log.d("TAG", "onClick: "+gson.toJson(listTienIchSeleted));
                    RequestBody requestBodyListTienIch = RequestBody.create(MediaType.parse("miltipart/form-data"), gson.toJson(listTienIchSeleted));
                    RequestBody requestBodyLoaiPhong = RequestBody.create(MediaType.parse("miltipart/form-data"),  loaiPhong+ "");
                    Call<Integer> call = ApiServiceMinh.apiService.suaPhongTroTheoIdChuTro(
                            requestBodyIDPhong,
                            requestBodySoPhong,
                            requestBodyGia,
                            requestBodyDienTich,
                            requestBodyMoTa,
                            requestBodyIDQuan,
                            requestBodyIDPhuong,
                            requestBodyGioiTinh,
                            requestBodyDiaChiChiTiet,
                            requestBodySoLuongToiDa,
                            requestBodyTienCoc,
                            requestBodyTienDien,
                            requestBodyTienNuoc,
                            partList,
                            requestBodyListTienIch,
                            requestBodyLoaiPhong
                    );
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.code() == 200) {
                                if (response.body() == 1) {
                                    Toast.makeText(EditRoomActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.d("TAG", "onFailure create: " + t);
                            Log.d("TAG", "onFailure create: " + call.request());
                        }
                    });
                } else {
                    Toast.makeText(context, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void checkPesmission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, RQ);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void getDataForListPhuong(int positionSeletedQuan) {
        Log.d("TAG", "getDataForListPhuong: " + ">>>>>>" + positionSeletedQuan);
        ApiServiceMinh.apiService.layTatCaPhuongTheoQuan(lisQuan.get(positionSeletedQuan).getId()).enqueue(new Callback<List<Phuong>>() {
            @Override
            public void onResponse(Call<List<Phuong>> call, Response<List<Phuong>> response) {
                if (response.code() == 200) {
                    listPhuong.clear();
                    listPhuong.addAll(response.body());
                    wardAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Phuong>> call, Throwable t) {

            }
        });
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data.getClipData() != null) {
//                            imgUri = data.getData();
                            for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                                imgUri = data.getClipData().getItemAt(i).getUri();
                                if (uriList.contains(imgUri) == false) {
                                    bitmapList.add(BitmapFactory.decodeFile(RealPathUtil.getRealPath(context, imgUri)));
                                    pathList.add(RealPathUtil.getRealPath(context, imgUri));
                                    Log.d("TAG", "onActivityResult: " + imgUri);
                                }
                            }
                        } else {

                            imgUri = data.getData();
                            Log.d("TAG", "onActivityResult 1: " + imgUri);
                            if (uriList.contains(imgUri) == false) {
                                uriList.add(imgUri);
                                bitmapList.add(BitmapFactory.decodeFile(RealPathUtil.getRealPath(context, imgUri)));
                                pathList.add(RealPathUtil.getRealPath(context, imgUri));
                            }
                        }
                        imagesAdapter.notifyDataSetChanged();


                    }
                }
            }
    );
}