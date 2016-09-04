package com.kkucherenkov.teploapp.homescreen;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public interface HomescreenContract {
    interface View {

        void openScanScreen();
    }

    interface Presenter {

        void viewCreated(View view);

        void viewDestroyed();

        void scanButtonClicked();
    }
}
