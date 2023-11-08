package com.example.mobileprojectapp2.model;

public class TienIch {
    private int id;
    private String ten;
    private String hinh;
    private int trangThai;

    public TienIch(int id, String ten, String hinh, int trangThai) {
        this.id = id;
        this.ten = ten;
        this.hinh = hinh;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
