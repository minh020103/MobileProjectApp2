package com.example.mobileprojectapp2.datamodel;

import java.util.List;

public class PhongTroChuTro {
    private int id;
    private int idChuTro;
    private int ten;

    public int getTen() {
        return ten;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }

    private int idPhongTro;
    private ChuTro chuTro;
    private PhongTro phongTro;
    private List<HinhAnh> hinhAnh;
    private int binhLuan;
    private double danhGia;
    private Quan quan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdChuTro() {
        return idChuTro;
    }

    public void setIdChuTro(int idChuTro) {
        this.idChuTro = idChuTro;
    }

    public int getIdPhongTro() {
        return idPhongTro;
    }

    public void setIdPhongTro(int idPhongTro) {
        this.idPhongTro = idPhongTro;
    }

    public ChuTro getChuTro() {
        return chuTro;
    }

    public void setChuTro(ChuTro chuTro) {
        this.chuTro = chuTro;
    }

    public PhongTro getPhongTro() {
        return phongTro;
    }

    public void setPhongTro(PhongTro phongTro) {
        this.phongTro = phongTro;
    }

    public List<HinhAnh> getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(List<HinhAnh> hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getBinhLuan() {
        return binhLuan;
    }

    public void setBinhLuan(int binhLuan) {
        this.binhLuan = binhLuan;
    }

    public double getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(double danhGia) {
        this.danhGia = danhGia;
    }

    public Quan getQuan() {
        return quan;
    }

    public void setQuan(Quan quan) {
        this.quan = quan;
    }

    @Override
    public String toString() {
        return "PhongTroChuTro{" +
                "id=" + id +
                ", idChuTro=" + idChuTro +
                ", ten=" + ten +
                ", idPhongTro=" + idPhongTro +
                ", chuTro=" + chuTro +
                ", phongTro=" + phongTro +
                ", hinhAnh=" + hinhAnh +
                ", binhLuan=" + binhLuan +
                ", danhGia=" + danhGia +
                ", quan=" + quan +
                '}';
    }
}
