package com.kkucherenkov.teploapp.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kkucherenkov.teploapp.model.VisitorDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Kirill Kucherenkov on 11/09/16.
 */

public class VisitorsDAO {

    private final DBHelper dbHelper;
    private final SimpleDateFormat dateFormat;

    VisitorsDAO(DBHelper dbHelper, SimpleDateFormat dateFormat) {
        this.dbHelper = dbHelper;
        this.dateFormat = dateFormat;
    }

    public Callable<List<VisitorDetails>> getActiveVisitors() {
        return () -> {
            ArrayList<VisitorDetails> visitors = new ArrayList<>();
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(DBHelper.VISITORS_TABLE_NAME,
                    new String[]{
                            DBHelper.ID_COLUMN,
                            DBHelper.NAME_COLUMN,
                            DBHelper.VISITOR_ID_COLUMN,
                            DBHelper.START_TIME_COLUMN,
                            DBHelper.END_TIME_COLUMN,
                            DBHelper.STOP_CHECK_COLUMN,
                            DBHelper.STOP_TIME_COLUMN},
                    DBHelper.END_TIME_COLUMN + "=\"\" or " + DBHelper.END_TIME_COLUMN + " is null",
                    new String[]{}, null, null, DBHelper.START_TIME_COLUMN);

            if (cursor.moveToFirst()) {
                do {
                    VisitorDetails visitorDetails = new VisitorDetails();
                    visitorDetails.setId(cursor.getInt(0));
                    visitorDetails.setVisitorId(cursor.getString(1));
                    visitorDetails.setFullName(cursor.getString(2));
                    visitorDetails.setStartDate(dateFormat.parse(cursor.getString(3)));
                    visitorDetails.setStopCheck(cursor.getInt(5));
                    visitorDetails.setStopTime(cursor.getInt(6));
                    visitors.add(visitorDetails);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return visitors;
        };
    }

    public Callable<VisitorDetails> getVisitor(String visitorID) {
        return () -> {
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            Cursor cursor = database.query(DBHelper.VISITORS_TABLE_NAME,
                    new String[]{
                            DBHelper.ID_COLUMN,
                            DBHelper.NAME_COLUMN,
                            DBHelper.VISITOR_ID_COLUMN,
                            DBHelper.START_TIME_COLUMN,
                            DBHelper.END_TIME_COLUMN,
                            DBHelper.STOP_CHECK_COLUMN,
                            DBHelper.STOP_TIME_COLUMN},
                    "(" + DBHelper.END_TIME_COLUMN + "=\"\" OR " + DBHelper.END_TIME_COLUMN + " is null ) AND " + DBHelper.VISITOR_ID_COLUMN + " = ?",
                    new String[]{visitorID}, null, null, DBHelper.START_TIME_COLUMN);

            VisitorDetails visitorDetails = null;
            if (cursor.moveToFirst()) {
                visitorDetails = new VisitorDetails();
                visitorDetails.setId(cursor.getInt(0));
                visitorDetails.setFullName(cursor.getString(1));
                visitorDetails.setVisitorId(cursor.getString(2));
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
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.NAME_COLUMN, visitorDetails.getFullName());
            values.put(DBHelper.VISITOR_ID_COLUMN, visitorDetails.getVisitorId());
            values.put(DBHelper.START_TIME_COLUMN, dateFormat.format(visitorDetails.getStartDate()));
            values.put(DBHelper.END_TIME_COLUMN, "");
            values.put(DBHelper.STOP_CHECK_COLUMN, visitorDetails.getStopCheck());
            values.put(DBHelper.STOP_TIME_COLUMN, visitorDetails.getStopTime());

            database.beginTransaction();
            try {
                long res = database.insert(DBHelper.VISITORS_TABLE_NAME, DBHelper.END_TIME_COLUMN, values);
                result = res > -1;
                if (result) {
                    database.setTransactionSuccessful();
                }
            } finally {
                database.endTransaction();
                database.close();
            }
            return result;
        };
    }

    public Callable<Boolean> updateVisitor(VisitorDetails visitorDetails) {
        return () -> {
            boolean result = false;
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.END_TIME_COLUMN, dateFormat.format(visitorDetails.getEndDate()));
            database.beginTransaction();
            try {
                result = database.update(
                        DBHelper.VISITORS_TABLE_NAME,
                        values,
                        DBHelper.VISITOR_ID_COLUMN + " = ? and (" + DBHelper.END_TIME_COLUMN + "=\"\" OR " + DBHelper.END_TIME_COLUMN + " is null )",
                        new String[]{visitorDetails.getVisitorId()}) > 0;
                if (result) {
                    database.setTransactionSuccessful();
                }
            } finally {
                database.endTransaction();
                database.close();
            }
            return result;
        };
    }
}
