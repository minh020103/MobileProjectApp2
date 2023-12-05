package com.example.mobileprojectapp2.datamodel.fcm;

import com.example.mobileprojectapp2.datamodel.ThongBao;

import java.util.List;

public class FCMThongBaoDatPhong {
    private int result;
    private ThongBao thongBaoThanhCong;
    private List<ThongBao> thongBaoThatBai;
    private int loai;
    private String string;

    public FCMThongBaoDatPhong(int result, ThongBao thongBaoThanhCong, List<ThongBao> thongBaoThatBai, int loai, String string) {
        this.result = result;
        this.thongBaoThanhCong = thongBaoThanhCong;
        this.thongBaoThatBai = thongBaoThatBai;
        this.loai = loai;
        this.string = string;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ThongBao getThongBaoThanhCong() {
        return thongBaoThanhCong;
    }

    public void setThongBaoThanhCong(ThongBao thongBaoThanhCong) {
        this.thongBaoThanhCong = thongBaoThanhCong;
    }

    public List<ThongBao> getThongBaoThatBai() {
        return thongBaoThatBai;
    }

    public void setThongBaoThatBai(List<ThongBao> thongBaoThatBai) {
        this.thongBaoThatBai = thongBaoThatBai;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "FCMThongBaoDatPhong{" +
                "result=" + result +
                ", thongBaoThanhCong=" + thongBaoThanhCong +
                ", thongBaoThatBai=" + thongBaoThatBai +
                ", loai=" + loai +
                ", string='" + string + '\'' +
                '}';
    }
}
