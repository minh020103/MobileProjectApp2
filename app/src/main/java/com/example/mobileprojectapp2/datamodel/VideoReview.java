package com.example.mobileprojectapp2.datamodel;

public class VideoReview {
    private int idPhong;
    private String linkVideo;
    private int loaiVideo;

    public int getLoaiVideo() {
        return loaiVideo;
    }

    public void setLoaiVideo(int loaiVideo) {
        this.loaiVideo = loaiVideo;
    }

    public VideoReview(int idPhong, String linkVideo, int loaiVideo) {
        this.idPhong = idPhong;
        this.linkVideo = linkVideo;
        this.loaiVideo = loaiVideo;
    }

    public VideoReview(int idPhong, String linkVideo) {
        this.idPhong = idPhong;
        this.linkVideo = linkVideo;
    }

    public int getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(int idPhong) {
        this.idPhong = idPhong;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }
}
