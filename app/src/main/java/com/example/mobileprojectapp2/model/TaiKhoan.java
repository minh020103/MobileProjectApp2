package com.example.mobileproject.datamodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaiKhoan{
    private int id;
    private String tenTaiKhoan;
    private String matKhau;
    private int trangThai;
    private int loaiTaiKhoan;
    private String email;

    public TaiKhoan(String tenTaiKhoan, String matKhau, int trangThai, int loaiTaiKhoan, String email) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.email = email;
    }
    public TaiKhoan() {

    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(int loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
