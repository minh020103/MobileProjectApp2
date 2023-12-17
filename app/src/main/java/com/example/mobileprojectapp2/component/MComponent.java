package com.example.mobileprojectapp2.component;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectapp2.R;
import com.example.mobileprojectapp2.api.chutro.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.FirebaseCloudMessaging;
import com.example.mobileprojectapp2.datamodel.PhongBinhLuan;
import com.example.mobileprojectapp2.datamodel.PhongDanhGia;
import com.example.mobileprojectapp2.player.RatingPlayer;
import com.example.mobileprojectapp2.recyclerviewadapter.chutro.CommentAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MComponent {
    private static CommentAdapter commentAdapter;
    private static RecyclerView rcvComment;
    // Bình luận (activity, container, idPhong, listComment, idTaiKhoanNguoiBinhLuan)
    // activity là nếu ở trong fragment ghi getActivity, nếu ở activity thì ClassName.this
    // idPhong là id phòng của phòng mình nhấn vào
    // listComment cần phải khởi tạo linkedlist trước khi chuyền vào trong này
    // idTaiKhoanNguoiBinhLuan là idTaiKhoan mà mình đang đăng nhập
    public static void comment(Activity activity, ViewGroup container, int idPhong, LinkedList<PhongBinhLuan> listComment, int idTaiKhoanNguoiBinhLuan){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        View viewBottomSheetCommnent = activity.getLayoutInflater().inflate(R.layout.chutro_buttom_sheet_comment_layout, container, false);
        BottomSheetDialog bottomSheetComment = new BottomSheetDialog(activity, R.style.BottomSheetDialogTheme);
        bottomSheetComment.setContentView(viewBottomSheetCommnent);

        databaseReference.child("comment").child(idPhong+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getCommentFromAPI(idPhong, listComment);
                Log.d("TAG", "onDataChange: GET OK");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Ánh xạ
        ImageView imgSend = viewBottomSheetCommnent.findViewById(R.id.imgSend);
        EditText edtComment = viewBottomSheetCommnent.findViewById(R.id.edtComment);
        // set
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtComment.onEditorAction(EditorInfo.IME_ACTION_DONE);
                if (edtComment.length() > 0 && edtComment.length() <= 255) {
                    ApiServiceMinh.apiService.themBinhLuanChoPhong(idPhong, idTaiKhoanNguoiBinhLuan, edtComment.getText().toString().trim()).enqueue(new Callback<PhongBinhLuan>() {
                        @Override
                        public void onResponse(Call<PhongBinhLuan> call, Response<PhongBinhLuan> response) {
                            if (response.code() == 201) {
                                if (response.body()!=null) {
                                    edtComment.setText("");
                                    listComment.addFirst(response.body());
                                    commentAdapter.notifyDataSetChanged();
                                    databaseReference.child("comment").child(idPhong + "").child(response.body().getId() + "").setValue(response.body().getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("TAG", "onDataChange: NEW OK");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PhongBinhLuan> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(activity, "Bình luận tối đa 255 ký tự và phải nhập dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //recyclerview
        rcvComment = viewBottomSheetCommnent.findViewById(R.id.rcvComment);
        commentAdapter = new CommentAdapter(activity, listComment, R.layout.chutro_cardview_comment_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(commentAdapter);


        bottomSheetComment.show();

    }
    private static void getCommentFromAPI(int idPhong, LinkedList<PhongBinhLuan> listComment) {
        listComment.clear();
        Log.d("TAG", "getCommentFromAPI: " + idPhong);
        ApiServiceMinh.apiService.layTatCaBinhLuanCuaPhong(idPhong).enqueue(new Callback<List<PhongBinhLuan>>() {
            @Override
            public void onResponse(Call<List<PhongBinhLuan>> call, Response<List<PhongBinhLuan>> response) {
                if (response.code() == 200) {
                    if (response.body()!=null) {
                        listComment.clear();
                        listComment.addAll(response.body());
                        commentAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PhongBinhLuan>> call, Throwable t) {

            }
        });
    }

    // Đánh giá (activity, idPhong, idTaiKhoan)
    // activity là nếu ở trong fragment ghi getActivity, nếu ở activity thì ClassName.this
    // idPhong là id phòng của phòng mình nhấn vào
    // idTaiKhoan là idTaiKhoan mà mình đang đăng nhập
    public static void rating(Activity activity, int idPhong, int idTaiKhoan){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.chutro_dialog_rating_layout, null);
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();


        //builder.show();
        dialog.show();
        ImageView imgClose = viewDialog.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close
                dialog.hide();
            }
        });
        RatingPlayer ratingPlayer = new RatingPlayer(viewDialog);
        // đổi sao ra giao diện
        ratingPlayer.setStarForRating(0);
        ApiServiceMinh.apiService.layDanhGiaCuaNguoiDunngChoPhong(idTaiKhoan, idPhong).enqueue(new Callback<PhongDanhGia>() {
            @Override
            public void onResponse(Call<PhongDanhGia> call, Response<PhongDanhGia> response) {
                if (response.code() == 200) {
                    if (response.body()!=null) {
                        ratingPlayer.setStarForRating(response.body().getDanhGia());
                    }
                }
            }

            @Override
            public void onFailure(Call<PhongDanhGia> call, Throwable t) {

            }
        });

        // chọn sao
        ratingPlayer.rating();
        //  hàm sử lý số sao đã chọn được ủy quyền
        ratingPlayer.setiRatingPlayer(new RatingPlayer.IRatingPlayer() {
            @Override
            public void layDanhGia(int rating) {
                // được ủy quyền ra ngoài để lấy lượng đánh giá
                Log.d("TAG", "setOnClickRating: " + rating);
                ApiServiceMinh.apiService.danhGiaChoPhong(idTaiKhoan, idPhong, rating).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.code() == 200) {
                            if (response.body()!=null) {
                                Toast.makeText(activity, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                                dialog.hide();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        });
    }


    //Hàm cắt bỏ phần đuôi của chuỗi (str, soLuongKyTu, kyTuThayThe)
    //str là chuỗi bỏ vào
    //soLuongKyTu là chuỗi chỉ lấy từ vị trí thứ 0 -> soLuongKyTu VD str="0123456" soLuongKyTu=5 thì str còn "012345"
    //kyTuThayThe là phần bị mất đi được thay thế bằng kyTuThayThe VD kyTuThayThe="..." thì str ở trên sẽ thành "012345..."
    public static String boChuoiPhiaSau(String str, int soLuongKyTu, String kyTuThayThe){
        String res = str.length() >= soLuongKyTu+1?str.substring(0, soLuongKyTu)+kyTuThayThe:str;
        return res;
    }
    //Lưu token ứng dụng của thiết bị (idAccount)
    //idAccount là tài khoản đã đăng nhập vào ứng dụng
    public static void saveTokenAppDevice(int idAccount){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh.apiService.saveTokenDeviceOfAccount(token, idAccount).enqueue(new Callback<FirebaseCloudMessaging>() {
                            @Override
                            public void onResponse(Call<FirebaseCloudMessaging> call, Response<FirebaseCloudMessaging> response) {
                                if (response.code() == 200) {
                                    if (response.body()!=null) {
                                        Log.d("TAG", "onResponse: SAVE TOKEN COMPLATED");
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<FirebaseCloudMessaging> call, Throwable t) {

                            }
                        });
                    }
                });
    }
    //Xóa token
    public static void deleteTokenDivice(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh.apiService.deleteTokenDeviceOfAccount(token).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.code() == 200) {
                                    if (response.body()!=null) {
                                        Log.d("TAG", "onResponse: DELETE TOKEN COMPLATED");
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                    }
                });

    }
}
