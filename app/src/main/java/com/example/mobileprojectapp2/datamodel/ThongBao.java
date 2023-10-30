package com.example.mobileprojectapp2.datamodel;

public class ThongBao {
    private int Id;
    private int idTaiKhoanGui;
    private int idTaiKhoanNhan;
    private String noiDung;
    private int trangThai;

    public ThongBao(int id, int idTaiKhoanGui, int idTaiKhoanNhan, String noiDung, int trangThai) {
        Id = id;
        this.idTaiKhoanGui = idTaiKhoanGui;
        this.idTaiKhoanNhan = idTaiKhoanNhan;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdTaiKhoanGui() {
        return idTaiKhoanGui;
    }

    public void setIdTaiKhoanGui(int idTaiKhoanGui) {
        this.idTaiKhoanGui = idTaiKhoanGui;
    }

    public int getIdTaiKhoanNhan() {
        return idTaiKhoanNhan;
    }

    public void setIdTaiKhoanNhan(int idTaiKhoanNhan) {
        this.idTaiKhoanNhan = idTaiKhoanNhan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "ThongBao{" +
                "Id=" + Id +
                ", idTaiKhoanGui=" + idTaiKhoanGui +
                ", idTaiKhoanNhan=" + idTaiKhoanNhan +
                ", noiDung='" + noiDung + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
