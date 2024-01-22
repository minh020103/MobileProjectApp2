package com.example.mobileprojectapp2.component;

import android.util.Log;

import com.example.mobileprojectapp2.api.ApiFCMService;
import com.example.mobileprojectapp2.api.Const;
import com.example.mobileprojectapp2.api.nguoithue.ApiServiceMinh;
import com.example.mobileprojectapp2.datamodel.FirebaseCloudMessaging;
import com.example.mobileprojectapp2.datamodel.ResultFCM;
import com.example.mobileprojectapp2.datamodel.TaiKhoan;
import com.example.mobileprojectapp2.datamodel.fcm.Notification;
import com.example.mobileprojectapp2.datamodel.fcm.PushNotification;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MFCM {
    // Gửi thông báo đến tất cả thiết bị mà tài khoản đã đăng nhập (idTaiKhoan, idNotification, title, content)
    // idTaiKhoan là id của tài khoản mà sẽ nhận thông báo
    // Thêm 1 cái thông báo hoặc yêu cầu xác nhận vào trong database thì nó sẽ trả về 1 đối tượng về response
    // idNotification là cái id mà cái response trả về
    // title là cái mà mọi người nhập vào để hiện tiêu đề lên giao diện thông báo
    // content là nội dụng thông báo mà mọi  người nhập vào để hiện lên giao diện thông báo
    public static void sendNotificationForAccountID(int idTaiKhoan ,int idNotification, String title, String content){
        ApiServiceMinh.apiService.layTatCaTokenCuaTaiKhoan(idTaiKhoan).enqueue(new Callback<List<FirebaseCloudMessaging>>() {
            @Override
            public void onResponse(Call<List<FirebaseCloudMessaging>> call, Response<List<FirebaseCloudMessaging>> responseToken) {
                if (responseToken.code() == 200){
                    if (responseToken.body()!=null) {
                        for (FirebaseCloudMessaging firebaseCloudMessaging :
                                responseToken.body()) {
                            Log.d("TAG", "onResponse: " + firebaseCloudMessaging.getToken());
                            ApiFCMService.apiService.postNotification(new PushNotification(
                                    new Notification(idNotification, title, content),
                                    firebaseCloudMessaging.getToken())
                            ).enqueue(new Callback<ResultFCM>() {
                                @Override
                                public void onResponse(Call<ResultFCM> call, Response<ResultFCM> response) {
                                    Log.d("TAG", "onResponse: idNoti: " + idNotification);
                                    Log.d("TAG", "onResponse: title: " + title);
                                    Log.d("TAG", "onResponse: content: " + content);
                                    Log.d("TAG", "onResponse: idTK: " + idTaiKhoan);
                                    Log.d("TAG", "onResponse: token: " + firebaseCloudMessaging.getToken());

                                }

                                @Override
                                public void onFailure(Call<ResultFCM> call, Throwable t) {
                                    Log.d("TAG", "onFailure: " + t);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FirebaseCloudMessaging>> call, Throwable t) {

            }
        });
    }

}
