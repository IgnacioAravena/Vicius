package com.devazt.networking;

import android.os.StrictMode;

/**
 * Created by Neksze on 01/10/2015.
 */
public class AndroidHelper {

    protected static void AddStrictMode(){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

}
