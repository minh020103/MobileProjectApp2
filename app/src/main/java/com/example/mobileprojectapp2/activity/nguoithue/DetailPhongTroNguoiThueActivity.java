package com.example.mobileprojectapp2.activity.nguoithue;

import static com.example.mobileprojectapp2.api.Const.MALE_GENDERS;
import static com.example.mobileprojectapp2.api.Const.PHONG_DON;
import static com.example.mobileprojectapp2.api.Const.PHONG_TRONG;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.chutro.DetailPhongTroActivity;
import com.example.mobileprojectapp2.activity.chutro.PhongNhanTinActivity;
import com.example.mobileprojectapp2.activity.chutro.SearchActivity;
import com.example.mobileprojectapp2.activity.chutro.RenterDetailActivity;
import com.example.mobileprojectapp2.activity.chutro.ReviewRoomActivity;
import com.example.mobileprojectapp2.activity.chutro.ZoomOutPageTransformer;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.chutro.ApiServiceNghiem;
import com.example.mobileprojectapp2.api.chutro.ApiServicePhuc;
import com.example.mobileprojectapp2.api.nguoithue.ApiServicePhuc2;

import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodel.PhongNguoiThue;
import com.example.mobileprojectapp2.datamodel.PhongTinNhan;
import com.example.mobileprojectapp2.datamodel.VideoReview;
import com.example.mobileprojectapp2.model.ChuTro;
import com.example.mobileprojectapp2.model.PhongTro;
import com.example.mobileprojectapp2.model.PhongTroGoiY;
import com.example.mobileprojectapp2.model.TienIch;
import com.example.mobileprojectapp2.model.YeuCauDatPhong;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.HinhAnhAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.PhongTroChuTroAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.TienIchAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucDanhSachPhongGoiYAdapter;
import com.example.mobileprojectapp2.recyclerviewadapter.nguoithue.PhucNguoiThueAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPhongTroNguoiThueActivity extends AppCompatActivity {


    private TextView tvLoaiPhongNguoiThue, tvGioTinhNguoiThue, tvGiaNguoiThue, tvSoLuongToiDaNguoiThue, tvDienTichNguoiThue,
            tvTienCocNguoiThue, tvTienDienNguoiThue, tvTienNuocNguoiThue, tvQuanNguoiThue, tvDiaChiNguoiThue, tvTienIchRong,
            tvTenChuTro, tvSDTChuTro, tv_dsnt;
    private ImageView img_hinh_anh_rong, image_gif;
    private ReadMoreTextView tvMoTaNguoiThue;
    private TienIchAdapter adapterTienIch;
    private ImageView imageBack, imageViewChuTro;
    private LinearLayoutManager layoutManagerTienIch = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
    private LinearLayoutManager layoutManagerNguoiThue = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
    private LinearLayoutManager layoutManagerPhongGoiY = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);

    private RecyclerView rcvListTienIchNguoiThue, rcvListNguoiThue, rcvDSPhongGoiY;
    private HinhAnhAdapter adapterHinhAnh;
    private ViewPager2 mViewPager2;
    private List<TienIch> listTienIch;
    private List<HinhAnh> listHinhAnh;
    private List<PhongNguoiThue> listNguoiThue;
    private PhucNguoiThueAdapter adapterNguoiThue;
    private List<com.example.mobileprojectapp2.datamodels.PhongTro> listPhongGoiY;
    private PhucDanhSachPhongGoiYAdapter adapterPhongGoiY;
    private RelativeLayout rlt_tren_dsnt;
    private int idPhong;
    private int idTaiKhoan;
    private LinearLayout llXemThem, llThuGon, ll_dsnt, llDatPhong, llGoi, llChat, ll_dsp_chu_tro;
    private int idTaiKhoanNhan;
    private ProgressDialog mProgressDialog;
    SharedPreferences sharedPreferences;
    Intent intentChuTro;
    Intent intentNguoiThue;
       MaterialButton reviewPhong;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == listHinhAnh.size() - 1) {
                mViewPager2.setCurrentItem(0);
            } else {
                // Next sang page tiep theo
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nguoithue_detail_phong_tro_layout);

        intentChuTro = new Intent(DetailPhongTroNguoiThueActivity.this, PhongNhanTinActivity.class);
        intentNguoiThue = new Intent(DetailPhongTroNguoiThueActivity.this, PhongNhanTinActivity.class);
        listTienIch = new ArrayList<>();
        listHinhAnh = new ArrayList<>();
        listNguoiThue = new ArrayList<>();
        listPhongGoiY = new ArrayList<>();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ...");

        sharedPreferences = this.getSharedPreferences(Const.PRE_LOGIN, Context.MODE_PRIVATE);
        idTaiKhoan = sharedPreferences.getInt("idTaiKhoan", -1);

        Intent intent = getIntent();
        idPhong = intent.getIntExtra("idPhong", -1);

        anhXa();
        reviewPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xemVideoReview();
            }
        });
        getDataFromApi();
//        getNguoiThue();

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                //Thoi gian chuyen sile
                handler.postDelayed(runnable, 5000);
            }
        });

        mViewPager2.setPageTransformer(new ZoomOutPageTransformer());

        adapterNguoiThue.setMyOnClickListener(new PhongTroChuTroAdapter.MyOnClickListener() {
            @Override
            public void OnClickItem(int position, View v) {
                if (listNguoiThue.get(position).getNguoiThue().getIdTaiKhoan() != idTaiKhoan) {
                    Call<Integer> call = ApiServiceNghiem.apiService.layIdPhongTinNhan(idTaiKhoan, listNguoiThue.get(position).getNguoiThue().getIdTaiKhoan());
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response != null) {
                                if (response.body() == -1) {
                                    RequestBody senderID = RequestBody.create(MediaType.parse("multipart/form-data"), idTaiKhoan + "");
                                    RequestBody nguoiThueID = RequestBody.create(MediaType.parse("multipart/form-data"), listNguoiThue.get(position).getNguoiThue().getIdTaiKhoan() + "");
                                    Call<PhongTinNhan> taoPhong = ApiServiceNghiem.apiService.taoPhongTinNhan(senderID, nguoiThueID);
                                    taoPhong.enqueue(new Callback<PhongTinNhan>() {
                                        @Override
                                        public void onResponse(Call<PhongTinNhan> call, Response<PhongTinNhan> response) {
                                            if (response != null) {
                                                intentNguoiThue.putExtra("idPhong", response.body().getId());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<PhongTinNhan> call, Throwable t) {

                                        }
                                    });
                                } else {
                                    intentNguoiThue.putExtra("idPhong", response.body());
                                }
                                intentNguoiThue.putExtra("idDoiPhuong", listNguoiThue.get(position).getNguoiThue().getIdTaiKhoan());
                                intentNguoiThue.putExtra("ten", listNguoiThue.get(position).getNguoiThue().getTen());
                                intentNguoiThue.putExtra("hinh", listNguoiThue.get(position).getNguoiThue().getHinh());
                                startActivity(intentNguoiThue);
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                } else {
                    alertSuccess("Không Thể Nhắn Tin Cho Chính Bạn!");
                }
            }
        });

        getDanhSachPhongGoiY();


        llChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentChuTro);
            }
        });

        adapterPhongGoiY.setMyOnCLickListener(new PhucDanhSachPhongGoiYAdapter.MyOnCLickListener() {
            @Override
            public void OnClickItem(int position, View v) {
                alertSuccess("ok");
                Intent intent = new Intent(DetailPhongTroNguoiThueActivity.this,DetailPhongTroNguoiThueActivity.class);
                intent.putExtra("idPhong",listPhongGoiY.get(position).getId());
                startActivity(intent);
            }
        });


    }

    private void capNhatPhongGoiY(int idTaiKhoan, int idQuan, int tienCoc, int gioiTinh) {
        RequestBody idTaiKhoanApi = RequestBody.create(MediaType.parse("multipart/form-data"), idTaiKhoan+"");
        RequestBody idQuanApi = RequestBody.create(MediaType.parse("multipart/form-data"), idQuan+"");
        RequestBody tienCocApi = RequestBody.create(MediaType.parse("multipart/form-data"), tienCoc+"");
        RequestBody gioiTinhApi = RequestBody.create(MediaType.parse("multipart/form-data"), gioiTinh+"");
        Call<Integer> call = ApiServicePhuc2.apiService.capNhatPhongGoiY(idTaiKhoanApi,idQuanApi,tienCocApi,gioiTinhApi);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(DetailPhongTroNguoiThueActivity.this, "gay r ba", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDanhSachPhongGoiY();
        adapterPhongGoiY.notifyDataSetChanged();
    }

    private void xemVideoReview(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = DetailPhongTroNguoiThueActivity.this.getLayoutInflater().inflate(R.layout.activity_review_phong,null);
        dialog.setView(view);
        AlertDialog dialogB = dialog.create();
        dialogB.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView dong = view.findViewById(R.id.dongDialog);
        TextView ghiChuHuongDan = view.findViewById(R.id.ghiChuHuongDan);
        WebView webView = view.findViewById(R.id.videoReview);

        Call<VideoReview> call = com.example.mobileprojectapp2.api.nguoithue.ApiServiceNghiem.apiService.getVideoReview(idPhong);
        call.enqueue(new Callback<VideoReview>() {
            @Override
            public void onResponse(Call<VideoReview> call, Response<VideoReview> response) {
                if(response.body()!=null){
                    ghiChuHuongDan.setVisibility(View.INVISIBLE);
                    VideoReview videoReview = response.body();
                    if(!videoReview.getLinkVideo().equals("Video_Rong")){
                        if(videoReview.getLoaiVideo()==0){
                            String video = Const.DOMAIN+response.body().getLinkVideo();
                            webView.loadUrl(video);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.setWebChromeClient(new WebChromeClient());
                        }else{
                            String dataUrl = "<!DOCTYPE html>\n" +
                                    "<html>\n" +
                                    "  <body>\n" +
                                    "    <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->\n" +
                                    "    <div id=\"player\"></div>\n" +
                                    "\n" +
                                    "    <script>\n" +
                                    "      // 2. This code loads the IFrame Player API code asynchronously.\n" +
                                    "      var tag = document.createElement('script');\n" +
                                    "\n" +
                                    "      tag.src = \"https://www.youtube.com/iframe_api\";\n" +
                                    "      var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
                                    "      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
                                    "\n" +
                                    "      // 3. This function creates an <iframe> (and YouTube player)\n" +
                                    "      //    after the API code downloads.\n" +
                                    "      var player;\n" +
                                    "      function onYouTubeIframeAPIReady() {\n" +
                                    "        player = new YT.Player('player', {\n" +
                                    "          height: '240',\n" +
                                    "          width: '100%',\n" +
                                    "          videoId: '"+ videoReview.getLinkVideo()+"',\n" +
                                    "          playerVars: {\n" +
                                    "            'playsinline': 1\n" +
                                    "          },\n" +
                                    "          events: {\n" +
                                    "            'onReady': onPlayerReady,\n" +
                                    "            'onStateChange': onPlayerStateChange\n" +
                                    "          }\n" +
                                    "        });\n" +
                                    "      }\n" +
                                    "\n" +
                                    "      // 4. The API will call this function when the video player is ready.\n" +
                                    "      function onPlayerReady(event) {\n" +
                                    "        event.target.playVideo();\n" +
                                    "      }\n" +
                                    "\n" +
                                    "      // 5. The API calls this function when the player's state changes.\n" +
                                    "      //    The function indicates that when playing a video (state=1),\n" +
                                    "      //    the player should play for six seconds and then stop.\n" +
                                    "      var done = false;\n" +
                                    "      function onPlayerStateChange(event) {\n" +
                                    "        if (event.data == YT.PlayerState.PLAYING && !done) {\n" +
                                    "          setTimeout(stopVideo, 10000);\n" +
                                    "          done = true;\n" +
                                    "        }\n" +
                                    "      }\n" +
                                    "      function stopVideo() {\n" +
                                    "        player.stopVideo();\n" +
                                    "      }\n" +
                                    "    </script>\n" +
                                    "  </body>\n" +
                                    "</html>";


                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.setWebViewClient(new WebViewClient(){
                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return true;
                                }
                            });
//                            webView.getSettings().getMixedContentMode();
                            webView.loadData(dataUrl,"text/html","utf-8");
                            webView.setWebChromeClient(new WebChromeClient());
                        }
                    }else{
                        ghiChuHuongDan.setVisibility(View.VISIBLE);
                    }
                }else{
                    ghiChuHuongDan.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<VideoReview> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lỗi Dữ Liệu",Toast.LENGTH_SHORT).show();
            }
        });
        dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogB.hide();
            }
        });
        dialogB.show();
    }
    private void requestYeuCauDatPhong(int idNhan) {
        llDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPhongTroNguoiThueActivity.this);
                builder.setMessage("Bạn chắc chắn muốn đặt phòng này ?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<YeuCauDatPhong> call = ApiServicePhuc2.apiService.yeuCauDatPhong(idTaiKhoan, idNhan, idPhong);
                                call.enqueue(new Callback<YeuCauDatPhong>() {
                                    @Override
                                    public void onResponse(Call<YeuCauDatPhong> call, Response<YeuCauDatPhong> responseYC) {
                                        databaseReference.child("notification").child(idNhan + "").child(responseYC.body().getId() + "").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("TAG", "onSuccess: PUSH NOTIFICATION REALTIME");
                                            }
                                        });
                                        alertSuccess("Bạn đã đặt phòng thành công!");
//                                        llDatPhong.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<YeuCauDatPhong> call, Throwable t) {
                                        Toast.makeText(DetailPhongTroNguoiThueActivity.this, "Error not call Api", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mProgressDialog.cancel();
                            }
                        });
                builder.create();
                builder.show();
            }
        });


    }


    private void getDataFromApi() {
        Call<PhongTro> call = ApiServicePhuc.apiService.getPhongTroByID(idPhong);
        call.enqueue(new Callback<PhongTro>() {
            @Override
            public void onResponse(Call<PhongTro> call, Response<PhongTro> response) {
                tvDienTichNguoiThue.setText(response.body().getDienTich() + "㎡");
                tvQuanNguoiThue.setText(response.body().getIdQuan() + "");
                tvGiaNguoiThue.setText(response.body().getGia() + " / tháng");
                if (response.body().getLoaiPhong() == PHONG_DON || response.body().getLoaiPhong() == PHONG_TRONG) {
                    tvLoaiPhongNguoiThue.setText("Phòng đơn");
                    ll_dsnt.setVisibility(View.GONE);
                    tv_dsnt.setVisibility(View.GONE);
                    rlt_tren_dsnt.setVisibility(View.GONE);
                } else {
                    tvLoaiPhongNguoiThue.setText("Phòng ghép");
                    ll_dsnt.setVisibility(View.VISIBLE);
                    getDanhSachNguoiThueGoiY();
                    rlt_tren_dsnt.setVisibility(View.VISIBLE);
                }

                tvMoTaNguoiThue.setText(response.body().getMoTa());
                if (response.body().getGioiTinh() == MALE_GENDERS) {
                    tvGioTinhNguoiThue.setText("Nam ♂");
                } else {
                    tvGioTinhNguoiThue.setText("Nữ ♀");
                }
                tvTienDienNguoiThue.setText(response.body().getTienDien() + "k");
                tvTienCocNguoiThue.setText(response.body().getTienCoc() + " ₫");
                tvTienNuocNguoiThue.setText(response.body().getTienNuoc() + "k");
                tvSoLuongToiDaNguoiThue.setText(response.body().getSoLuongToiDa() + " người");
                tvDiaChiNguoiThue.setText(response.body().getDiaChiChiTiet());


                if (response.body().getHinhAnhPhongTro().size() == 0) {
//                    tvHinhAnhRong.setVisibility(View.VISIBLE);
                    img_hinh_anh_rong.setVisibility(View.VISIBLE);
                    mViewPager2.setVisibility(View.GONE);

                }
                for (HinhAnh hinhAnh : response.body().getHinhAnhPhongTro()) {
                    listHinhAnh.add(hinhAnh);
                }
                adapterHinhAnh.notifyDataSetChanged();

                if (response.body().getDanhSachTienIch().size() == 0) {
                    tvTienIchRong.setVisibility(View.VISIBLE);
                }
                if (response.body().getDanhSachTienIch().size() < 8) {
                    llXemThem.setVisibility(View.GONE);
                }
                int i = 0;
                for (TienIch tienIch : response.body().getDanhSachTienIch()) {
                    listTienIch.add(tienIch);
                    i++;
                    if (i == 8) {
                        break;
                    }
                }
                adapterTienIch.notifyDataSetChanged();
                llXemThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listTienIch.clear();
                        for (TienIch tienIch : response.body().getDanhSachTienIch()) {
                            listTienIch.add(tienIch);
                        }
                        adapterTienIch.notifyDataSetChanged();
                        llXemThem.setVisibility(View.GONE);
                        llThuGon.setVisibility(View.VISIBLE);
                    }
                });
                llThuGon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listTienIch.clear();
                        int i = 0;
                        for (TienIch tienIch : response.body().getDanhSachTienIch()) {
                            listTienIch.add(tienIch);
                            i++;
                            if (i == 8)
                                break;
                        }
                        adapterTienIch.notifyDataSetChanged();
                        llXemThem.setVisibility(View.VISIBLE);
                        llThuGon.setVisibility(View.GONE);
                    }
                });
                if (response.body().getPhongTroChuTro() != null) {
                    int idNhan = response.body().getPhongTroChuTro().getIdTaiKhoan();
                    RequestBody senderID = RequestBody.create(MediaType.parse("multipart/form-data"), idTaiKhoan + "");
                    RequestBody nguoiThueID = RequestBody.create(MediaType.parse("multipart/form-data"), idNhan + "");
                    Call<Integer> calll = ApiServiceNghiem.apiService.layIdPhongTinNhan(idTaiKhoan, response.body().getPhongTroChuTro().getIdTaiKhoan());
                    calll.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.body() == -1) {
                                Call<PhongTinNhan> taoPhong = ApiServiceNghiem.apiService.taoPhongTinNhan(senderID, nguoiThueID);
                                taoPhong.enqueue(new Callback<PhongTinNhan>() {
                                    @Override
                                    public void onResponse(Call<PhongTinNhan> call, Response<PhongTinNhan> response) {
                                        if (response != null) {
                                            intentChuTro.putExtra("idPhong", response.body().getId());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PhongTinNhan> call, Throwable t) {

                                    }
                                });
                            } else {
                                intentChuTro.putExtra("idPhong", response.body());
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                    tvTenChuTro.setText(response.body().getPhongTroChuTro().getTen());
                    tvSDTChuTro.setText(response.body().getPhongTroChuTro().getSoDienThoai());
                    idTaiKhoanNhan = response.body().getPhongTroChuTro().getId();
                    int idNhanYC = response.body().getPhongTroChuTro().getIdTaiKhoan();
                    requestYeuCauDatPhong(idNhanYC);
                    Glide.with(DetailPhongTroNguoiThueActivity.this.getLayoutInflater().getContext()).load(Const.DOMAIN + response.body().getPhongTroChuTro().getHinh()).into(imageViewChuTro);
                    intentChuTro.putExtra("idDoiPhuong", response.body().getPhongTroChuTro().getIdTaiKhoan());
                    intentChuTro.putExtra("ten", response.body().getPhongTroChuTro().getTen());
                    intentChuTro.putExtra("hinh", response.body().getPhongTroChuTro().getHinh());
                    capNhatPhongGoiY(idTaiKhoan, response.body().getIdQuan(), response.body().getTienCoc(), response.body().getGioiTinh());
                    getDanhSachPhongGoiY();
                    ll_dsp_chu_tro.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DetailPhongTroNguoiThueActivity.this, DanhSachPhongTheoChuTroActivity.class);
                            intent.putExtra("idTaiKhoan",response.body().getPhongTroChuTro().getIdTaiKhoan());
                            Log.d("TAG", "onClick: "+ response.body().getPhongTroChuTro().getIdTaiKhoan());
                            intent.putExtra("idChuTro",response.body().getPhongTroChuTro().getId());
                            Log.d("TAG", "onClick: "+ response.body().getPhongTroChuTro().getId());



                            startActivity(intent);
                        }
                    });
                }
                else{
                    alertSuccess("Đéo có chủ trọ mà có phòng");
                }
                llGoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone = "tel:" + tvSDTChuTro.getText();
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse(phone));
                        startActivity(callIntent);
                    }
                });


            }

            @Override
            public void onFailure(Call<PhongTro> call, Throwable t) {
                Toast.makeText(DetailPhongTroNguoiThueActivity.this, "Error not call Api", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDanhSachNguoiThueGoiY() {
        Call<List<PhongNguoiThue>> call = ApiServicePhuc2.apiService.getNguoiThueTheoPhong(idPhong);
        call.enqueue(new Callback<List<PhongNguoiThue>>() {
            @Override
            public void onResponse(Call<List<PhongNguoiThue>> call, Response<List<PhongNguoiThue>> response) {
                if (response.code() == 200) {
                    listNguoiThue.clear();
                    listNguoiThue.addAll(response.body());
                    adapterNguoiThue.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhongNguoiThue>> call, Throwable t) {
                Toast.makeText(DetailPhongTroNguoiThueActivity.this, "Error not call Api", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getDanhSachPhongGoiY() {
        Call<List<com.example.mobileprojectapp2.datamodels.PhongTro>> call = ApiServicePhuc2.apiService.getDanhSachPhongGoiYTheoQuan(idTaiKhoan);
        call.enqueue(new Callback<List<com.example.mobileprojectapp2.datamodels.PhongTro>>() {
            @Override
            public void onResponse(Call<List<com.example.mobileprojectapp2.datamodels.PhongTro>> call, Response<List<com.example.mobileprojectapp2.datamodels.PhongTro>> response) {
                if (response.code() == 200) {
                    listPhongGoiY.clear();
                    listPhongGoiY.addAll(response.body());
                    adapterPhongGoiY.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<com.example.mobileprojectapp2.datamodels.PhongTro>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        tvDienTichNguoiThue = findViewById(R.id.tv_detail_dien_tich_nguoi_thue);
        tvQuanNguoiThue = findViewById(R.id.tv_detail_quan_nguoi_thue);
        tvMoTaNguoiThue = findViewById(R.id.tv_detail_mo_ta_nguoi_thue);
        tvGiaNguoiThue = findViewById(R.id.tv_detail_gia_nguoi_thue);
        tvLoaiPhongNguoiThue = findViewById(R.id.tv_detail_loai_phong_nguoi_thue);
        tvSoLuongToiDaNguoiThue = findViewById(R.id.tv_detail_so_luong_toi_da_nguoi_thue);
        tvTienCocNguoiThue = findViewById(R.id.tv_detail_tien_coc_nguoi_thue);
        tvTienDienNguoiThue = findViewById(R.id.tv_detail_tien_dien_nguoi_thue);
        tvTienNuocNguoiThue = findViewById(R.id.tv_detail_tien_nuoc_nguoi_thue);
        tvGioTinhNguoiThue = findViewById(R.id.tv_detail_gio_tinh_nguoi_thue);
        tvDiaChiNguoiThue = findViewById(R.id.tv_detail_dia_chi_nguoi_thue);
        rcvListTienIchNguoiThue = findViewById(R.id.rcv_list_tien_ich_nguoi_thue);
        rcvListNguoiThue = findViewById(R.id.rcv_list_nguoi_thue);
        rlt_tren_dsnt = findViewById(R.id.rlt_tren_dsnt);
        rcvDSPhongGoiY = findViewById(R.id.rcv_list_ds_phong_goi_y);
        imageViewChuTro = findViewById(R.id.imgView_chu_tro);
        tvTenChuTro = findViewById(R.id.tv_ten_chu_tro);
        tvSDTChuTro = findViewById(R.id.tv_sdt_chu_tro);
        img_hinh_anh_rong = findViewById(R.id.img_hinh_anh_rong);
        tv_dsnt = findViewById(R.id.tv_dsnt);

        llThuGon = findViewById(R.id.ll_thu_gon);
        llXemThem = findViewById(R.id.ll_xem_them);
        ll_dsnt = findViewById(R.id.ll_dsnt);
        llChat = findViewById(R.id.ll_chat);
        llDatPhong = findViewById(R.id.ll_dat_phong);
        image_gif = findViewById(R.id.image_gif);
        llGoi = findViewById(R.id.ll_goi);
        ll_dsp_chu_tro = findViewById(R.id.ll_dsp_chu_tro);
        tvTienIchRong = findViewById(R.id.tv_tien_ich_rong);

        mViewPager2 = findViewById(R.id.view_pager_2_nguoi_thue);
        adapterHinhAnh = new HinhAnhAdapter(DetailPhongTroNguoiThueActivity.this, listHinhAnh, R.layout.chutro_item_image_layout);
        mViewPager2.setAdapter(adapterHinhAnh);

        Glide.with(this).load(R.drawable.iconp_giphy).into(image_gif);


        adapterTienIch = new TienIchAdapter(DetailPhongTroNguoiThueActivity.this, listTienIch, R.layout.cardview_item_tien_ich_layout);
        layoutManagerTienIch = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
        layoutManagerTienIch.setOrientation(RecyclerView.HORIZONTAL);
        layoutManagerTienIch = new GridLayoutManager(this, 4);
        rcvListTienIchNguoiThue.setLayoutManager(layoutManagerTienIch);
        rcvListTienIchNguoiThue.setAdapter(adapterTienIch);
        imageBack = findViewById(R.id.img_back_detail);

//        listNguoiThue2 = getListNguoiThue2();
        adapterNguoiThue = new PhucNguoiThueAdapter(DetailPhongTroNguoiThueActivity.this, listNguoiThue, R.layout.nguoithue_cardview_item_nguoi_thue_layout);
        layoutManagerNguoiThue = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
        layoutManagerNguoiThue.setOrientation(RecyclerView.VERTICAL);
        rcvListNguoiThue.setLayoutManager(layoutManagerNguoiThue);
        rcvListNguoiThue.setAdapter(adapterNguoiThue);

        adapterPhongGoiY = new PhucDanhSachPhongGoiYAdapter(DetailPhongTroNguoiThueActivity.this, listPhongGoiY, R.layout.nguoi_thue_cardview_item_phong_goi_y);
        layoutManagerPhongGoiY = new LinearLayoutManager(DetailPhongTroNguoiThueActivity.this);
        layoutManagerPhongGoiY.setOrientation(RecyclerView.HORIZONTAL);
        layoutManagerPhongGoiY = new GridLayoutManager(this, 2);
        rcvDSPhongGoiY.setLayoutManager(layoutManagerPhongGoiY);
        rcvDSPhongGoiY.setAdapter(adapterPhongGoiY);
        reviewPhong = findViewById(R.id.reviewPhong);

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