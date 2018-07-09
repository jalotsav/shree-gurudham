/*
 * Copyright (c) 2018 Jalotsav
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jalotsav.shreegurudham.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Jalotsav on 7/9/2018.
 */
public class GeneralFunctions {

    /***
     * Check Internet Connection
     * Mobile device is connect with Internet or not?
     * ***/
    public static boolean isNetConnected(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //This for Wifi.
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected())
            return true;

        //This for Mobile Network.
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected())
            return true;

        //This for Return true else false for Current status.
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            return true;

        return false;
    }

    /**
     *  Show/Cancel Toast
     **/
    private static Toast TOAST = null;

    public static void showToastSingle(Context context, String message, int toastLength) {

        if(TOAST != null) TOAST.cancel();
        switch (toastLength) {
            case Toast.LENGTH_SHORT:

                TOAST = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                TOAST.show();
                break;
            case Toast.LENGTH_LONG:

                TOAST = Toast.makeText(context, message, Toast.LENGTH_LONG);
                TOAST.show();
                break;
            default:

                TOAST = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                TOAST.show();
                break;
        }
    }
}
