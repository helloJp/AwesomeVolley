package me.jp.volley.common;

import android.app.Application;

/**
 * Created by JiangPing on 2015/6/4.
 */
public class App extends Application {
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }
}
