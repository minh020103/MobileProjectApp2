package com.example.mobileprojectapp2.model;

import com.example.mobileprojectapp2.datamodel.ChuTro;
import com.example.mobileprojectapp2.datamodel.NguoiThue;

public class YeuCauDatPhong {
    private int id;
    private int idTaiKhoanGui;
    private int idTaiKhoanNhan;
    private int idPhong;
    private int trangThaiXacThuc;
    private int trangThaiThongBao;
    private int trangThaiNhan;

    public YeuCauDatPhong(int id, int idTaiKhoanGui, int idTaiKhoanNhan, int idPhong, int trangThaiXacThuc, int trangThaiThongBao, int trangThaiNhan) {
        this.id = id;
        this.idTaiKhoanGui = idTaiKhoanGui;
        this.idTaiKhoanNhan = idTaiKhoanNhan;
        this.idPhong = idPhong;
        this.trangThaiXacThuc = trangThaiXacThuc;
        this.trangThaiThongBao = trangThaiThongBao;
        this.trangThaiNhan = trangThaiNhan;
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

    public int getTrangThaiNhan() {
        return trangThaiNhan;
    }

    public void setTrangThaiNhan(int trangThaiNhan) {
        this.trangThaiNhan = trangThaiNhan;
    }
}


