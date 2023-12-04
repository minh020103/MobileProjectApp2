package com.example.mobileprojectapp2.datamodel.fcm;

public class PushNotification {
    private Notification notification;
    private String to;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public PushNotification(Notification notification, String to) {
        this.notification = notification;
        this.to = to;
    }
}
