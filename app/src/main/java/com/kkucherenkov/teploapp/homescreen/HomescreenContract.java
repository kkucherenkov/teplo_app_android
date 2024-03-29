package com.kkucherenkov.teploapp.homescreen;

import com.kkucherenkov.teploapp.model.BadgeData;
import com.kkucherenkov.teploapp.model.VisitorDetails;

import java.util.List;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public interface HomescreenContract {
    interface HomeView {

        void openScanScreen();

        void showNewVisitorScreen(BadgeData badge);

        void showEndOfVisitScreen(VisitorDetails visitorDetails);

        void setVisitors(List<VisitorDetails> visitors);

        void showProgress();

        void hideProgress();

        void setTitle(int titleId);
    }

    interface Presenter {

        void viewCreated(HomeView view);

        void viewDestroyed();

        void scanButtonClicked();

        void scanCompleted(String dataString);

    }
}
