package com.example.mobileprojectapp2.datamodel;

public class FirebaseCloudMessaging {
    private int id;
    private String token;
    private int idTaiKhoan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public FirebaseCloudMessaging(int id, String token, int idTaiKhoan) {
        this.id = id;
        this.token = token;
        this.idTaiKhoan = idTaiKhoan;
    }
}
