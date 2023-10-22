package com.example.mobileprojectapp2.datamodel;

public class PhongTinNhan {
    private int id;
    private int idTaiKhoan1;
    private int idTaiKhoan2;
    private String tinNhanMoiNhat;
    private String thoiGianCuaTinNhan;

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
