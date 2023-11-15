package com.example.mobileprojectapp2.model;

public class PhongTroTienIch {
    private int idPhong;
    private int idTienIch;

    public PhongTroTienIch(int idPhong, int idTienIch) {
        this.idPhong = idPhong;
        this.idTienIch = idTienIch;
    }

    public int getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(int idPhong) {
        this.idPhong = idPhong;
    }

    public int getIdTienIch() {
        return idTienIch;
    }

    public void setIdTienIch(int idTienIch) {
        this.idTienIch = idTienIch;
    }
}
