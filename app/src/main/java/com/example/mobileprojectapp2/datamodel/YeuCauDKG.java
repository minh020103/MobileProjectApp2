package com.example.mobileprojectapp2.datamodel;

public class YeuCauDKG {
    private String idChuTro;
    private String idGoi;
    private String hinhAnhChuyenKhoan;

    public String getIdChuTro() {
        return idChuTro;
    }

    public void setIdChuTro(String idChuTro) {
        this.idChuTro = idChuTro;
    }

    public String getIdGoi() {
        return idGoi;
    }

    public void setIdGoi(String idGoi) {
        this.idGoi = idGoi;
    }

    public String getHinhAnhChuyenKhoan() {
        return hinhAnhChuyenKhoan;
    }

    public void setHinhAnhChuyenKhoan(String hinhAnhChuyenKhoan) {
        this.hinhAnhChuyenKhoan = hinhAnhChuyenKhoan;
    }

    public YeuCauDKG(String idChuTro, String idGoi, String hinhAnhChuyenKhoan) {
        this.idChuTro = idChuTro;
        this.idGoi = idGoi;
        this.hinhAnhChuyenKhoan = hinhAnhChuyenKhoan;
    }
}
