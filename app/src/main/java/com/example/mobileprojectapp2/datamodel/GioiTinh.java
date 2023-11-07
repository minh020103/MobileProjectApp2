package com.example.mobileprojectapp2.datamodel;

public class GioiTinh {
    private int id;
    private String gioiTinh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public GioiTinh(int id, String gioiTinh) {
        this.id = id;
        this.gioiTinh = gioiTinh;
    }
}
