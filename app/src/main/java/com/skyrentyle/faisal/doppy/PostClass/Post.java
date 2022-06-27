package com.skyrentyle.faisal.doppy.PostClass;

import com.google.firebase.database.ServerValue;

public class Post {
    private String title;
    private String description;
    private String userID;
    private String contact="Meta";
    private String postID;
    private String pickedImageUrl;
    private Object timeStamp;

    public Post(String title, String description, String userID, String pickedImageUrl,String contact) {
        this.title = title;
        this.description = description;
        this.userID = userID;
        this.pickedImageUrl = pickedImageUrl;
        this.contact=contact;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public Post() {

    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setPickedImageUrl(String pickedImageUrl) {
        this.pickedImageUrl = pickedImageUrl;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUserID() {
        return userID;
    }

    public String getPostID() {
        return postID;
    }

    public String getPickedImageUrl() {
        return pickedImageUrl;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }
}
