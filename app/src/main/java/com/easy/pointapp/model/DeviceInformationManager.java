package com.easy.pointapp.model;

import android.os.Build;

import com.easy.pointapp.BuildConfig;

/**
 * Created by Igor on 28.06.2015.
 */
public class DeviceInformationManager {
    public static String getAppVersion()
    {
        return BuildConfig.VERSION_NAME;
    }
    public static String getDeviceModel()
    {
        String manufacturer = Build.MANUFACTURER;
        String brand        = Build.BRAND;
        String product      = Build.PRODUCT;
        String model        = Build.MODEL;
        return manufacturer+" "+brand+" "+product+" "+model;
    }
    public static String getOSVersion()
    {
        String androidVersion = android.os.Build.VERSION.RELEASE;
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        return androidVersion;
    }
    public static String getSDKVersion()
    {
        Integer sdkVersion = android.os.Build.VERSION.SDK_INT;
        return sdkVersion.toString();
    }
    public static String getSDKName()
    {
        Integer sdkVersion = android.os.Build.VERSION.SDK_INT;
        String sdkName = "UNKNOWN";
        switch (sdkVersion)
        {
            case Build.VERSION_CODES.BASE:
                sdkName = "BASE";
                break;
            case Build.VERSION_CODES.BASE_1_1:
                sdkName = "BASE_1_1";
                break;
            case Build.VERSION_CODES.CUPCAKE:
                sdkName = "CUPCAKE";
                break;
            case Build.VERSION_CODES.CUR_DEVELOPMENT:
                sdkName = "CUR_DEVELOPMENT";
                break;
            case Build.VERSION_CODES.DONUT:
                sdkName = "DONUT";
                break;
            case Build.VERSION_CODES.ECLAIR:
                sdkName = "ECLAIR";
                break;
            case Build.VERSION_CODES.ECLAIR_0_1:
                sdkName = "ECLAIR_0_1";
                break;
            case Build.VERSION_CODES.ECLAIR_MR1:
                sdkName = "ECLAIR_MR1";
                break;
            case Build.VERSION_CODES.FROYO:
                sdkName = "FROYO";
                break;
            case Build.VERSION_CODES.GINGERBREAD:
                sdkName = "GINGERBREAD";
                break;
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                sdkName = "GINGERBREAD_MR1";
                break;
            case Build.VERSION_CODES.HONEYCOMB:
                sdkName = "HONEYCOMB";
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR1:
                sdkName = "HONEYCOMB_MR1";
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                sdkName = "HONEYCOMB_MR2";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                sdkName = "ICE_CREAM_SANDWICH";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                sdkName = "ICE_CREAM_SANDWICH_MR1";
                break;
            case Build.VERSION_CODES.JELLY_BEAN:
                sdkName = "JELLY_BEAN";
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
                sdkName = "JELLY_BEAN_MR1";
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                sdkName = "JELLY_BEAN_MR2";
                break;
            case Build.VERSION_CODES.KITKAT:
                sdkName = "KITKAT";
                break;
            case Build.VERSION_CODES.KITKAT_WATCH:
                sdkName = "KITKAT_WATCH";
                break;
            case Build.VERSION_CODES.LOLLIPOP:
                sdkName = "LOLLIPOP";
                break;
            case Build.VERSION_CODES.LOLLIPOP_MR1:
                sdkName = "LOLLIPOP_MR1";
                break;
        }
        return sdkName;
    }
}
