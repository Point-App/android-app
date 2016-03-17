package com.easy.pointapp.model;

import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.CommentsLoader;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.model.system.DeviceInformationManager;
import com.fasterxml.jackson.annotation.JsonProperty;

import android.location.Location;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by nixan on 17.03.16.
 */
public interface PointRestService {

    class RequestBoilerplate {

        @JsonProperty("user")
        private final String mUser;

        private final String mOSVersion;

        private final String mSDKName;

        private final String mSDKVersion;

        @JsonProperty("dev_model")
        private final String mDeviceModel;

        @JsonProperty("app_v")
        private final String mApplicationVersion;

        public RequestBoilerplate(String user) {
            mUser = user;
            mOSVersion = DeviceInformationManager.getOSVersion();
            mSDKName = DeviceInformationManager.getSDKName();
            mSDKVersion = DeviceInformationManager.getSDKVersion();
            mDeviceModel = DeviceInformationManager.getDeviceModel();
            mApplicationVersion = DeviceInformationManager.getAppVersion();
        }

        @JsonProperty("dev_os")
        private String getDeviceOS() {
            return "OS: android : " + mOSVersion + " : " +
                    mSDKName + " : sdk=" + mSDKVersion;
        }
    }

    class RequestWithLocation extends RequestBoilerplate {

        private final Location mLocation;

        public RequestWithLocation(String user, Location location) {
            super(user);
            mLocation = location;
        }

        @JsonProperty("lat")
        private String getLatitude() {
            return Double.toString(mLocation.getLatitude());
        }

        @JsonProperty("lon")
        private String getLongitude() {
            return Double.toString(mLocation.getLongitude());
        }
    }

    @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
    @POST("show")
    Observable<List<Post>> getPosts(@Body RequestWithLocation requestBoilerplate);

    @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
    @POST("show2")
    Observable<List<Post>> getFavouritePosts(@Body RequestBoilerplate requestBoilerplate);

    @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
    @POST("details")
    Observable<List<Comment>> getComments(@Body CommentsLoader.CommentsRequest requestBoilerplate);

}
