package com.example.permissiontest;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.wasitech.wpermissions.WCompatActivity;
import com.wasitech.wpermissions.classes.Utils;

import java.util.Arrays;

public class MainActivity extends WCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request().single().storage(1);
        request().multi().location(12);
    }

    @Override
    public void onGrantedAll(@NonNull String[] pers, int rCode) {
        super.onGrantedAll(pers, rCode);
        Utils.Log("per: " + Arrays.toString(pers));
    }

}