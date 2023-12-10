package com.example.mobileprojectapp2.activity.chutro;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.activity.nguoithue.DetailPhongTroNguoiThueActivity;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceNghiem;
import com.example.mobileprojectapp2.datamodel.VideoReview;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRoomActivity extends AppCompatActivity {

    ImageView ic_back;
    WebView webView;
    CardView cardViewTrongMay;
    CardView cardViewYoutube;
    CardView cardViewCapNhat;
    CardView cardViewXoa;
    Intent intent;
    private int videoExist = -1;
    private int idPhong =0;
    private static final int MY_REQUEST_CODE = 10;
    private Uri mUri = Uri.parse("ChuaCo");
    private int loaiVideo= 0;
    private String linkVideoYoutube = "";
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        //Du anh lieu tu gallery
                        Uri uri = data.getData();
                        mUri = uri;
                        loaiVideo = 0;

                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_room);
        getIntentData();
        anhXa();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDuLieu();
        setSuKien();
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
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Seletect Video"));
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
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] permission = {Manifest.permission.READ_MEDIA_VIDEO};
                requestPermissions(permission, MY_REQUEST_CODE);
            }
        }
    }

    private void getIntentData(){
        intent = getIntent();
        idPhong = intent.getIntExtra("idPhong",-1);
    }
    private void setDuLieu(){

        Call<VideoReview> call = ApiServiceNghiem.apiService.getVideoReview(idPhong);
        call.enqueue(new Callback<VideoReview>() {
            @Override
            public void onResponse(Call<VideoReview> call, Response<VideoReview> response) {
                if(response.body()!=null){
                    ReviewRoomActivity.this.videoExist = 1;
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
                    }
                }else{

                }
            }
            @Override
            public void onFailure(Call<VideoReview> call, Throwable t) {
                thongBao("Lỗi Dữ Liệu");
            }
        });
    }

    private void setSuKien(){
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cardViewTrongMay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        cardViewYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaiVideo=1;
                openDialogLinkYoutube();
            }
        });
        cardViewCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loaiVideo==0){
                    if(!mUri.getPath().equals("ChuaCo")){
                        String strRealPath = RealPathUtil.getRealPath(getApplicationContext(), mUri);
                        File file = new File(strRealPath);
                        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part mulPart = MultipartBody.Part.createFormData("file", file.getName(), requestBodyImage);
                        int file_size = Integer.parseInt(String.valueOf(file.length()/1024));
                        if(file_size<=2000){
                            Call<Integer> call = ApiServiceNghiem.apiService.uploadVideoReview(idPhong,loaiVideo,mulPart);
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if(response.body()!=null)
                                    {
                                        if (response.body()==1){
                                            ReviewRoomActivity.this.videoExist =1;
                                            thongBao("Cập Nhật Thành Công!");
                                        }else{
                                            thongBao("Cập Nhật Thất Bại!");
                                        }
                                    }else{
                                        thongBao("Cập Nhật Thất Bại!");
                                    }

                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    thongBao("Có Lỗi Xảy Ra");
                                }
                            });
                        }else{
                            thongBao("File Tối Đa 2MB");
                        }


                    }else{
                        thongBao("Chưa Có Dữ Liệu Để Cập Nhật!");
                    }
                }else{
//                    Youtube
                   if(!linkVideoYoutube.trim().isEmpty()){
                       RequestBody linkVideo = RequestBody.create(MediaType.parse("multipart/form"),linkVideoYoutube);
                       Call<Integer> call = ApiServiceNghiem.apiService.uploadVideoReviewYoutube(idPhong,loaiVideo,linkVideo);
                       call.enqueue(new Callback<Integer>() {
                           @Override
                           public void onResponse(Call<Integer> call, Response<Integer> response) {
                               if(response.body()!=null){
                                   if(response.body()==1){
                                       thongBao("Cập Nhật Thành Công!");
                                   }else{
                                       thongBao("Cập Nhật Thất Bại!");
                                   }
                               }else{
                                   thongBao("Cập Nhật Thất Bại!");
                               }
                           }

                           @Override
                           public void onFailure(Call<Integer> call, Throwable t) {

                           }
                       });
                   }else{
                       thongBao("Chưa Có Dữ Liệu Để Cập Nhật");
                   }
                }
            }
        });
        cardViewXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoExist==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewRoomActivity.this);
                    builder.setMessage("Bạn Chắc Chắn Xóa Video Này?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Call<Integer> call = ApiServiceNghiem.apiService.deleteVideoReviewYoutube(idPhong);
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if(response.body()!=null){
                                        if(response.body()==1){
                                            ReviewRoomActivity.this.videoExist = -1;
                                            thongBao("Xóa Video Thành Công!");
                                        }else{
                                            thongBao("Xóa Thất Bại!");
                                        }
                                    }else{
                                        thongBao("Xóa Thất Bại!");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {

                                }
                            });
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create();
                    builder.show();
                }
                else {
                    thongBao("Chưa Có Video Để Xóa!");
                }
            }
        });
    }


    private void openDialogLinkYoutube() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = ReviewRoomActivity.this.getLayoutInflater().inflate(R.layout.huongdanyoutube,null);
        dialog.setView(view);
        AlertDialog dialogB = dialog.create();
        dialogB.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView dong = view.findViewById(R.id.dongDialog);
        Button btnChapNhanLink = view.findViewById(R.id.buttonCN);
        EditText editText = view.findViewById(R.id.edtLinkVideo);
        btnChapNhanLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().trim().isEmpty()){
                    linkVideoYoutube = editText.getText().toString();
                    dialogB.hide();
                    Toast.makeText(getApplicationContext(),"Hãy Ấn Cập Nhật!",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Chưa Có Link!",Toast.LENGTH_SHORT).show();
                }
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

    private void anhXa(){
        ic_back = findViewById(R.id.ic_back);
        webView = findViewById(R.id.webView);
        cardViewTrongMay = findViewById(R.id.cardViewTrongMay);
        cardViewYoutube = findViewById(R.id.cardViewYoutube);
        cardViewCapNhat = findViewById(R.id.cardViewCapNhat);
        cardViewXoa = findViewById(R.id.cardViewXoa);
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

}