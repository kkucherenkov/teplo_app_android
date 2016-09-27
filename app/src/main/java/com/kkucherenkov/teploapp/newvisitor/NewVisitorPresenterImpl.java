package com.kkucherenkov.teploapp.newvisitor;

import com.kkucherenkov.teploapp.IO.IVisitorsService;
import com.kkucherenkov.teploapp.model.BadgeData;
import com.kkucherenkov.teploapp.model.VisitorDetails;

import java.util.Date;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by kirillkucherenkov on 28/09/2016.
 */

public class NewVisitorPresenterImpl implements NewVisitorContract.Presenter {

    private NewVisitorContract.View newVisitorView;
    private final IVisitorsService visitorsService;

    public NewVisitorPresenterImpl(IVisitorsService visitorsService) {
        this.visitorsService = visitorsService;
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

        visitorsService.addNewVisitor(visitorDetails)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result -> {
                    if (result) {
                        newVisitorView.closeFragment();
//                        loadData();
                    }
                }), (throwable -> {
                }));
    }
}
