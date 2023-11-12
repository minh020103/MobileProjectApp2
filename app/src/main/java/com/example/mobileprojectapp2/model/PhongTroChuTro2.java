package com.example.mobileprojectapp2.model;

import com.example.mobileprojectapp2.datamodel.HinhAnh;

import java.io.Serializable;
import java.util.List;

public class PhongTroChuTro2 implements Serializable {
    private int id;
    private int idChuTro;
    private int idPhongTro;

    private PhongTro phongTro;

    public PhongTroChuTro2() {
    }

    public PhongTro getPhongTro() {
        return phongTro;
    }

    public void setPhongTro(PhongTro phongTro) {
        this.phongTro = phongTro;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdChuTro() {
        return idChuTro;
    }

    public void setIdChuTro(int idChuTro) {
        this.idChuTro = idChuTro;
    }

    public int getIdPhongTro() {
        return idPhongTro;
    }

    public void setIdPhongTro(int idPhongTro) {
        this.idPhongTro = idPhongTro;
    }
}
