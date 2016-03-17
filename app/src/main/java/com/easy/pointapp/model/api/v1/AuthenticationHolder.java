package com.easy.pointapp.model.api.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Igor on 28.06.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationHolder {

    @JsonProperty("token")
    private String mToken;

    public String getToken() {
        return mToken;
    }
}
