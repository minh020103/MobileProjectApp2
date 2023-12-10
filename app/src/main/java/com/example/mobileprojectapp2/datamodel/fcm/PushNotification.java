package com.example.mobileprojectapp2.datamodel.fcm;

public class PushNotification {
    private Notification data;
    private String to;

    public PushNotification(Notification data, String to) {
        this.data = data;
        this.to = to;
    }

    public Notification getData() {
        return data;
    }

    public void setData(Notification data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public PushNotification(Notification data, String to) {
        this.data = data;
        this.to = to;
    }
}
