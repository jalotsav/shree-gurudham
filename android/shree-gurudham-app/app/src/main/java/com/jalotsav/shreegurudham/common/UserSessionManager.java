package com.jalotsav.shreegurudham.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jalotsav on 7/7/2018.
 */
public class UserSessionManager implements AppConstants {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    // Constructor
    public UserSessionManager(Context context) {

        this.mContext = context;
        String PREFER_NAME = mContext.getPackageName() + "_shrdprfrnc";
        int PRIVATE_MODE = 0;
        pref = mContext.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    // Get-Set Selected Language to SharedPreferences
    public String getSelectedLanguage() {

        return pref.getString(KEY_SELECTED_LANGUAGE, LANGUAGE_SHORT_GUJARATI);
    }

    public void setSelectedLanguage(String selectedLanguage) {

        editor.putString(KEY_SELECTED_LANGUAGE, selectedLanguage);
        editor.commit();
    }

    // Get-Set Language Changed to SharedPreferences
    public boolean isLanguageChanged() {

        return pref.getBoolean(KEY_LANGUAGE_CHANGED, false);
    }

    public void setLanguageChanged(boolean isLanguageChanged) {

        editor.putBoolean(KEY_LANGUAGE_CHANGED, isLanguageChanged);
        editor.commit();
    }
}
