package com.jalotsav.shreegurudham;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

import com.jalotsav.shreegurudham.common.UserSessionManager;

import java.util.Locale;

/**
 * Created by Jalotsav on 7/7/2018.
 */
public class ShreeGurudhamApplication extends Application {

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static ShreeGurudhamApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Set current language to Application Locale
        UserSessionManager session = new UserSessionManager(this);
        setLanguageToAppLocale(session.getSelectedLanguage());
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ShreeGurudhamApplication getInstance() {
        return mInstance;
    }

    // Set current language to application LOCALE
    private void setLanguageToAppLocale(String currentLanguage) {

        Toast.makeText(this, "Lang: " + currentLanguage, Toast.LENGTH_SHORT).show();
        Locale locale = new Locale(currentLanguage.toLowerCase());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
