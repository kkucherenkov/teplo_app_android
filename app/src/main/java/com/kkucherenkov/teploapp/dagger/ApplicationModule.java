package com.kkucherenkov.teploapp.dagger;

import android.app.Application;

import com.google.gson.Gson;
import com.kkucherenkov.teploapp.homescreen.HomescreenContract;
import com.kkucherenkov.teploapp.homescreen.HomescreenPresenterImpl;
import com.kkucherenkov.teploapp.homescreen.VisitorsAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
    HomescreenContract.Presenter provideHomeScreenPresenter(Gson gson) {
        return new HomescreenPresenterImpl(gson);
    }

    @Provides
    @PerApp
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @PerApp
    DateFormat providesDateFormat() {
        return new SimpleDateFormat("EEE dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @Provides
    @PerApp
    VisitorsAdapter provideVisitorsAdapter(DateFormat dateFormat) {
        return new VisitorsAdapter(dateFormat);
    }

}
