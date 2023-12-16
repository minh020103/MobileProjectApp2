package com.example.mobileprojectapp2.model;

public class LoaiPhong {
    private int id;
    private int loaiPhong;

    public LoaiPhong(int id, int loaiPhong) {
        this.id = id;
        this.loaiPhong = loaiPhong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(int loaiPhong) {
        this.loaiPhong = loaiPhong;
    }
}
