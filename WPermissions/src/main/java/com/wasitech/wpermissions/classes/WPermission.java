package com.wasitech.wpermissions.classes;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.wasitech.wpermissions.inter.RequestListener;
import com.wasitech.wpermissions.inter.ResultListener;

public class WPermission {
    private WRequest sr;
    private WResult res;
    private final ResultListener result;
    private final RequestListener request;

    public WPermission(Activity ac, @NonNull ResultListener resultListener, @NonNull RequestListener requestListener) {
        this.result = resultListener;
        this.request = requestListener;
        init(ac);
    }

    private void init(Activity ac) {
        sr = new WRequest(ac) {
            @NonNull
            @Override
            public String setRationalMessage(int rCode) {
                return request.setRationalMessage(rCode);
            }

            @Override
            public void unableToAskPermission(@NonNull String[]per,int rCode) {
                request.unableToAskPermission(per,rCode);
            }

            @Override
            public void rationalPopUpCancelled(int rCode) {
                request.rationalPopUpCancelled(rCode);
            }

            @Override
            public void onError(String message) {
                request.onError(message);
            }
        };
        res = new WResult(ac) {
            @Override
            public void firstTimeRequested(@NonNull String[] pers, int rCode) {
                result.firstTimeRequested(pers, rCode);
            }

            @Override
            public void curGranted(@NonNull String[] pers, int rCode) {
                result.curGranted(pers, rCode);
            }

            @Override
            public void onGrantedAll(@NonNull String[] pers, int rCode) {
                result.onGrantedAll(pers, rCode);
            }

            @Override
            public void prevGranted(@NonNull String[] pers, int rCode) {
                result.prevGranted(pers, rCode);
            }

            @Override
            public void curDenied(@NonNull String[] pers, int rCode) {
                result.curDenied(pers, rCode);
            }

            @Override
            public void onDeniedAll(@NonNull String[] pers, int rCode) {
                result.onDeniedAll(pers, rCode);
            }

            @Override
            public void prevDenied(@NonNull String[] pers, int rCode) {
                result.prevDenied(pers, rCode);
            }

            @Override
            public void onComplete(int rCode) {
                result.onComplete(rCode);
            }
        };
    }

    @NonNull
    public WRequest request() {
        return sr;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        res.makeResult(requestCode, permissions, grantResults);
    }

}
