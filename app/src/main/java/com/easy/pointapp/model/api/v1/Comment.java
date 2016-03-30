package com.easy.pointapp.model.api.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mini1 on 03.07.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    @JsonProperty("text")
    private String mText;

    @JsonProperty("user")
    private String mUser;

    @JsonProperty("post")
    private String mPost;

    private String mID;

    @JsonProperty("like")
    private Integer mLikeNumber;

    @JsonProperty("icon")
    private String mIcon;

    @JsonProperty("icon_color")
    private String mIconColor;

    @JsonProperty("created")
    private String mCreatedDate;

    public void set_id (String _id){this.mID = _id;}
    public void setId (String id){this.mID = id;}

    public String getText() {
        return mText;
    }

    public String getUser() {
        return mUser;
    }

    public String getPost() {
        return mPost;
    }

    public String getID() {
        return mID;
    }

    public Integer getLikeNumber() {
        return mLikeNumber;
    }

    public String getIcon() {
        return mIcon;
    }

    public String getIconColor() {
        return mIconColor;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }
}
