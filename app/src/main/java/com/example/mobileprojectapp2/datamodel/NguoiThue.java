package com.example.mobileprojectapp2.datamodel;

public class NguoiThue {
    private int Id;
    private int IdTaiKhoan;
    private String HinhNguoiDung;
    private String TenNguoiDung;
    private String SoDienThoai;
    private String GioiTinh;

    public NguoiThue(int id, int idTaiKhoan, String hinhNguoiDung, String tenNguoiDung, String soDienThoai, String gioiTinh) {
        Id = id;
        IdTaiKhoan = idTaiKhoan;
        HinhNguoiDung = hinhNguoiDung;
        TenNguoiDung = tenNguoiDung;
        SoDienThoai = soDienThoai;
        GioiTinh = gioiTinh;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdTaiKhoan() {
        return IdTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        IdTaiKhoan = idTaiKhoan;
    }

    public String getHinhNguoiDung() {
        return HinhNguoiDung;
    }

    public void setHinhNguoiDung(String hinhNguoiDung) {
        HinhNguoiDung = hinhNguoiDung;
    }

    public String getTenNguoiDung() {
        return TenNguoiDung;
    }

    public void setTenNguoiDpung(String tenNguoiDung) {
        TenNguoiDung = tenNguoiDung;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    @Override
    public String   toString() {
        return "NguoiThue{" +
                "Id=" + Id +
                ", IdTaiKhoan=" + IdTaiKhoan +
                ", HinhNguoiDung='" + HinhNguoiDung + '\'' +
                ", TenNguoiDpung='" + TenNguoiDung + '\'' +
                ", SoDienThoai='" + SoDienThoai + '\'' +
                ", GioiTinh='" + GioiTinh + '\'' +
                '}';
    }
}
