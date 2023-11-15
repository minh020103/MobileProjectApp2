package com.example.mobileprojectapp2.model;

public class TienIch {
    private int id;
    private String ten;
    private String hinh;
    private int trangThai;

    private PhongTroTienIch pivot;

    public TienIch(int id, String ten, String hinh, int trangThai, PhongTroTienIch pivot) {
        this.id = id;
        this.ten = ten;
        this.hinh = hinh;
        this.trangThai = trangThai;
        this.pivot = pivot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public PhongTroTienIch getPivot() {
        return pivot;
    }

    public void setPivot(PhongTroTienIch pivot) {
        this.pivot = pivot;
    }

    @Override
    public String toString() {
        return "TienIch{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", hinh='" + hinh + '\'' +
                ", trangThai=" + trangThai +
                ", pivot=" + pivot +
                '}';
    }
}
