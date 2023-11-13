package com.example.mobileprojectapp2.datamodel;

import java.sql.Timestamp;

public class PhongBinhLuan {
    private int id;
    private int idPhong;
    private int idTaiKhoan;
    private String noiDungBinhLuan;
    private String loaiTaiKhoan;
    private Timestamp created_at;
    private NguoiGui nguoiGui;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(int idPhong) {
        this.idPhong = idPhong;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getNoiDungBinhLuan() {
        return noiDungBinhLuan;
    }

    public void setNoiDungBinhLuan(String noiDungBinhLuan) {
        this.noiDungBinhLuan = noiDungBinhLuan;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public NguoiGui getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(NguoiGui nguoiGui) {
        this.nguoiGui = nguoiGui;
    }
}
