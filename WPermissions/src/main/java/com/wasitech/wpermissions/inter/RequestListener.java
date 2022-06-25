package com.wasitech.wpermissions.inter;

import androidx.annotation.NonNull;

public interface RequestListener {

    /**
     * @param rCode the request code
     * @implNote set message to show up when it is required to rationally request to user <br>
     */
    @NonNull
    String setRationalMessage(int rCode);

    /**
     * @param rCode request code
     * @param pers  List of Permission denied
     * @implNote callback invoke when a Permission is denied before multiple times,
     * or set to never ask again. so this time without showing the request this method invokes <br>
     */
    void unableToAskPermission(@NonNull String[] pers, int rCode);

    /**
     * @param rCode the request code of permission set you set
     * @implNote The rational message pop up is cancelled with the request code<br>
     */
    void rationalPopUpCancelled(int rCode);

    /**
     * @param message description and possible solution of error
     * @implNote callback invoke when there found an error
     */
    void onError(String message);
}

