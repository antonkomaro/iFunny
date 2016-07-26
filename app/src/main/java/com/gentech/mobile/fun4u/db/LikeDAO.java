package com.gentech.mobile.fun4u.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LikeDAO extends EntityDbDAO {

    public static final String TAG = LikeDAO.class.getSimpleName();
    private static final String WHERE_POST_ID_EQUALS = DBHelper.POST_ID_COLUMN + " = ?";


    public LikeDAO(Context context) {
        super(context);
    }

    public void saveAll(List<Like> likes) {

        deleteAll();
        for (Like like: likes) {
            save(like);
        }
        Log.d(TAG, "Likes updated");
    }

    public void deleteAll() {
        String sql = "DELETE FROM " + DBHelper.LIKES_TABLE;
        database.execSQL(sql);
    }

    public List<Like> getAll() {

        List<Like> likes = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.LIKES_TABLE,
                new String[]{
                        DBHelper.POST_ID_COLUMN,
                        DBHelper.IS_LIKED_COLUMN},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Like like = new Like();
            like.setPostId(cursor.getString(0));
            like.setIsLiked(cursor.getInt(1));
            likes.add(like);
        }

        return likes;
    }

    public long save(Like like) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.POST_ID_COLUMN, like.getPostId());
        values.put(DBHelper.IS_LIKED_COLUMN, like.getIsLiked());

        return database.insert(DBHelper.LIKES_TABLE, null, values);
    }

    public Like get(String postId) {

        String sql = "SELECT * FROM " + DBHelper.LIKES_TABLE + " WHERE " + WHERE_POST_ID_EQUALS;
        Cursor cursor = database.rawQuery(sql, new String[]{postId});

        Like like = null;
        if (cursor.moveToNext()) {
            like = new Like();
            like.setPostId(cursor.getString(0));
            like.setIsLiked(cursor.getInt(1));
        }

        cursor.close();

        return like;
    }

    public int delete(Like like) {
        return database.delete(DBHelper.LIKES_TABLE, WHERE_POST_ID_EQUALS,
                new String[]{like.getPostId()});
    }


}
