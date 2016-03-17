package com.easy.pointapp.model.api.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mini1 on 03.07.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    @JsonProperty("text")
    public String mText;

    @JsonProperty("user")
    public String mUser;

    @JsonProperty("post")
    public String mPost;

    @JsonProperty("_id")
    public String mID;

    @JsonProperty("like")
    public Integer mLikeNumber;

    @JsonProperty("icon")
    public String mIcon;

    @JsonProperty("icon_color")
    public String mIconColor;

    @JsonProperty("created")
    public String mCreatedDate;

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
