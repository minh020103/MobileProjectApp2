package com.example.mobileprojectapp2.datamodel;

public class PhongNguoiThue {
    private int id;
    private int idNguoiThue;
    private int idPhong;
    private NguoiThue nguoiThue;

    public PhongNguoiThue(int id, int idNguoiThue, int idPhong, NguoiThue nguoiThue) {
        this.id = id;
        this.idNguoiThue = idNguoiThue;
        this.idPhong = idPhong;
        this.nguoiThue = nguoiThue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNguoiThue() {
        return idNguoiThue;
    }

    public void setIdNguoiThue(int idNguoiThue) {
        this.idNguoiThue = idNguoiThue;
    }

    public int getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(int idPhong) {
        this.idPhong = idPhong;
    }

    public NguoiThue getNguoiThue() {
        return nguoiThue;
    }

    public void setNguoiThue(NguoiThue nguoiThue) {
        this.nguoiThue = nguoiThue;
    }

    @Override
    public String toString() {
        return "PhongNguoiThue{" +
                "id=" + id +
                ", idNguoiThue=" + idNguoiThue +
                ", idPhong=" + idPhong +
                ", nguoiThue=" + nguoiThue +
                '}';
    }
}
