package com.kkucherenkov.teploapp.endofvisit;

import com.kkucherenkov.teploapp.model.VisitorDetails;

/**
 * Created by kirillkucherenkov on 25/09/2016.
 */

public interface EndOfVisitContract {

    interface View {

        void setVisitorName(String visitorName);

        void setVisitorId(String visitorId);

        void setTotalCost(String totalCost);

        void closeFragment();
    }

    interface Presenter {
        void viewEndOfVisitCreated(EndOfVisitContract.View endOfVisitView, VisitorDetails visitorDetails);

        void viewEndOfVisitDestroyed();

        void okButtonClicked();
    }
}
