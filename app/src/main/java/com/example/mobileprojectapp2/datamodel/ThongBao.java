package com.example.mobileprojectapp2.datamodel;

public class ThongBao {
    private int id;
    private int idTaiKhoanGui;
    private int idTaiKhoanNhan;
    private String tieuDe;
    private String noiDung;
    private int trangThai;
    private int trangThaiNhan;
    private NguoiGuiThongBao nguoiGui;
    private NguoiNhanThongBao taiKhoanNhan;

    public ThongBao(int id, int idTaiKhoanGui, int idTaiKhoanNhan, String tieuDe, String noiDung, int trangThai, NguoiGuiThongBao nguoiGui, NguoiNhanThongBao taiKhoanNhan) {
        this.id = id;
        this.idTaiKhoanGui = idTaiKhoanGui;
        this.idTaiKhoanNhan = idTaiKhoanNhan;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
        this.nguoiGui = nguoiGui;
        this.taiKhoanNhan = taiKhoanNhan;
    }

    public int getTrangThaiNhan() {
        return trangThaiNhan;
    }

    public void setTrangThaiNhan(int trangThaiNhan) {
        this.trangThaiNhan = trangThaiNhan;
    }

    public ThongBao() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
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

    public NguoiGuiThongBao getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(NguoiGuiThongBao nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public NguoiNhanThongBao getTaiKhoanNhan() {
        return taiKhoanNhan;
    }

    public void setTaiKhoanNhan(NguoiNhanThongBao taiKhoanNhan) {
        this.taiKhoanNhan = taiKhoanNhan;
    }

    @Override
    public String toString() {
        return "ThongBao{" +
                "id=" + id +
                ", idTaiKhoanGui=" + idTaiKhoanGui +
                ", idTaiKhoanNhan=" + idTaiKhoanNhan +
                ", tieuDe='" + tieuDe + '\'' +
                ", noiDung='" + noiDung + '\'' +
                ", trangThai=" + trangThai +
                ", nguoiGui=" + nguoiGui +
                ", taiKhoanNhan=" + taiKhoanNhan +
                '}';
    }
}
