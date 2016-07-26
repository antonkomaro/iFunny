package com.gentech.mobile.fun4u.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by anton on 26.07.16.
 */
public class EntityDbDAO {
    protected SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context mContext;

    public EntityDbDAO(Context context) {
        this.mContext = context;
        dbHelper = DBHelper.getHelper(mContext);
        open();
    }

    public void open() throws SQLException {
        if (dbHelper == null)
            dbHelper = DBHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database = null;
    }
}
