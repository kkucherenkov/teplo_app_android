package com.kkucherenkov.teploapp.homescreen;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */

public class HomescreenPresenterImpl implements HomescreenContract.Presenter {

    private HomescreenContract.View view;


    @Override
    public void viewCreated(HomescreenContract.View view) {
        this.view = view;
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
        
    }
}
