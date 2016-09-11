package com.kkucherenkov.teploapp.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kkucherenkov.teploapp.model.VisitorDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Kirill Kucherenkov on 11/09/16.
 */

public class VisitorsDAO {

    private final DBHelper dbHelper;
    private final SimpleDateFormat dateFormat;

    public VisitorsDAO(DBHelper dbHelper, SimpleDateFormat dateFormat) {
        this.dbHelper = dbHelper;
        this.dateFormat = dateFormat;
    }

    public Callable<List<VisitorDetails>> getActiveVisitors() {
        return () -> {
            ArrayList<VisitorDetails> visitors = new ArrayList<>();
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(DBHelper.VISITORS_TABLE_NAME,
                    new String[]{DBHelper.ID_COLUMN,
                            DBHelper.VISITOR_ID_COLUMN,
                            DBHelper.NAME_COLUMN,
                            DBHelper.START_TIME_COLUMN,
                            DBHelper.END_TIME_COLUMN,
                            DBHelper.STOP_CHECK_COLUMN,
                            DBHelper.STOP_TIME_COLUMN
                    },
                    DBHelper.END_TIME_COLUMN + " is null or " + DBHelper.END_TIME_COLUMN + " = ?",
                    new String[]{""},
                    null,
                    null,
                    DBHelper.START_TIME_COLUMN);

            while (cursor.moveToNext()) {
                VisitorDetails visitorDetails = new VisitorDetails();
                visitorDetails.setId(cursor.getInt(0));
                visitorDetails.setVisitorId(cursor.getString(1));
                visitorDetails.setFullName(cursor.getString(2));
                visitorDetails.setStartDate(dateFormat.parse(cursor.getString(3)));
                visitorDetails.setStopCheck(cursor.getInt(5));
                visitorDetails.setStopTime(cursor.getInt(6));
                visitors.add(visitorDetails);
            }
            cursor.close();
            return visitors;
        };
    }

    public Callable<VisitorDetails> getVisitor(String visitorID) {
        return () -> {
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(DBHelper.VISITORS_TABLE_NAME,
                    new String[]{DBHelper.ID_COLUMN,
                            DBHelper.VISITOR_ID_COLUMN,
                            DBHelper.NAME_COLUMN,
                            DBHelper.START_TIME_COLUMN,
                            DBHelper.END_TIME_COLUMN,
                            DBHelper.STOP_CHECK_COLUMN,
                            DBHelper.STOP_TIME_COLUMN
                    },
                    DBHelper.VISITOR_ID_COLUMN + " = ?", new String[]{"visitorID"},
                    null, null, null);

            VisitorDetails visitorDetails = null;
            if (cursor.moveToFirst()) {
                visitorDetails = new VisitorDetails();
                visitorDetails.setId(cursor.getInt(0));
                visitorDetails.setVisitorId(cursor.getString(1));
                visitorDetails.setFullName(cursor.getString(2));
                visitorDetails.setStartDate(dateFormat.parse(cursor.getString(3)));
                visitorDetails.setStopCheck(cursor.getInt(5));
                visitorDetails.setStopTime(cursor.getInt(6));
            }
            cursor.close();
            return visitorDetails;
        };
    }

    public Callable<Boolean> addNewVisitor(VisitorDetails visitorDetails) {
        return () -> {
            boolean result = false;
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.NAME_COLUMN, visitorDetails.getFullName());
            values.put(DBHelper.VISITOR_ID_COLUMN, visitorDetails.getVisitorId());
            values.put(DBHelper.START_TIME_COLUMN, dateFormat.format(visitorDetails.getStartDate()));
            values.put(DBHelper.STOP_CHECK_COLUMN, visitorDetails.getStopCheck());
            values.put(DBHelper.START_TIME_COLUMN, visitorDetails.getStopTime());
            database.beginTransaction();
            result = database.insert(DBHelper.VISITORS_TABLE_NAME, null, values) != -1;
            database.endTransaction();
            return result;
        };
    }

    public Callable<Boolean> updateVisitor(VisitorDetails visitorDetails) {
        return () -> {
            boolean result = false;
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.NAME_COLUMN, visitorDetails.getFullName());
            values.put(DBHelper.VISITOR_ID_COLUMN, visitorDetails.getVisitorId());
            values.put(DBHelper.START_TIME_COLUMN, dateFormat.format(visitorDetails.getStartDate()));
            values.put(DBHelper.END_TIME_COLUMN, dateFormat.format(visitorDetails.getEndDate()));
            values.put(DBHelper.STOP_CHECK_COLUMN, visitorDetails.getStopCheck());
            values.put(DBHelper.START_TIME_COLUMN, visitorDetails.getStopTime());
            database.beginTransaction();
            result = database.update(
                    DBHelper.VISITORS_TABLE_NAME,
                    values,
                    DBHelper.VISITOR_ID_COLUMN + " = ? and " + DBHelper.START_TIME_COLUMN + " = ?",
                    new String[]{visitorDetails.getVisitorId(), dateFormat.format(visitorDetails.getStartDate())}) > 0;
            database.endTransaction();
            return result;
        };
    }

    public static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch (Exception ex) {
                            subscriber.onError(ex);
                        }
                    }
                });
    }


}
