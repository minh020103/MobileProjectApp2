package com.example.mobileprojectapp2.datamodel;

public class ChinhSach {
    private int id;
    private String noiDungChinhSach;

    public ChinhSach(int id, String noiDungChinhSach) {
        this.id = id;
        this.noiDungChinhSach = noiDungChinhSach;
    }
    public ChinhSach() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoiDungChinhSach() {
        return noiDungChinhSach;
    }

    public void setNoiDungChinhSach(String noiDungChinhSach) {
        this.noiDungChinhSach = noiDungChinhSach;
    }
}
