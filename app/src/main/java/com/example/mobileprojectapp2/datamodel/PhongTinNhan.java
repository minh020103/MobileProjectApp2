package com.example.mobileprojectapp2.datamodel;

public class PhongTinNhan {
    private int id;
    private int idTaiKhoan1;
    private int idTaiKhoan2;
    private String tinNhanMoiNhat;
    private String thoiGianCuaTinNhan;
    private NguoiThue nguoiThue;
    private ChuTro chuTro;

    public PhongTinNhan(int id, int idTaiKhoan1, int idTaiKhoan2, String tinNhanMoiNhat, String thoiGianCuaTinNhan, NguoiThue nguoiThue, ChuTro chuTro, int trangThai1, int trangThai2) {
        this.id = id;
        this.idTaiKhoan1 = idTaiKhoan1;
        this.idTaiKhoan2 = idTaiKhoan2;
        this.tinNhanMoiNhat = tinNhanMoiNhat;
        this.thoiGianCuaTinNhan = thoiGianCuaTinNhan;
        this.nguoiThue = nguoiThue;
        this.chuTro = chuTro;
        this.trangThai1 = trangThai1;
        this.trangThai2 = trangThai2;
    }

    public ChuTro getChuTro() {
        return chuTro;
    }

    public void setChuTro(ChuTro chuTro) {
        this.chuTro = chuTro;
    }

    private int trangThai1;
    private int trangThai2;

    public int getTrangThai1() {
        return trangThai1;
    }

    public void setTrangThai1(int trangThai1) {
        this.trangThai1 = trangThai1;
    }

    public int getTrangThai2() {
        return trangThai2;
    }

    public void setTrangThai2(int trangThai2) {
        this.trangThai2 = trangThai2;
    }

    public PhongTinNhan(int id, int idTaiKhoan1, int idTaiKhoan2, String tinNhanMoiNhat, String thoiGianCuaTinNhan, NguoiThue nguoiThue, int trangThai1, int trangThai2) {
        this.id = id;
        this.idTaiKhoan1 = idTaiKhoan1;
        this.idTaiKhoan2 = idTaiKhoan2;
        this.tinNhanMoiNhat = tinNhanMoiNhat;
        this.thoiGianCuaTinNhan = thoiGianCuaTinNhan;
        this.nguoiThue = nguoiThue;
        this.trangThai1 = trangThai1;
        this.trangThai2 = trangThai2;
    }

    public PhongTinNhan(int id, int idTaiKhoan1, int idTaiKhoan2, String tinNhanMoiNhat, String thoiGianCuaTinNhan, NguoiThue nguoiThue) {
        this.id = id;
        this.idTaiKhoan1 = idTaiKhoan1;
        this.idTaiKhoan2 = idTaiKhoan2;
        this.tinNhanMoiNhat = tinNhanMoiNhat;
        this.thoiGianCuaTinNhan = thoiGianCuaTinNhan;
        this.nguoiThue = nguoiThue;
    }

    public NguoiThue getNguoiThue() {
        return nguoiThue;
    }

    public void setNguoiThue(NguoiThue nguoiThue) {
        this.nguoiThue = nguoiThue;
    }

    public void setTinNhanMoiNhat(String tinNhanMoiNhat) {
        this.tinNhanMoiNhat = tinNhanMoiNhat;
    }

    public String getThoiGianCuaTinNhan() {
        return thoiGianCuaTinNhan;
    }

    public void setThoiGianCuaTinNhan(String thoiGianCuaTinNhan) {
        this.thoiGianCuaTinNhan = thoiGianCuaTinNhan;
    }

    public PhongTinNhan(int id, int idTaiKhoan1, int idTaiKhoan2, String tinNhanMoiNhat, String thoiGianCuaTinNhan) {
        this.id = id;
        this.idTaiKhoan1 = idTaiKhoan1;
        this.idTaiKhoan2 = idTaiKhoan2;
        this.tinNhanMoiNhat = tinNhanMoiNhat;
        this.thoiGianCuaTinNhan = thoiGianCuaTinNhan;
    }
    public PhongTinNhan() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTaiKhoan1() {
        return idTaiKhoan1;
    }

    public void setIdTaiKhoan1(int idTaiKhoan1) {
        this.idTaiKhoan1 = idTaiKhoan1;
    }

    public int getIdTaiKhoan2() {
        return idTaiKhoan2;
    }

    public void setIdTaiKhoan2(int idTaiKhoan2) {
        this.idTaiKhoan2 = idTaiKhoan2;
    }

    public String getTinNhanMoiNhat() {
        return tinNhanMoiNhat;
    }

}
