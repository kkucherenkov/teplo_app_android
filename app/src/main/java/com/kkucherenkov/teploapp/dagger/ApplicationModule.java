package com.kkucherenkov.teploapp.dagger;

import android.app.Application;

import dagger.Module;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }
}
