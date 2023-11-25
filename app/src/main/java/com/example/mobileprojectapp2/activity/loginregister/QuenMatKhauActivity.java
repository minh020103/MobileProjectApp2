package com.example.mobileprojectapp2.activity.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.mobileprojectapp2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMatKhauActivity extends AppCompatActivity {

    AppCompatImageView ic_back;
    EditText email;
    Button btnXacNhan;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        anhXa();
        setSuKien();
    }
    private void setSuKien(){
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                batTatProgessBar(0);
                suKien();
            }
        });
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
    private void suKien(){
        if(!email.getText().toString().isEmpty()){
            firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    batTatProgessBar(1);
                    email.setText("");
                    thongBao("Hãy Kiểm Tra Email Của Bạn!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    batTatProgessBar(1);
                    thongBao("Hệ Thống Không Tìm Thấy Email Của Bạn!");
                }
            });
        }else{
            batTatProgessBar(1);
            thongBao("Email Rỗng!");
        }
    }
    private void batTatProgessBar(int kt){
        if(kt ==0 ){
            progressBar.setVisibility(View.VISIBLE);
        }else if(kt==1){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void anhXa(){
        email = findViewById(R.id.email);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        ic_back = findViewById(R.id.ic_back);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progessbar);
    }
}