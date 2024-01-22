package com.example.mobileprojectapp2.model;

public class KetQuaGuiYeuCauDatPhong {
    private String message;
    private YeuCauDatPhong object;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public YeuCauDatPhong getObject() {
        return object;
    }

    public void setObject(YeuCauDatPhong object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "KetQuaGuiYeuCauDatPhong{" +
                "message='" + message + '\'' +
                ", object=" + object +
                '}';
    }
}
