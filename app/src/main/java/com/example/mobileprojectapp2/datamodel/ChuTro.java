package com.example.mobileprojectapp2.datamodel;

public class ChuTro {
    private int id;
    private int idTaiKhoan;
    private String hinh;
    private String ten;
    private String soDienThoai;
    private int idGoi;
    private String soTaiKhoanNganHang;
    private String tenChuTaiKhoanNganHang;
    private int xacThuc;

    public ChuTro(int id, int idTaiKhoan, String hinh, String ten, String soDienThoai, int idGoi, String soTaiKhoanNganHang, String tenChuTaiKhoanNganHang, int xacThuc) {
        this.id = id;
        this.idTaiKhoan = idTaiKhoan;
        this.hinh = hinh;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
        this.idGoi = idGoi;
        this.soTaiKhoanNganHang = soTaiKhoanNganHang;
        this.tenChuTaiKhoanNganHang = tenChuTaiKhoanNganHang;
        this.xacThuc = xacThuc;
    }
    public ChuTro() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public int getIdGoi() {
        return idGoi;
    }

    public void setIdGoi(int idGoi) {
        this.idGoi = idGoi;
    }

    public String getSoTaiKhoanNganHang() {
        return soTaiKhoanNganHang;
    }

    public void setSoTaiKhoanNganHang(String soTaiKhoanNganHang) {
        this.soTaiKhoanNganHang = soTaiKhoanNganHang;
    }

    public String getTenChuTaiKhoanNganHang() {
        return tenChuTaiKhoanNganHang;
    }

    public void setTenChuTaiKhoanNganHang(String tenChuTaiKhoanNganHang) {
        this.tenChuTaiKhoanNganHang = tenChuTaiKhoanNganHang;
    }

    public int getXacThuc() {
        return xacThuc;
    }

    public void setXacThuc(int xacThuc) {
        this.xacThuc = xacThuc;
    }
}
