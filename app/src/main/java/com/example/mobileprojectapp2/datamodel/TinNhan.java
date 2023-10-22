package com.example.mobileprojectapp2.datamodel;

import java.sql.Time;
import java.sql.Timestamp;

public class TinNhan {
    private int id;
    private int idPhong;
    private int idTaiKhoan;
    private String noiDung;
    private Timestamp created_at;

    public TinNhan(int id, int idPhong, int idTaiKhoan, String noiDung, Timestamp created_at) {
        this.id = id;
        this.idPhong = idPhong;
        this.idTaiKhoan = idTaiKhoan;
        this.noiDung = noiDung;
        this.created_at = created_at;
    }
    public TinNhan() {

    }

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

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
