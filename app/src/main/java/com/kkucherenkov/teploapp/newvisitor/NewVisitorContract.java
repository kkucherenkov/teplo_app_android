package com.kkucherenkov.teploapp.newvisitor;

import com.kkucherenkov.teploapp.model.BadgeData;

import java.util.Date;

/**
 * Created by Kirill Kucherenkov on 12/09/16.
 */

public interface NewVisitorContract {
    interface View {

        void setName(String fullname);

        void setId(String id);

        void setStartDate(Date startDate);
    }

    interface Presenter {
        void viewCreated(View view, BadgeData data);

        void viewDestroyed();
    }
}
