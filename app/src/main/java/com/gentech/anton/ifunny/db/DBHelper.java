package com.gentech.anton.ifunny.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anton on 22.07.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fun4u_db";
    private static final int DB_VERSION = 1;

    public static final String LIKE_TABLE = "likeTable";

    public static final String LIKE_ID_COLUMN = "_id";

    public static final String POST_ID_COLUMN = "post_id";
    public static final String IS_LIKED_COLUMN = "is_liked";


    public static final String CREATE_LIKE_TABLE = "CREATE TABLE "
            + LIKE_TABLE + "("
            + LIKE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + POST_ID_COLUMN + " TEXT, "
            + IS_LIKED_COLUMN + " INTEGER);";

    private static DBHelper instance;

    public static synchronized DBHelper getDBHelper(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
