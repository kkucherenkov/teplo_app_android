package com.kkucherenkov.teploapp.homescreen;

import com.google.gson.Gson;
import com.kkucherenkov.teploapp.IO.IVisitorsService;
import com.kkucherenkov.teploapp.model.BadgeData;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public class HomescreenPresenterImpl implements HomescreenContract.Presenter {

    private HomescreenContract.View view;
    private final Gson gson;
    private final IVisitorsService visitorsService;

    public HomescreenPresenterImpl(IVisitorsService visitorsService, Gson gson) {
        this.gson = gson;
        this.visitorsService = visitorsService;
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
        visitorsService.getVisitor(badge.getId())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((visitorDetails -> {
                    if (visitorDetails == null) {
                        view.showNewVisitorScreen(badge);
                    } else {
                        view.showEndOfVisitScreen(visitorDetails);
                    }
                }), (throwable -> {
                }), (() -> {
                }));

    }
}
