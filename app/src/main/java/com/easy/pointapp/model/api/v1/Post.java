package com.easy.pointapp.model.api.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Igor on 28.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    @JsonProperty("text")
    private String mText;

    public void set_id (String _id){this.mID = _id;}

    public void setId (String id){this.mID = id;}

    private String mID;

    @JsonProperty("like")
    private Integer mLikesNumber;

    @JsonProperty("created")
    private String mCreatedDate;

    @JsonProperty("distance")
    private String mDistanceToPost;

    @JsonProperty("backdrop")
    private String mBackdropColor;

    @JsonProperty("comments")
    private Integer mCommentsNumber;

    public String getText() {
        return mText;
    }

    public String getID() {
        return mID;
    }

    public Integer getLikesNumber() {
        return mLikesNumber;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public String getDistanceToPost() {
        return mDistanceToPost;
    }

    public String getBackdropColor() {
        return mBackdropColor;
    }

    public Integer getCommentsNumber() {
        return mCommentsNumber;
    }
}
