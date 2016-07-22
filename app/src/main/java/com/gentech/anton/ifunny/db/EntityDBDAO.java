package com.gentech.anton.ifunny.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by anton on 22.07.16.
 */
public class EntityDBDAO {
    protected SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context mContext;

    public EntityDBDAO(Context context) {
        this.mContext = context;
        dbHelper = DBHelper.getDBHelper(mContext);
        open();
    }

    public void open() throws SQLException {
        if (dbHelper == null)
            dbHelper = DBHelper.getDBHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database = null;
    }
}
