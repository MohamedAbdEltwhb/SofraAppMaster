package com.example.mm.sofraappmaster;

import android.app.Application;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;

import java.util.Locale;


public class App extends Application {


    private static Application mInstance;
    public static String Lang = "ar";
    Locale loc;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static synchronized Application getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(Lang.equals("ar")) {
            Lang = "ar";
            String ar = "ar";
            loc = new Locale(ar);
            Locale.setDefault(loc);
            Configuration config = new Configuration();
            config.locale = loc;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (Lang.equals("ar")) {
            loc = new Locale("ar");
        } else {
            loc = new Locale("en");
        }

        Locale.setDefault(loc);
        Configuration config = new Configuration();
        config.locale = loc;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

    }
}