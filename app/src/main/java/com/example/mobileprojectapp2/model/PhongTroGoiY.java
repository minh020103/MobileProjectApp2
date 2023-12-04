package com.example.mobileprojectapp2.model;

public class PhongTroGoiY {
    private int idTaiKhoan;
    private int idQuan;
    private int tienCoc;
    private int gioiTinh;

    public PhongTroGoiY(int idTaiKhoan, int idQuan, int tienCoc, int gioiTinh) {
        this.idTaiKhoan = idTaiKhoan;
        this.idQuan = idQuan;
        this.tienCoc = tienCoc;
        this.gioiTinh = gioiTinh;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public int getIdQuan() {
        return idQuan;
    }

    public void setIdQuan(int idQuan) {
        this.idQuan = idQuan;
    }

    public int getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(int tienCoc) {
        this.tienCoc = tienCoc;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
