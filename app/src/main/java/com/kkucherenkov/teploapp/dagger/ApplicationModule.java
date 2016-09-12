package com.kkucherenkov.teploapp.dagger;

import android.app.Application;

import com.google.gson.Gson;
import com.kkucherenkov.teploapp.Data.DBHelper;
import com.kkucherenkov.teploapp.IO.IVisitorsService;
import com.kkucherenkov.teploapp.IO.DBVisitorsService;
import com.kkucherenkov.teploapp.homescreen.HomescreenContract;
import com.kkucherenkov.teploapp.homescreen.HomescreenPresenterImpl;
import com.kkucherenkov.teploapp.homescreen.VisitorsAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

@Module
public class ApplicationModule {
    private final Application application;
    private static final String APP_DATE_FORMAT = "AppDateTime";
    private static final String DB_DATE_FORMAT = "DBDateTime";

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
    HomescreenContract.Presenter provideHomeScreenPresenter(IVisitorsService visitorsService, Gson gson) {
        return new HomescreenPresenterImpl(visitorsService, gson);
    }

    @Provides
    @PerApp
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @PerApp
    @Named(APP_DATE_FORMAT)
    DateFormat providesAppDateFormat() {
        return new SimpleDateFormat("EEE dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @Provides
    @PerApp
    @Named(DB_DATE_FORMAT)
    DateFormat providesDbDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Provides
    @PerApp
    VisitorsAdapter provideVisitorsAdapter(@Named(APP_DATE_FORMAT) DateFormat dateFormat) {
        return new VisitorsAdapter(dateFormat);
    }

    @Provides
    @PerApp
    DBHelper providesDBHelper(@Named(DB_DATE_FORMAT) DateFormat dateFormat) {
        return new DBHelper(application.getApplicationContext(), dateFormat);
    }

    @Provides
    @PerApp
    IVisitorsService providesVisitorsService(DBHelper dbHelper) {
        return new DBVisitorsService(dbHelper);
    }

}
