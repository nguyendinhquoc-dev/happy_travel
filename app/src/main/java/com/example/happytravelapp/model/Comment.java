package com.example.happytravelapp.model;

public class Comment {
    public String uid;
    public String author;
    public String authorPic;
    public String text;

    public Comment() {
    }

    public Comment(String uid, String author, String authorPic,String text) {
        this.uid = uid;
        this.author = author;
        this.authorPic=authorPic;
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorPic() {
        return authorPic;
    }

    public void setAuthorPic(String authorPic) {
        this.authorPic = authorPic;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
