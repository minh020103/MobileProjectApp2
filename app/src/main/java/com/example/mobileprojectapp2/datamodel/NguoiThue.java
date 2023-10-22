package com.example.mobileprojectapp2.datamodel;

public class NguoiThue {
    private int id;
    private int idTaiKhoan;
    private String hinh;
    private String ten;
    private String soDienThoai;
    private int gioiTinh;

    public NguoiThue(int id, int idTaiKhoan, String hinh, String ten, String soDienThoai, int gioiTinh) {
        this.id = id;
        this.idTaiKhoan = idTaiKhoan;
        this.hinh = hinh;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
    }
    public NguoiThue() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
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

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
