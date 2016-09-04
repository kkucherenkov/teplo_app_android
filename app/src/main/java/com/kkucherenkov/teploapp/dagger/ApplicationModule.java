package com.kkucherenkov.teploapp.dagger;

import android.app.Application;

import com.kkucherenkov.teploapp.homescreen.HomescreenContract;
import com.kkucherenkov.teploapp.homescreen.HomescreenPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @PerApp
    Application provideApplication() {
        return application;
    }

    @Provides
    @PerApp
    HomescreenContract.Presenter provideHomeScreenPresenter() {
        return new HomescreenPresenterImpl();
    }
}
