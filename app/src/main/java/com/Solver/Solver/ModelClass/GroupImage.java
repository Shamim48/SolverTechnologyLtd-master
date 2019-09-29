package com.Solver.Solver.ModelClass;

public class GroupImage {
    private String name;
    private String sender;
    private String time;
    private String date;
    private String imageUri;
    private String type;
    public GroupImage(String name, String sender, String time, String date, String imageUri, String type) {
        this.name = name;
        this.sender = sender;
        this.time = time;
        this.date = date;
        this.imageUri = imageUri;
        this.type = type;
    }


    public GroupImage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GroupImage{" +
                "name='" + name + '\'' +
                ", sender='" + sender + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
