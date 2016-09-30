package com.kkucherenkov.teploapp.homescreen;

import com.google.gson.Gson;
import com.kkucherenkov.teploapp.io.IVisitorsService;
import com.kkucherenkov.teploapp.R;
import com.kkucherenkov.teploapp.model.BadgeData;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public class HomescreenPresenterImpl implements HomescreenContract.Presenter {

    private HomescreenContract.HomeView view;
    private final Gson gson;
    private final IVisitorsService visitorsService;

    public HomescreenPresenterImpl(IVisitorsService visitorsService, Gson gson) {
        this.gson = gson;
        this.visitorsService = visitorsService;
    }

    @Override
    public void viewCreated(HomescreenContract.HomeView view) {
        this.view = view;
        view.setTitle(R.string.app_name);
        loadData();
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

    private void loadData() {
        if (view != null) {
            view.showProgress();

            visitorsService.getActiveVisitors()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnTerminate(() -> view.hideProgress())
                    .subscribe((visitors -> {
                                view.setVisitors(visitors);
                            }),
                            (throwable -> {

                            }));
        }
    }
}
