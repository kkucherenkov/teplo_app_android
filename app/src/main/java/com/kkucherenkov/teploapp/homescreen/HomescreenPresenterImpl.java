package com.kkucherenkov.teploapp.homescreen;

import com.google.gson.Gson;
import com.kkucherenkov.teploapp.IO.IVisitorsService;
import com.kkucherenkov.teploapp.model.BadgeData;
import com.kkucherenkov.teploapp.model.VisitorDetails;
import com.kkucherenkov.teploapp.newvisitor.NewVisitorContract;

import java.util.Date;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public class HomescreenPresenterImpl implements HomescreenContract.Presenter, NewVisitorContract.Presenter {

    private HomescreenContract.View view;
    private NewVisitorContract.View newVisitorView;
    private final Gson gson;
    private final IVisitorsService visitorsService;

    public HomescreenPresenterImpl(IVisitorsService visitorsService, Gson gson) {
        this.gson = gson;
        this.visitorsService = visitorsService;
    }

    @Override
    public void viewCreated(HomescreenContract.View view) {
        this.view = view;
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

    @Override
    public void closeVisitor(VisitorDetails visitorDetails) {
        visitorsService.updateVisitor(visitorDetails)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    if (result) {
                        loadData();
                    }
                }, (throwable) -> {

                });
    }

    @Override
    public void viewNewVisitorCreated(NewVisitorContract.View view, BadgeData data) {
        this.newVisitorView = view;
        data.setStartDate(new Date());
        newVisitorView.setName(data.getFullname());
        newVisitorView.setId(data.getId());
        newVisitorView.setStartDate(data.getStartDate());
    }

    @Override
    public void viewNewVisitorDestroyed() {
        newVisitorView = null;
    }

    @Override
    public void okButtonClicked(VisitorDetails visitorDetails) {
        newVisitorView.dismiss();
        visitorsService.addNewVisitor(visitorDetails)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result -> {
                    if (result) {
                        loadData();
                    }
                }), (throwable -> {
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
