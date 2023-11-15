package com.example.mobileprojectapp2.datamodel;

import java.io.Serializable;

public class HinhAnh implements Serializable {
    private int id;
    private int idPhong;
    private String hinh;

    public HinhAnh(int id, int idPhong, String hinh) {
        this.id = id;
        this.idPhong = idPhong;
        this.hinh = hinh;
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

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
