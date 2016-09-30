package com.kkucherenkov.teploapp;

import android.app.Application;

import com.kkucherenkov.teploapp.dagger.ApplicationComponent;
import com.kkucherenkov.teploapp.dagger.ApplicationModule;
import com.kkucherenkov.teploapp.dagger.DaggerApplicationComponent;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public class TeploApp extends Application {
    protected ApplicationComponent component;

    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
