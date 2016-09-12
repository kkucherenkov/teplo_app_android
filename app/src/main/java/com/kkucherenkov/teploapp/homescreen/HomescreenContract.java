package com.kkucherenkov.teploapp.homescreen;

import com.kkucherenkov.teploapp.model.BadgeData;
import com.kkucherenkov.teploapp.model.VisitorDetails;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public interface HomescreenContract {
    interface View {

        void openScanScreen();

        void showNewVisitorScreen(BadgeData badge);

        void showEndOfVisitScreen(VisitorDetails visitorDetails);
    }

    interface Presenter {

        void viewCreated(View view);

        void viewDestroyed();

        void scanButtonClicked();

        void scanCompleted(String dataString);
    }
}
