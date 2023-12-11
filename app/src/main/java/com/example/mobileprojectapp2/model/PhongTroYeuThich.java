package com.example.mobileprojectapp2.model;

public class PhongTroYeuThich {
    private int id;
    private PhongTro idPhong;
    private int idTaiKhoan;

    public PhongTroYeuThich(int id, PhongTro idPhong, int idTaiKhoan) {
        this.id = id;
        this.idPhong = idPhong;
        this.idTaiKhoan = idTaiKhoan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PhongTro getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(PhongTro idPhong) {
        this.idPhong = idPhong;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }
}
