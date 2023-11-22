package com.example.mobileprojectapp2.datamodels;

import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.HinhAnh;
import com.example.mobileprojectapp2.datamodel.Quan;

import java.sql.Timestamp;
import java.util.List;

public class PhongTro {
    private int id;
    private int soPhong;
    private int gia;
    private int dienTich;
    private String moTa;
    private int idQuan;
    private int idPhuong;
    private String diaChiChiTiet;
    private int loaiPhong;
    private int soLuongToiDa;
    private int tienCoc;
    private int gioiTinh;
    private Timestamp created_at;
    private int tienDien;
    private int tienNuoc;
    private Quan quan;
    private List<HinhAnh> hinhAnhPhongTro;
    private ChuTro chuTro;

    public ChuTro getChuTro() {
        return chuTro;
    }

    public void setChuTro(ChuTro chuTro) {
        this.chuTro = chuTro;
    }

    public List<HinhAnh> getHinhAnhPhongTro() {
        return hinhAnhPhongTro;
    }

    public void setHinhAnhPhongTro(List<HinhAnh> hinhAnhPhongTro) {
        this.hinhAnhPhongTro = hinhAnhPhongTro;
    }

    public Quan getQuan() {
        return quan;
    }

    public void setQuan(Quan quan) {
        this.quan = quan;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getDienTich() {
        return dienTich;
    }

    public void setDienTich(int dienTich) {
        this.dienTich = dienTich;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdQuan() {
        return idQuan;
    }

    public void setIdQuan(int idQuan) {
        this.idQuan = idQuan;
    }

    public int getIdPhuong() {
        return idPhuong;
    }

    public void setIdPhuong(int idPhuong) {
        this.idPhuong = idPhuong;
    }

    public String getDiaChiChiTiet() {
        return diaChiChiTiet;
    }

    public void setDiaChiChiTiet(String diaChiChiTiet) {
        this.diaChiChiTiet = diaChiChiTiet;
    }

    public int getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(int loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public int getSoLuongToiDa() {
        return soLuongToiDa;
    }

    public void setSoLuongToiDa(int soLuongToiDa) {
        this.soLuongToiDa = soLuongToiDa;
    }

    public int getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(int tienCoc) {
        this.tienCoc = tienCoc;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getTienDien() {
        return tienDien;
    }

    public void setTienDien(int tienDien) {
        this.tienDien = tienDien;
    }

    public int getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(int tienNuoc) {
        this.tienNuoc = tienNuoc;
    }
}
