package com.example.mobileprojectapp2.datamodel;

public class Goi {
    private int id;
    private int thoiHan;
    private int soLuongPhongToiDa;
    private int gia;
    private int trangThai;

    public Goi(int id, int thoiHan, int soLuongPhongToiDa, int gia, int trangThai) {
        this.id = id;
        this.thoiHan = thoiHan;
        this.soLuongPhongToiDa = soLuongPhongToiDa;
        this.gia = gia;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(int thoiHan) {
        this.thoiHan = thoiHan;
    }

    public int getSoLuongPhongToiDa() {
        return soLuongPhongToiDa;
    }

    public void setSoLuongPhongToiDa(int soLuongPhongToiDa) {
        this.soLuongPhongToiDa = soLuongPhongToiDa;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "GoiDichVu{" +
                "id=" + id +
                ", thoiHan=" + thoiHan +
                ", soLuongPhongToiDa=" + soLuongPhongToiDa +
                ", gia=" + gia +
                ", trangThai=" + trangThai +
                '}';
    }
}
