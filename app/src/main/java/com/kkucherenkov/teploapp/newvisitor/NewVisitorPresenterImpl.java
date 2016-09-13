package com.kkucherenkov.teploapp.newvisitor;

import com.kkucherenkov.teploapp.model.BadgeData;

import java.util.Date;

/**
 * Created by Kirill Kucherenkov on 12/09/16.
 */

public class NewVisitorPresenterImpl implements NewVisitorContract.Presenter {

    private NewVisitorContract.View view;

    @Override
    public void viewCreated(NewVisitorContract.View view, BadgeData data) {
        this.view = view;
        data.setStartDate(new Date());
        view.setName(data.getFullname());
        view.setId(data.getId());
        view.setStartDate(data.getStartDate());
    }

    @Override
    public void viewDestroyed() {
        this.view = null;
    }
}
