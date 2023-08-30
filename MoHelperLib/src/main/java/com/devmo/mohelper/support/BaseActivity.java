package com.devmo.mohelper.support;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateBaseContextLocale(this);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    private Context updateBaseContextLocale(Context context) {
        new AppStorage(context);
        AppHelper.setLanguage(context, AppStorage.getAppLanguage());
        return context;
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        CustomDialogFragment.dismissDialog();
        LoadingFragment.dismissDialog();
        updateBaseContextLocale(this);

    }
}
