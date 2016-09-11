package com.kkucherenkov.teploapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kirill Kucherenkov on 11/09/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "teplo_app.db";

    private static final int DATABASE_VERSION = 1;

    private static final String ID_COLUMN = "id";
    private static final String VISITOR_ID_COLUMN = "visitor_id";
    private static final String NAME_COLUMN = "name";
    private static final String START_TIME_COLUMN = "start_time";
    private static final String END_TIME_COLUMN = "end_time";
    private static final String STOP_CHECK_COLUMN = "stop_check";
    private static final String STOP_TIME_COLUMN = "stop_time";
    private static final String VISITORS_TABLE_NAME = "visitors";

    private static final String CREATE_VISITORS_TABLE = "CREATE TABLE " + VISITORS_TABLE_NAME + " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VISITOR_ID_COLUMN + " TEXT, "
            + NAME_COLUMN + " TEXT, "
            + START_TIME_COLUMN + " DATETIME, "
            + END_TIME_COLUMN + " DATETIME, "
            + STOP_CHECK_COLUMN + " INTEGER, "
            + STOP_TIME_COLUMN + " INTEGER" + ");";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_VISITORS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
