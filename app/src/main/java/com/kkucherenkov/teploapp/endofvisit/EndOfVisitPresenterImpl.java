package com.kkucherenkov.teploapp.endofvisit;

import com.kkucherenkov.teploapp.io.IVisitorsService;
import com.kkucherenkov.teploapp.model.VisitorDetails;
import com.kkucherenkov.teploapp.utils.CostCalculator;

import java.util.Date;
import java.util.Locale;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by kirillkucherenkov on 28/09/2016.
 */

public class EndOfVisitPresenterImpl implements EndOfVisitContract.Presenter {
    private final IVisitorsService visitorsService;
    private VisitorDetails visitorDetails;
    private EndOfVisitContract.EndOfVisitView view;

    public EndOfVisitPresenterImpl(IVisitorsService visitorsService) {
        this.visitorsService = visitorsService;
    }

    @Override
    public void viewEndOfVisitCreated(EndOfVisitContract.EndOfVisitView endOfVisitView, VisitorDetails data) {
        visitorDetails = data;
        visitorDetails.setEndDate(new Date());
        this.view = endOfVisitView;

        view.setVisitorName(data.getFullName());
        view.setVisitorId(data.getVisitorId());
        view.setTotalCost(String.format(Locale.getDefault(), "%d rub", CostCalculator.getTotalCost(data.getStartDate(), data.getEndDate())));
    }

    @Override
    public void viewEndOfVisitDestroyed() {
        this.view = null;
    }

    @Override
    public void okButtonClicked() {
        visitorsService.updateVisitor(visitorDetails)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    if (result) {
                        view.closeFragment();
                    }
                }, (throwable) -> {

                });
    }
}
