package com.example.mobileprojectapp2.datamodel;

public class ThongBao {
    private int id;
    private TaiKhoan TaiKhoanNguoiGui;
    private TaiKhoan TaiKhoanNguoiNhan;
    private String noiDung;
    private int trangThaiTaiKhoanNguoiNhan;

    public ThongBao(int id, TaiKhoan taiKhoanNguoiGui, TaiKhoan TaiKhoanNguoiNhan, String noiDung, int trangThaiTaiKhoanNguoiNhan) {
        this.id = id;
        TaiKhoanNguoiGui = taiKhoanNguoiGui;
        this.TaiKhoanNguoiNhan = TaiKhoanNguoiNhan;
        this.noiDung = noiDung;
        this.trangThaiTaiKhoanNguoiNhan = trangThaiTaiKhoanNguoiNhan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaiKhoan getTaiKhoanNguoiGui() {
        return TaiKhoanNguoiGui;
    }

    public void setTaiKhoanNguoiGui(TaiKhoan taiKhoanNguoiGui) {
        TaiKhoanNguoiGui = taiKhoanNguoiGui;
    }

    public TaiKhoan getTaiKhoanNguoiNhan() {
        return TaiKhoanNguoiNhan;
    }

    public void setTaiKhoanNguoiNhan(TaiKhoan idTaiKhoanNguoiNhan) {
        this.TaiKhoanNguoiNhan = idTaiKhoanNguoiNhan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getTrangThaiTaiKhoanNguoiNhan() {
        return trangThaiTaiKhoanNguoiNhan;
    }

    public void setTrangThaiTaiKhoanNguoiNhan(int trangThaiTaiKhoanNguoiNhan) {
        this.trangThaiTaiKhoanNguoiNhan = trangThaiTaiKhoanNguoiNhan;
    }

    @Override
    public String toString() {
        return "ThongBao{" +
                "id=" + id +
                ", TaiKhoanNguoiGui=" + TaiKhoanNguoiGui +
                ", idTaiKhoanNguoiNhan=" + TaiKhoanNguoiNhan +
                ", noiDung='" + noiDung + '\'' +
                ", trangThaiTaiKhoanNguoiNhan=" + trangThaiTaiKhoanNguoiNhan +
                '}';
    }
}
