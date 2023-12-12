package com.example.mobileprojectapp2.api;

public class Const {
    public static String DOMAIN = "http://192.168.1.231/3t/laravel/public/";
    public static final String PRE_LOGIN = "SharedPreferencesLogin";
	//Giới tính
    public static final int ALL_GENDERS = 0;
    public static final int MALE_GENDERS = 1;
    public static final int FEMALE_GENDERS = 2;

    public static final int PHONG_TRONG = 0;
    public static final int PHONG_DON = 1;
    public static final int PHONG_GHEP = 2;

    public static final String LON_DEN_NHO = "DESC";
    public static final String NHO_DEN_LON = "ASC";

    public static final int KHONG_HOAT_DONG = 0;
    public static final int HOAT_DONG = 1;

    public static final int PHONG_DA_CO_NGUOI_THUE = 100;
    public static final int DA_DAT_SO_LUONG_PHONG_TOI_DA = 101;
    public static final int CHUA_DANG_KY_DICH_VU = 102;

    //Firebase notification (Firebase cloud messaging)
    //Không được sửa mấy cái này nhe
    public static final String SERVER_KEY = "AAAAfDFy5i0:APA91bGe3Y99w0mvfaDwtR03dvFu_-0rt-HoP0NhLNjNkyFIimiUNwOdfxjtTYOQVdnCKhDrHr1FeeCpm5XidQZUhWDXZRcI4lAM9gl6CjeZSyTDsy6MBxYASClwUTugYd14FcPx4HgR";
    public static final String BASE_URL = "https://fcm.googleapis.com";
    public static final String CONTENT_TYPE = "application/json";

    public static final int NGUOI_THUE = 0;
    public static final int CHU_TRO = 1;
    public static final int ADMIN = 2;
    public static final String HEADER_YOUTUBE = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/";
    public static final String FOOTER_YOUTUBE = "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
    public static final String HEADER_DEVICE = "<iframe width=\"100%\" height=\"100%\" src=\"";
}
