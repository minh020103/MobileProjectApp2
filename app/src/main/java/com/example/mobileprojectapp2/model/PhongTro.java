package com.example.mobileprojectapp2.model;

public class PhongTro {
    private int id;
    private int soPhong;
    private String tenPhong;
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

    public PhongTro(int id, int soPhong, String tenPhong, int gia, int dienTich, String moTa, int idQuan,
                    int idPhuong, String diaChiChiTiet, int loaiPhong, int soLuongToiDa, int tienCoc, int gioiTinh) {
        this.id = id;
        this.soPhong = soPhong;
        this.tenPhong = tenPhong;
        this.gia = gia;
        this.dienTich = dienTich;
        this.moTa = moTa;
        this.idQuan = idQuan;
        this.idPhuong = idPhuong;
        this.diaChiChiTiet = diaChiChiTiet;
        this.loaiPhong = loaiPhong;
        this.soLuongToiDa = soLuongToiDa;
        this.tienCoc = tienCoc;
        this.gioiTinh = gioiTinh;
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

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
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

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}
