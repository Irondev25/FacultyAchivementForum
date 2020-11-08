package com.irondev25.facultyachivementforum.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RequestPermission {
    public static boolean getPermission(Activity activity, Context context){
        if(Build.VERSION.SDK_INT >= 23) {
            if(ContextCompat.checkSelfPermission(activity.getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            else{
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                return false;
            }
        }
        return true;
    }
}
