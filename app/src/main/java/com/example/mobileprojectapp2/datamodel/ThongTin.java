package com.example.mobileprojectapp2.datamodel;

public class ThongTin {
    private String hinh;
    private String ten;

    public ThongTin(String hinh, String ten) {
        this.hinh = hinh;
        this.ten = ten;
    }
    public ThongTin() {

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
}
