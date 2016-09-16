package com.kkucherenkov.teploapp.homescreen;

import com.kkucherenkov.teploapp.model.BadgeData;
import com.kkucherenkov.teploapp.model.VisitorDetails;
import com.kkucherenkov.teploapp.newvisitor.NewVisitorContract;
import com.kkucherenkov.teploapp.newvisitor.NewVisitorFragmentDialog;

import java.util.List;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public interface HomescreenContract {
    interface View {

        void openScanScreen();

        void showNewVisitorScreen(BadgeData badge);

        void showEndOfVisitScreen(VisitorDetails visitorDetails);

        void setVisitors(List<VisitorDetails> visitors);

        void showProgress();

        void hideProgress();
    }

    interface Presenter {

        void viewCreated(View view);

        void viewDestroyed();

        void scanButtonClicked();

        void scanCompleted(String dataString);

        void closeVisitor(VisitorDetails visitorDetails);
    }
}
