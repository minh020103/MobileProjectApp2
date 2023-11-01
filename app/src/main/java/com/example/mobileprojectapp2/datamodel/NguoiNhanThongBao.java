package com.example.mobileprojectapp2.datamodel;

public class NguoiNhanThongBao {
    private int id;
    private int loaiTaiKhoan;

    public NguoiNhanThongBao(int id, int loaiTaiKhoan) {
        this.id = id;
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(int loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    @Override
    public String toString() {
        return "NguoiGuiThongBao{" +
                "id=" + id +
                ", loaiTaiKhoan=" + loaiTaiKhoan +
                '}';
    }
}
