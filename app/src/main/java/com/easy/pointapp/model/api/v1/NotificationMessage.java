package com.easy.pointapp.model.api.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mini1 on 25.07.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationMessage {

    @JsonProperty("alert")
    private String mAlertText;

    public String getAlertText() {
        return mAlertText;
    }
}
