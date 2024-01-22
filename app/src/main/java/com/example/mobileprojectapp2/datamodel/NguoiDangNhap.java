package com.example.mobileprojectapp2.datamodel;

public class NguoiDangNhap {
    private int id;
    private int xacThuc;
    private String ten;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public NguoiDangNhap() {
    }

    public NguoiDangNhap(int id, int xacThuc) {
        this.id = id;
        this.xacThuc = xacThuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXacThuc() {
        return xacThuc;
    }

    public void setXacThuc(int xacThuc) {
        this.xacThuc = xacThuc;
    }
}
