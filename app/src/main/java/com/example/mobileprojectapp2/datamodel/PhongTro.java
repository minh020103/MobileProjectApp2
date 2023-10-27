package com.example.mobileprojectapp2.datamodel;

import java.util.List;

public class PhongTro {

    public PhongTro(List<HinhAnh> list) {
        this.list = list;
    }

    private List<HinhAnh> list;

    public List<HinhAnh> getList() {
        return list;
    }

    public void setList(List<HinhAnh> list) {
        this.list = list;
    }
}
