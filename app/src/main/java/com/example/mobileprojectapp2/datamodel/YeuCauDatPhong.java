package com.example.mobileprojectapp2.datamodel;

import com.example.mobileprojectapp2.model.PhongTro;

public class YeuCauDatPhong {
    private int id;
    private int idTaiKhoanGui;
    private int idTaiKhoanNhan;
    private int idPhong;
    private int trangThaiXacThuc;
    private int trangThaiThongBao;
    private int trangthaiNhan;
    private PhongTro phong;
    private NguoiThue nguoiThue;

    public YeuCauDatPhong(int id, int idTaiKhoanGui, int idTaiKhoanNhan, int idPhong, int trangThaiXacThuc, int trangThaiThongBao, int trangthaiNhan, PhongTro phong, NguoiThue nguoiThue) {
        this.id = id;
        this.idTaiKhoanGui = idTaiKhoanGui;
        this.idTaiKhoanNhan = idTaiKhoanNhan;
        this.idPhong = idPhong;
        this.trangThaiXacThuc = trangThaiXacThuc;
        this.trangThaiThongBao = trangThaiThongBao;
        this.trangthaiNhan = trangthaiNhan;
        this.phong = phong;
        this.nguoiThue = nguoiThue;
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

    public int getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(int idPhong) {
        this.idPhong = idPhong;
    }

    public int getTrangThaiXacThuc() {
        return trangThaiXacThuc;
    }

    public void setTrangThaiXacThuc(int trangThaiXacThuc) {
        this.trangThaiXacThuc = trangThaiXacThuc;
    }

    public int getTrangThaiThongBao() {
        return trangThaiThongBao;
    }

    public void setTrangThaiThongBao(int trangThaiThongBao) {
        this.trangThaiThongBao = trangThaiThongBao;
    }

    public int getTrangthaiNhan() {
        return trangthaiNhan;
    }

    public void setTrangthaiNhan(int trangthaiNhan) {
        this.trangthaiNhan = trangthaiNhan;
    }

    public PhongTro getPhong() {
        return phong;
    }

    public void setPhong(PhongTro phong) {
        this.phong = phong;
    }

    public NguoiThue getNguoiThue() {
        return nguoiThue;
    }

    public void setNguoiThue(NguoiThue nguoiThue) {
        this.nguoiThue = nguoiThue;
    }

    @Override
    public String toString() {
        return "YeuCauDatPhong{" +
                "id=" + id +
                ", idTaiKhoanGui=" + idTaiKhoanGui +
                ", idTaiKhoanNhan=" + idTaiKhoanNhan +
                ", idPhong=" + idPhong +
                ", trangThaiXacThuc=" + trangThaiXacThuc +
                ", trangThaiThongBao=" + trangThaiThongBao +
                ", trangthaiNhan=" + trangthaiNhan +
                ", phong=" + phong +
                ", nguoiThue=" + nguoiThue +
                '}';
    }
}
