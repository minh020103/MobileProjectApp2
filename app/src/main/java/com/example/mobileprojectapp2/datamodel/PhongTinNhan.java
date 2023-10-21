package com.example.mobileprojectapp2.datamodel;

public class PhongTinNhan {
    private int id;
    private int idUser1;
    private int idUser2;
    private String tinNhanMoiNhat;
    private String thoiGianCuaTinNhan;

    public PhongTinNhan(int id, int idUser1, int idUser2, String tinNhanMoiNhat, String thoiGianCuaTinNhan) {
        this.id = id;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
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

    public int getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(int idUser1) {
        this.idUser1 = idUser1;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }

    public String getTinNhanMoiNhat() {
        return tinNhanMoiNhat;
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

}
