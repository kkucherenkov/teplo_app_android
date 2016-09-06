package com.kkucherenkov.teploapp.homescreen;

import com.kkucherenkov.teploapp.model.BadgeData;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public interface HomescreenContract {
    interface View {

        void openScanScreen();

        void updateVisitors(BadgeData badge);
    }

    interface Presenter {

        void viewCreated(View view);

        void viewDestroyed();

        void scanButtonClicked();

        void scanCompleted(String dataString);
    }
}
