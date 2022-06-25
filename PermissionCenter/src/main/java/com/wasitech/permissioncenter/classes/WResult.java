package com.wasitech.permissioncenter.classes;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.wasitech.permissioncenter.inter.ResultListener;

import java.util.ArrayList;

public abstract class WResult extends Utils implements ResultListener {

    public WResult(Activity ac) {
        super(ac);
    }

    private void result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        ArrayList<String> first = new ArrayList<>();
        // PREV LOOP
        for (String per : permissions) {
            if (isNew(per)) {
                first.add(per);
                setOLd(per);
            } else {
                if (getValue(per, 999) == PackageManager.PERMISSION_GRANTED)
                    granted.add(per);
                else
                    denied.add(per);
            }

        }

        // PREV RESPONSE
        if (denied.size() != 0)
            prevDenied(denied.toArray(new String[0]),requestCode);
        if (granted.size() != 0)
            prevGranted(granted.toArray(new String[0]),requestCode);
        if (first.size() != 0)
            firstTimeRequested(first.toArray(new String[0]),requestCode);

        // CLEARING LISTS
        granted.clear();
        denied.clear();

        // CURRENT LOOP
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                granted.add(permissions[i]);
            else
                denied.add(permissions[i]);
        }

        // CURRENT RESPONSE
        if (granted.size() == permissions.length)
            onGrantedAll(permissions,requestCode);
        else if (denied.size() == permissions.length)
            onDeniedAll(permissions,requestCode);
        else {
            curDenied((String[]) denied.toArray(),requestCode);
            curGranted((String[]) granted.toArray(),requestCode);
        }
        // FINAL CALL
        onComplete(requestCode);
    }
    public void makeResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       result(requestCode, permissions, grantResults);
    }

}
