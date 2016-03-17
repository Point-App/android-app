package com.easy.pointapp.model;

import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.model.system.DeviceInformationManager;

import android.location.Location;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by nixan on 17.03.16.
 */
public interface PointRestService {

    public class Request extends HashMap<String, Object> {

        private Request() {
            // Private constructor
        }

        public static class Builder {

            private final static String KEY_USER = "user";

            private final static String KEY_DEVICE_MODEL = "dev_model";

            private final static String KEY_APPLICATION_VERSION = "app_v";

            private final static String KEY_DEVICE_OS = "dev_os";

            private final static String KEY_LATITUDE = "lat";

            private final static String KEY_LONGITUDE = "lon";

            private final Request mRequest = new Request();

            public Builder addValue(String key, Object value) {
                mRequest.put(key, value);
                return this;
            }

            public Builder setUserID(String userID) {
                return addValue(KEY_USER, userID);
            }

            public Builder setDeviceModelName(String deviceModelName) {
                return addValue(KEY_DEVICE_MODEL, deviceModelName);
            }

            public Builder setApplicationVersion(String applicationVersion) {
                return addValue(KEY_APPLICATION_VERSION, applicationVersion);
            }

            public Builder setDeviceFingerprint(String osVersion, String sdkName,
                    String sdkVersion) {
                return addValue(KEY_DEVICE_OS, "OS: android : " + osVersion + " : " +
                        sdkName + " : sdk=" + sdkVersion);
            }

            public Builder setLocation(Location location) {
                return addValue(KEY_LATITUDE, Double.toString(location.getLatitude()))
                        .addValue(KEY_LONGITUDE, Double.toString(location.getLongitude()));
            }

            public Request build() {
                if (!mRequest.containsKey(KEY_APPLICATION_VERSION)) {
                    setApplicationVersion(DeviceInformationManager.getAppVersion());
                }
                if (!mRequest.containsKey(KEY_DEVICE_MODEL)) {
                    setDeviceModelName(DeviceInformationManager.getDeviceModel());
                }
                if (!mRequest.containsKey(KEY_DEVICE_OS)) {
                    setDeviceFingerprint(DeviceInformationManager.getOSVersion(),
                            DeviceInformationManager.getSDKName(),
                            DeviceInformationManager.getSDKVersion());
                }
                return mRequest;
            }
        }
    }

    @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
    @POST("show")
    Observable<List<Post>> getPosts(@Body Request request);

    @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
    @POST("show2")
    Observable<List<Post>> getFavouritePosts(@Body Request request);

    @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
    @POST("details")
    Observable<List<Comment>> getComments(@Body Request request);

}
