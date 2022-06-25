package com.wasitech.permissioncenter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wasitech.permissioncenter.classes.Utils;
import com.wasitech.permissioncenter.classes.WPermission;
import com.wasitech.permissioncenter.classes.WRequest;
import com.wasitech.permissioncenter.inter.RequestListener;
import com.wasitech.permissioncenter.inter.ResultListener;

import java.util.Arrays;

public class WCompatActivity extends AppCompatActivity implements ResultListener, RequestListener {
    private WPermission per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        per = new WPermission(this, this, this);
    }


    @NonNull
    protected WRequest request() {
        return per.request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        per.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void firstTimeRequested(@NonNull String[] pers, int rCode) {
        Utils.Log("firstTimeRequested");
        Utils.Log(Arrays.toString(pers));
    }

    @Override
    public void curGranted(@NonNull String[] pers, int rCode) {
        Utils.Log("curGranted");
        Utils.Log(Arrays.toString(pers));
    }

    @Override
    public void onGrantedAll(@NonNull String[] pers, int rCode) {
        Utils.Log("onGrantedAll");
        Utils.Log(Arrays.toString(pers));
    }

    @Override
    public void prevGranted(@NonNull String[] pers, int rCode) {
        Utils.Log("prevGranted");
        Utils.Log(Arrays.toString(pers));
    }

    @Override
    public void curDenied(@NonNull String[] pers, int rCode) {
        Utils.Log("curDenied");
        Utils.Log(Arrays.toString(pers));
    }

    @Override
    public void onDeniedAll(@NonNull String[] pers, int rCode) {
        Utils.Log("onDeniedAll");
        Utils.Log(Arrays.toString(pers));
    }

    @Override
    public void prevDenied(@NonNull String[] pers, int rCode) {
        Utils.Log("prevDenied");
        Utils.Log(Arrays.toString(pers));
    }

    @Override
    public void onComplete(int rCode) {
        Utils.Log("onComplete");
        Utils.Log(rCode + "");
    }

    @NonNull
    @Override
    public String setRationalMessage(int rCode) {
        return "";
    }

    @Override
    public void unableToAskPermission(@NonNull String[] per, int rCode) {
        Utils.Log("whenUnableToShow");
    }

    @Override
    public void rationalPopUpCancelled(int rCode) {
        Utils.Log("rationalPopUpCancelled");
    }

    @Override
    public void onError(String message) {
        Utils.Log("error " + message);

    }

}