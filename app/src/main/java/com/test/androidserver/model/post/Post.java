package com.test.androidserver.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {

    @SerializedName("post_id")
    @Expose
    private String postId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_dp")
    @Expose
    private String userDp;
    @SerializedName("user_loc")
    @Expose
    private String userLoc;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("post_desc")
    @Expose
    private String postDesc;
    @SerializedName("post_bg_id")
    @Expose
    private String postBgId;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("favourites")
    @Expose
    private String favourites;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("comment_count")
    @Expose
    private String commentCount;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("priority")
    @Expose
    private String priority;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDp() {
        return userDp;
    }

    public void setUserDp(String userDp) {
        this.userDp = userDp;
    }

    public String getUserLoc() {
        return userLoc;
    }

    public void setUserLoc(String userLoc) {
        this.userLoc = userLoc;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getPostBgId() {
        return postBgId;
    }

    public void setPostBgId(String postBgId) {
        this.postBgId = postBgId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getFavourites() {
        return favourites;
    }

    public void setFavourites(String favourites) {
        this.favourites = favourites;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}