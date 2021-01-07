package com.example.happytravelapp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.HashMap;
import java.util.Map;

public class Post extends BaseObservable {
    public String uid;
    public String postid;
    public String author;
    public String profilePic;
    public String title;
    public String body;
    public String imageUrl;
    private Object timestamp;
    public int starCount = 0;
    public int commentCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
    }

    public Post(String uid,String postid, String author,String profilepic, String title, String body, String imageUrl,Object timestamp,int starCount,int commentCount) {
        this.uid = uid;
        this.postid=postid;
        this.author = author;
        this.profilePic=profilepic;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.timestamp=timestamp;
        this.starCount=starCount;
        this.commentCount=commentCount;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("postid", postid);
        result.put("author", author);
        result.put("profilePic",profilePic);
        result.put("title", title);
        result.put("body", body);
        result.put("imageUrl", imageUrl);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("timestamp",timestamp);

        return result;
    }

    @Bindable
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        notifyPropertyChanged(BR.uid);
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    @Bindable
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        notifyPropertyChanged(BR.author);
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        notifyPropertyChanged(BR.body);
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    @Bindable
    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
        notifyPropertyChanged(BR.starCount);
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Bindable
    public Map<String, Boolean> getStars() {
        return stars;
    }

    public void setStars(Map<String, Boolean> stars) {
        this.stars = stars;
        notifyPropertyChanged(BR.stars);
    }
}
