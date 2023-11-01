package com.example.mobileprojectapp2.datamodel;

public class NguoiNhanThongBao {
    private String hinh;
    private String ten;
    private String soDienThoai;

    public NguoiNhanThongBao(String hinh, String ten, String soDienThoai) {
        this.hinh = hinh;
        this.ten = ten;
        this.soDienThoai = soDienThoai;
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

    @Override
    public String toString() {
        return "NguoiNhanThongBao{" +
                "hinh='" + hinh + '\'' +
                ", ten='" + ten + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                '}';
    }
}
