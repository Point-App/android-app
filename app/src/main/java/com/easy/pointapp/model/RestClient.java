package com.easy.pointapp.model;

import com.easy.pointapp.BuildConfig;
import com.easy.pointapp.model.api.v1.Authentication;
import com.easy.pointapp.model.api.v1.Comment;
import com.easy.pointapp.model.api.v1.Post;
import com.easy.pointapp.model.system.DeviceInformationManager;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

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
import rx.Subscriber;
import rx.functions.Func1;

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
        @POST("comments")
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

    public static Observable<Void> likePost(final Context context, final String postID) {
        return getToken(context).flatMap(new Func1<String, Observable<Void>>() {
            @Override
            public Observable<Void> call(String s) {
                return RestClient.getService().sendLike(
                        new Request.Builder().setUserID(s).addValue("type", "post")
                                .addValue("target", postID).build());
            }
        });
    }

    public static Observable<Post> loadPost(final Context context, final String postID) {
        return getToken(context).flatMap(new Func1<String, Observable<Post>>() {
            @Override
            public Observable<Post> call(String s) {
                return RestClient.getNewService().loadPost(
                        new Request.Builder().setUserID(s).addValue("post", postID).build());
            }
        });
    }

    public static Observable<List<Post>> loadFavouritePosts(Context context) {
        return RestClient.getNewService().loadFavouritePosts(
                new Request.Builder().setUserID(AuthManager.getAuthToken(context)).build());
    }

    public static Observable<List<Post>> loadPosts(final Context context, final Location location) {
        return getToken(context).flatMap(new Func1<String, Observable<List<Post>>>() {
            @Override
            public Observable<List<Post>> call(String s) {
                return RestClient.getNewService().loadPosts(
                        new Request.Builder().setUserID(s).setLocation(location).build());
            }
        });
    }

    public static Observable<Void> sendPost(final Context context, final Location location,
            final String body) {
        return getToken(context).flatMap(new Func1<String, Observable<Void>>() {
            @Override
            public Observable<Void> call(String s) {
                return RestClient.getService().sendPost(
                        new Request.Builder().setUserID(s).setLocation(location)
                                .addValue("text", body).build());
            }
        });
    }

    public static Observable<Void> likeComment(final Context context, final String commentID) {
        return getToken(context).flatMap(new Func1<String, Observable<Void>>() {
            @Override
            public Observable<Void> call(String s) {
                return RestClient.getService().sendLike(
                        new Request.Builder().setUserID(s).addValue("type", "comment")
                                .addValue("target", commentID).build());
            }
        });
    }

    public static Observable<List<Comment>> loadComments(final Context context,
            final String postID) {
        return getToken(context).flatMap(new Func1<String, Observable<List<Comment>>>() {
            @Override
            public Observable<List<Comment>> call(String s) {
                return RestClient.getNewService().loadComments(
                        new Request.Builder().setUserID(s).addValue("post", postID).build());
            }
        });
    }

    public static Observable<Void> sendComment(final Context context, final String postID,
            final String comment) {
        return getToken(context).flatMap(new Func1<String, Observable<Void>>() {
            @Override
            public Observable<Void> call(String s) {
                return RestClient.getService().sendComment(
                        new Request.Builder().setUserID(s).addValue("post", postID)
                                .addValue("text", comment).build());
            }
        });
    }

    public static Observable<Authentication> authenticate() {
        return RestClient.getService().register(new Request.Builder().build());
//                        .setUserID(AuthManager.getAuthToken(context))
//                        .setLocation(location).build());
    }

    private static Observable<String> getToken(final Context context) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(AuthManager.getAuthToken(context));
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(final String s) {
                if (TextUtils.isEmpty(s)) {
                    return authenticate().map(new Func1<Authentication, String>() {
                        @Override
                        public String call(Authentication authentication) {
                            return authentication.getToken();
                        }
                    });
                } else {
                    return Observable.just(s);
                }
            }
        });
    }
}
