package com.wasitech.wpermissions.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

public class Utils {
    private final SharedPreferences pref;
    protected final Activity ac;

    public Utils(Activity ac) {
        this.ac = ac;
        pref = ac.getSharedPreferences(ac.getPackageName(), Context.MODE_PRIVATE);
    }
    public static class Intents{
        public static Intent gotoSettings(String pkg) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", pkg, null);
            intent.setData(uri);
            return intent;
        }

        public static Intent gotoDrawOver(String pkg) {
            return new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + pkg));
        }

        //private void requestPermission(Activity context, int code) {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        //            try {
        //                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        //                intent.addCategory("android.intent.category.DEFAULT");
        //                intent.setData(Uri.parse(String.format("package:%s", context.getApplicationContext().getPackageName())));
        //                context.startActivityForResult(intent, code);
        //            } catch (Exception e) {
        //                Intent intent = new Intent();
        //                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
        //                context.startActivityForResult(intent, code);//2296
        //            }
        //        } else {
        //            //below android 11
        //            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, code);
        //        }
        //    }
    }
    public static void Log(String log) {
        Log.i("founder", log);
    }


    protected void setValue(String tag, int val) {
        setNew(tag);
        pref.edit().putInt(tag, val).apply();
    }

    protected void setValue(String tag, String val) {
        setOLd(tag);
        pref.edit().putString(tag, val).apply();
    }

    protected void setValue(String tag, boolean val) {
        setOLd(tag);
        pref.edit().putBoolean(tag, val).apply();
    }

    protected int getValue(String tag, int def) {
        return pref.getInt(tag, def);
    }

    protected String getValue(String tag, String def) {
        return pref.getString(tag, def);
    }

    protected boolean getValue(String tag, boolean def) {
        return pref.getBoolean(tag, def);
    }

    protected void setNew(String tag) {
        if (isOLd(tag))
            return;
        pref.edit().putBoolean("new_" + tag, true).apply();
    }

    protected void setOLd(String tag) {
        if (isNew(tag))
            pref.edit().remove("new_" + tag).apply();
    }

    protected boolean isNew(String tag) {
        return pref.getAll().containsKey("new_" + tag);
    }

    private boolean isOLd(String tag) {
        return pref.getAll().containsKey(tag) && !isNew(tag);
    }

}
