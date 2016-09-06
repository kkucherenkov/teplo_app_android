package com.kkucherenkov.teploapp.homescreen;

import com.google.gson.Gson;
import com.kkucherenkov.teploapp.model.BadgeData;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public class HomescreenPresenterImpl implements HomescreenContract.Presenter {

    private HomescreenContract.View view;
    private final Gson gson;

    public HomescreenPresenterImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void viewCreated(HomescreenContract.View view) {
        this.view = view;
    }

    @Override
    public void viewDestroyed() {
        view = null;
    }

    @Override
    public void scanButtonClicked() {
        view.openScanScreen();
    }

    @Override
    public void scanCompleted(String dataString) {
        BadgeData badge = gson.fromJson(dataString, BadgeData.class);
        view.updateVisitors(badge);
    }
}
