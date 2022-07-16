package com.wasitech.wpermissions.classes;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.wasitech.wpermissions.inter.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class WRequest extends Utils implements RequestListener {
    private final Single s;
    private final Multi m;

    protected WRequest(Activity ac) {
        super(ac);
        s = new Single();
        m = new Multi();
    }

    /**
     * @author Wasi-Ibn-Adam
     */
    public static class Check {
        public static boolean custom(Context context, String per) {
            return context.checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED;
        }

        public static boolean custom(Context context, String[] pers) {
            boolean b;
            for (String per : pers) {
                b = custom(context, per);
                if (!b) {
                    return false;
                }
            }
            return true;
        }

        public static boolean location(Context context) {
            return custom(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
        }

        public static boolean location_Coarse(Context context) {
            return custom(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        public static boolean location_fine(Context context) {
            return custom(context, Manifest.permission.ACCESS_FINE_LOCATION);
        }

        public static boolean storage(Context context) {
            return custom(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        public static boolean storage_read(Context context) {
            return custom(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        public static boolean contact(Context context) {
            return custom(context, Manifest.permission.WRITE_CONTACTS);
        }

        public static boolean contact_read(Context context) {
            return custom(context, Manifest.permission.READ_CONTACTS);
        }

        public static boolean camera(Context context) {
            return custom(context, Manifest.permission.CAMERA);
        }

        public static boolean microphone(Context context) {
            return custom(context, Manifest.permission.RECORD_AUDIO);
        }

        public static boolean phone(Context context) {
            return custom(context, Manifest.permission.CALL_PHONE);
        }

        public static boolean send_sms(Context context) {
            return custom(context, Manifest.permission.SEND_SMS);
        }



    }

    /**
     * @author Wasi-Ibn-Adam
     * @implNote When only signle permission should be requested
     */
    public Single single() {
        return s;
    }

    /**
     * @author Wasi-Ibn-Adam
     * @implNote When multiple permissions should be requested
     */
    public Multi multi() {
        return m;
    }

    /**
     * saves the curr value<br>
     * returns true if permission should be checked else false
     */
    private boolean checkBeforeRequesting(String per) {
        // SAVE PREVIOUS VALUES
        int b = ac.checkSelfPermission(per);
        setValue(per, b);
        return b != PackageManager.PERMISSION_GRANTED;
    }

    protected void showRational(@NonNull String[] per, int rCode) {
        AlertDialog dialog = new AlertDialog.Builder(ac).create();
        dialog.setMessage("Grant following permission: " + setRationalMessage(per, rCode));
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", (v, a) -> ac.requestPermissions(per, rCode));
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (v, a) -> {
            dialog.dismiss();
            rationalPopUpCancelled(rCode);
        });
        dialog.show();
    }

    protected void setRationalValue(String tag, boolean b) {
        setValue("rat_" + tag, b);
    }

    protected boolean getRationalValue(String tag) {
        return getValue("rat_" + tag, false);
    }

    protected boolean hasPermission(String permission) {
        try {
            PackageInfo info = ac.getPackageManager().getPackageInfo(ac.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class Extras {

        /**
         * <h3>Convert permission to its name </h3>
         * <p>
         * for example
         *
         * @param per "Manifest.permission.ACCESS_COARSE_LOCATION"
         * @return "Location"
         */
        public static String getPermissionName(String per) {
            switch (per) {
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                case Manifest.permission.MANAGE_EXTERNAL_STORAGE: {
                    return "Storage";
                }
                case Manifest.permission.READ_CONTACTS:
                case Manifest.permission.WRITE_CONTACTS:
                case Manifest.permission.GET_ACCOUNTS: {
                    return "Contact";
                }
                case Manifest.permission.RECORD_AUDIO: {
                    return "Microphone";
                }
                case Manifest.permission.CAMERA: {
                    return "Camera";
                }
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                case Manifest.permission.ACCESS_FINE_LOCATION: {
                    return "Location";
                }
                case Manifest.permission.READ_SMS:
                case Manifest.permission.BROADCAST_SMS:
                case Manifest.permission.RECEIVE_SMS:
                case Manifest.permission.SEND_SMS: {
                    return "Sms";
                }
                case Manifest.permission.CALL_PHONE:
                case Manifest.permission.ANSWER_PHONE_CALLS:
                case Manifest.permission.READ_PHONE_NUMBERS:
                case Manifest.permission.READ_PRECISE_PHONE_STATE:
                case Manifest.permission.MODIFY_PHONE_STATE: {
                    return "Phone";
                }
                case Manifest.permission.BIND_VPN_SERVICE: {
                    return "VPN";
                }
                default:
                    return "";
            }
        }

        /**
         * <h3>Convert list of permission to list of their names </h3>
         * <p>
         * for example
         *
         * @param pers [
         *             "Manifest.permission.ACCESS_COARSE_LOCATION" ,
         *             "Manifest.permission.READ_CONTACTS" ]
         * @return ["Location" , "Contact"]
         */
        public static String[] getPermissionsName(String[] pers) {
            HashMap<String, Integer> list = new HashMap<>();
            for (String per : pers) {
                String d = getPermissionName(per);
                if (!d.isEmpty())
                    list.put(d, 1);
            }
            return ((String[]) list.keySet().toArray());
        }

        /**
         * <h3>Convert list of permission to String of their names </h3>
         * <p>
         * for example
         *
         * @param pers [
         *             "Manifest.permission.ACCESS_COARSE_LOCATION" ,
         *             "Manifest.permission.READ_CONTACTS" ]
         * @return "Location and Contact"
         */
        public static String permissionsToString(String[] pers) {
            String[] list = getPermissionsName(pers);
            switch (list.length) {
                case 0:
                    return "";
                case 1:
                    return list[0];
                case 2:
                    return list[0] + ", " + list[1];
                default: {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < list.length; i++) {
                        if (i == list.length - 1)
                            builder.append("& ").append(builder.append(list[i]));
                        else
                            builder.append(list[i]).append(", ");
                    }
                    return builder.toString();
                }
            }
        }

    }

    public class Single {
        /**
         * <h3> Add Permission in Manifest which you are requesting</h3>
         */
        private void permission(String per, int code) {
            if (!hasPermission(per)) {
                onError("Add permission in your manifest file first: " + per);
                return;
            }

            if (checkBeforeRequesting(per)) {
                if (isNew(per)) { // 1st Call
                    ac.requestPermissions(new String[]{per}, code);
                } else {
                    boolean curr = ac.shouldShowRequestPermissionRationale(per);  // for the sake of never ask again
                    //boolean prev = getRationalValue(per);
                    if (curr) {
                        showRational(new String[]{per}, code);
                    } else {
                        unableToAskPermission(new String[]{per}, code);
                    }
                    setRationalValue(per, curr);
                }
            } else {
                ac.requestPermissions(new String[]{per}, code);
            }
        }

        /**
         * <h3> Add Permission in Manifest file, which you are requesting</h3>
         */
        public void custom(@NonNull String per, int rCode) {
            permission(per, rCode);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.CAMERA" </i>
         */
        public void camera(int code) {
            permission(Manifest.permission.CAMERA, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.RECORD_AUDIO" </i>
         */
        public void microphone(int code) {
            permission(Manifest.permission.RECORD_AUDIO, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" </i>
         */
        public void storage_read(int code) {
            permission(Manifest.permission.READ_EXTERNAL_STORAGE, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" </i>
         */
        public void storage_write(int code) {
            permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, code);
        }

        /**
         * <h3>Add following permissions in Manifest</h3>
         * <i>uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" </i>
         * <i>uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" </i>
         */
        public void storage(int code) {
            permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.READ_CONTACTS" </i>
         */
        public void contact_read(int code) {
            permission(Manifest.permission.READ_CONTACTS, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.WRITE_CONTACTS" </i>
         */
        public void contact_write(int code) {
            permission(Manifest.permission.WRITE_CONTACTS, code);
        }

        /**
         * <h3>Add following permissions in Manifest</h3>
         * <i>uses-permission android:name="android.permission.READ_CONTACTS" </i>
         * <i>uses-permission android:name="android.permission.WRITE_CONTACTS" </i>
         */
        public void contact(int code) {
            permission(Manifest.permission.WRITE_CONTACTS, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.CALL_PHONE" </i>
         */
        public void call(int code) {
            permission(Manifest.permission.CALL_PHONE, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.READ_CALL_LOG" </i>
         */
        public void call_log_read(int code) {
            permission(Manifest.permission.READ_CALL_LOG, code);
        }


        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.WRITE_CALL_LOG" </i>
         */
        public void call_log_write(int code) {
            permission(Manifest.permission.WRITE_CALL_LOG, code);
        }

    }

    public class Multi {
        /**
         * <h3> Add Permissions in Manifest which you are requesting</h3>
         */
        private void permissions(@NonNull String[] pers, int rCode) {

            ArrayList<String> arr = new ArrayList<>();
            boolean showRat = false;
            boolean err = false;
            ArrayList<String> error = new ArrayList<>();

            for (String per : pers) {
                if (!hasPermission(per)) {
                    err = true;
                    error.add(per);
                    continue;
                }
                if (checkBeforeRequesting(per)) {
                    if (!isNew(per)) {
                        boolean curr = ac.shouldShowRequestPermissionRationale(per);  // for the sake of never ask again
                        if (curr) {
                            showRat = true;
                        } else {
                            arr.add(per);
                        }
                        setRationalValue(per, curr);
                    }
                }
            }
            if (err) {
                onError("Add following in your manifest file first: " + error.toString());
                return;
            }

            if (arr.size() == 0) {
                if (showRat) {
                    showRational(pers, rCode);
                } else {
                    ac.requestPermissions(pers, rCode);
                }
            } else {
                unableToAskPermission(arr.toArray(new String[]{""}), rCode);
            }
        }

        /**
         * <h3> Add All Permissions in Manifest file, which you are requesting</h3>
         */
        public void custom(@NonNull String[] pers, int rCode) {
            permissions(pers, rCode);
        }

        /**
         * <h3>Add following permissions in Manifest</h3>
         * <i>uses-permission android:name="android.permission.CAMERA" </i>
         * <i>uses-permission android:name="android.permission.RECORD_AUDIO" </i>
         */
        public void cameraApp(int code) {
            permissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" </i>
         * <i>uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" </i>
         */
        public void location(int code) {
            permissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, code);
        }

        /**
         * <h3>Add following permissions in Manifest</h3>
         * <i>uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" </i>
         * <i>uses-permission android:name="android.permission.RECORD_AUDIO" </i>
         */
        public void recorderApp(int code) {
            permissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, code);
        }

        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.RECORD_AUDIO" </i>
         */
        public void audioDetector(int code) {
            permissions(new String[]{Manifest.permission.RECORD_AUDIO}, code);
        }


        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" </i>
         */
        public void playerApp(int code) {
            permissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);
        }


        /**
         * <h3>Add following permission in Manifest</h3>
         * <i>uses-permission android:name="android.permission.WRITE_CONTACTS" </i>
         */
        public void contactApp(int code) {
            permissions(new String[]{Manifest.permission.WRITE_CONTACTS}, code);
        }


    }

}
