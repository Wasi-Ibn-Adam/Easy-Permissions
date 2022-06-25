package com.wasitech.permissioncenter.inter;

import androidx.annotation.NonNull;

public interface ResultListener {

    /**
     * @param pers  List of permissions first time requested
     * @param rCode request code
     * @implNote this callback invokes when a permission is requested first time only
     */
    void firstTimeRequested(@NonNull String[] pers, int rCode);

    /**
     * @param pers  List of permissions granted currently from the requested ones
     * @param rCode request code
     * @implNote this callback invokes when a permission is granted in result of current request
     * moreover this callback will invoke only if some of permissions granted or denied, if all
     * permissions granted or denied this method will not invoke
     * @see #onGrantedAll(String[], int)
     * @see #prevGranted(String[], int)
     */
    void curGranted(@NonNull String[] pers, int rCode);

    /**
     * @param pers  List of permissions granted
     * @param rCode request code
     * @implNote this callback invokes when all permissions granted
     * @see #curGranted(String[], int)
     * @see #prevGranted(String[], int)
     */
    void onGrantedAll(@NonNull String[] pers, int rCode);

    /**
     * @param pers  List of permissions granted before request
     * @param rCode request code
     * @implNote this callback invokes when some or all permissions were granted before current request
     * @see #onGrantedAll(String[], int)
     * @see #curGranted(String[], int)
     */
    void prevGranted(@NonNull String[] pers, int rCode);

    /**
     * @param pers  List of permissions denied currently
     * @param rCode request code
     * @implNote this callback invokes when a permission is denied in result of current request
     * moreover this callback will invoke only if some of permissions granted or denied, if all
     * permissions granted or denied this method will not invoke
     * @see #onDeniedAll(String[], int)
     * @see #prevDenied(String[], int)
     */
    void curDenied(@NonNull String[] pers, int rCode);

    /**
     * @param pers  List of permissions denied
     * @param rCode request code
     * @implNote this callback invokes when all permissions denied
     * @see #curDenied(String[], int)
     * @see #prevDenied(String[], int)
     */
    void onDeniedAll(@NonNull String[] pers, int rCode);

    /**
     * @param pers  List of permissions denied before request
     * @param rCode request code
     * @implNote this callback invokes when some or all permissions were denied before current request
     * @see #onDeniedAll(String[], int)
     * @see #prevDenied(String[], int)
     */
    void prevDenied(@NonNull String[] pers, int rCode);

    /**
     * @param rCode request code
     * @implNote Final callback this method invokes everytime when result is complete
     */
    void onComplete(int rCode);

}
