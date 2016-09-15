package com.kkucherenkov.teploapp.IO;

import com.kkucherenkov.teploapp.Data.DBHelper;
import com.kkucherenkov.teploapp.model.VisitorDetails;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * Created by Kirill Kucherenkov on 12/09/16.
 */

public class DBVisitorsService implements IVisitorsService {

    private final DBHelper dbHelper;

    public DBVisitorsService(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Observable<List<VisitorDetails>> getActiveVisitors() {
        return makeObservable(dbHelper.getVisitorsDao().getActiveVisitors())
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public Observable<VisitorDetails> getVisitor(String visitorId) {
        return makeObservable(dbHelper.getVisitorsDao().getVisitor(visitorId))
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public Observable<Boolean> addNewVisitor(VisitorDetails visitorDetails) {
        return makeObservable(dbHelper.getVisitorsDao().addNewVisitor(visitorDetails))
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public Observable<Boolean> updateVisitor(VisitorDetails visitorDetails) {
        return makeObservable(dbHelper.getVisitorsDao().updateVisitor(visitorDetails))
                .subscribeOn(Schedulers.computation());
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                            subscriber.onCompleted();
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                });
    }

}
