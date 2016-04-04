package com.easy.pointapp.model;

import com.easy.pointapp.BuildConfig;
import com.easy.pointapp.model.api.v1.Authentication;
import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.model.system.DeviceInformationManager;

import android.content.Context;
import android.location.Location;

import java.util.HashMap;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Igor on 30.06.2015.
 */
public class RestClient {

    public static class Request extends HashMap<String, Object> {

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

            public Request getRequest()
            {
                return mRequest;
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

    public interface PointRestService {

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("post")
        Observable<Void> sendPost(@Body Request request);

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("like")
        Observable<Void> sendLike(@Body Request request);

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("single")
        Observable<Post> loadPost(@Body Request request);

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("show")
        Observable<List<Post>> loadPosts(@Body Request request);

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("feed")
        Observable<List<Post>> loadFavouritePosts(@Body Request request);

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("details")
        Observable<List<Comment>> loadComments(@Body Request request);

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("hello")
        Observable<Authentication> register(@Body Request request);

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("comment")
        Observable<Void> sendComment(@Body Request request);

        @Headers({"Accept: application/json; charset=utf-8", "Content-Type: application/json"})
        @POST("pushregistration")
        Observable<Void> pushRegister(@Body Request request);

    }

    private static final Retrofit RETROFIT_SERVICE = getServiceInstance("http://77.37.212.235:3000/");
    private static final Retrofit NEW_API_SERVICE = getServiceInstance("http://77.37.212.235:4000/");

    private static final Retrofit getServiceInstance(String baseURL) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        if (BuildConfig.DEBUG) {
            builder.client(new okhttp3.OkHttpClient.Builder().addInterceptor(
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build());
        }
        return builder.build();
    }

    public static PointRestService getNewService() {
        return NEW_API_SERVICE.create(PointRestService.class);
    }
    public static PointRestService getService() {
        return RETROFIT_SERVICE.create(PointRestService.class);
    }

    public static Observable<Void> likePost(Context context, String postID) {
        return RestClient.getService().sendLike(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("type", "post").addValue("target", postID).build());
    }

    public static Observable<Post> loadPost(Context context, String postID) {
        return RestClient.getNewService().loadPost(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("post", postID).build());
    }

    public static Observable<List<Post>> loadFavouritePosts(Context context) {
        return RestClient.getNewService().loadFavouritePosts(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context)).build());
    }

    public static Observable<List<Post>> loadPosts(Context context, Location location) {
        return RestClient.getNewService().loadPosts(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .setLocation(location).build());
    }

    public static Observable<Void> sendPost(Context context, Location location, String body) {
        return RestClient.getService().sendPost(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .setLocation(location).addValue("text", body).build());
    }

    public static Observable<Void> likeComment(Context context, String commentID) {
        return RestClient.getService().sendLike(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("type", "comment").addValue("target", commentID).build());
    }

    public static Observable<List<Comment>> loadComments(Context context, String postID) {
        return RestClient.getService().loadComments(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("post", postID).build());
    }

    public static Observable<Void> sendComment(Context context, String postID, String comment) {
        return RestClient.getService().sendComment(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .addValue("post", postID).addValue("text", comment).build());
    }

    public static Observable<Authentication> authenticate(Context context, Location location) {
        return RestClient.getService().register(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context))
                        .setLocation(location).build());
    }
}
