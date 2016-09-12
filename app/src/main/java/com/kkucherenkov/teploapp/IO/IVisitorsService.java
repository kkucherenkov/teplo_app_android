package com.kkucherenkov.teploapp.IO;

import com.kkucherenkov.teploapp.model.VisitorDetails;

import java.util.List;

import rx.Observable;

/**
 * Created by Kirill Kucherenkov on 12/09/16.
 */
public interface IVisitorsService {
    Observable<List<VisitorDetails>> getActiveVisitors();

    Observable<VisitorDetails> getVisitor(String visitorId);

    Observable<Boolean> addNewVisitor(VisitorDetails visitorDetails);

    Observable<Boolean> updateVisitor(VisitorDetails visitorDetails);
}
