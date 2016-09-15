package com.kkucherenkov.teploapp.newvisitor;

import com.kkucherenkov.teploapp.model.BadgeData;
import com.kkucherenkov.teploapp.model.VisitorDetails;

import java.util.Date;

/**
 * Created by Kirill Kucherenkov on 12/09/16.
 */

public interface NewVisitorContract {
    interface View {

        void setName(String fullname);

        void setId(String id);

        void setStartDate(Date startDate);

        void dismiss();
    }

    interface Presenter {
        void viewNewVisitorCreated(View newVisitorView, BadgeData data);

        void viewNewVisitorDestroyed();

        void okButtonClicked(VisitorDetails visitorDetails);
    }
}
