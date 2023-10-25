package com.example.mobileproject.datamodel;

import com.google.gson.annotations.SerializedName;

public class Admin {
    @SerializedName("idTaiKhoan")
    private int idTaiKhoan;
    @SerializedName("ten")
    private String ten;
    @SerializedName("hinh")
    private String hinh;
    @SerializedName("soDienThoai")
    private String soDienThoai;
    @SerializedName("soTaiKhoanNganHang")
    private String soTaiKhoanNganHang;
    @SerializedName("tenChuTaiKhoan")
    private String tenChuTaiKhoan;

    public String getHinh() {
        return hinh;
    }

    public Admin(int idTaiKhoan, String ten, String hinh, String soDienThoai, String soTaiKhoanNganHang, String tenChuTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
        this.ten = ten;
        this.hinh = hinh;
        this.soDienThoai = soDienThoai;
        this.soTaiKhoanNganHang = soTaiKhoanNganHang;
        this.tenChuTaiKhoan = tenChuTaiKhoan;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Admin() {

    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getSoTaiKhoanNganHang() {
        return soTaiKhoanNganHang;
    }

    public void setSoTaiKhoanNganHang(String soTaiKhoanNganHang) {
        this.soTaiKhoanNganHang = soTaiKhoanNganHang;
    }

    public String getTenChuTaiKhoan() {
        return tenChuTaiKhoan;
    }

    public void setTenChuTaiKhoan(String tenChuTaiKhoan) {
        this.tenChuTaiKhoan = tenChuTaiKhoan;
    }
}
