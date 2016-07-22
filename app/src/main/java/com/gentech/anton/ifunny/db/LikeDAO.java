package com.gentech.anton.ifunny.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.gentech.anton.ifunny.models.Like;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 22.07.16.
 */
public class LikeDAO extends EntityDBDAO {
    public static final String TAG = LikeDAO.class.getSimpleName();
    private static final String WHERE_ID_EQUALS = DBHelper.LIKE_ID_COLUMN + " = ?";

    public LikeDAO(Context context) {
        super(context);
    }

    public void saveAll(List<Like> likes) {

        deleteAll();
        for (Like like : likes) {
            save(like);
        }
        Log.d(TAG, "Likes updated");
    }

    public void deleteAll() {
        String sql = "DELETE FROM " + DBHelper.LIKE_TABLE;
        database.execSQL(sql);
    }

    public List<Like> getAll() {

        List<Like> likes = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.LIKE_TABLE,
                new String[]{DBHelper.LIKE_ID_COLUMN,
                        DBHelper.POST_ID_COLUMN,
                        DBHelper.IS_LIKED_COLUMN},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Like like = new Like();
            like.setId(cursor.getInt(0));
            like.setPostId(cursor.getString(1));
            like.setIsLiked(cursor.getInt(2));
            likes.add(like);
        }

        return likes;
    }

    public long save(Like like) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.LIKE_ID_COLUMN, like.getId());
        values.put(DBHelper.POST_ID_COLUMN, like.getPostId());
        values.put(DBHelper.IS_LIKED_COLUMN, like.getIsLiked());

        return database.insert(DBHelper.LIKE_TABLE, null, values);
    }

    public Like get(int id) {

        Like like = null;
        String sql = "SELECT * FROM " + DBHelper.LIKE_TABLE + " WHERE " + WHERE_ID_EQUALS;
        Cursor cursor = database.rawQuery(sql, new String[]{id + ""});

        if (cursor.moveToNext()) {
            like = new Like();
            like.setId(cursor.getInt(0));
            like.setPostId(cursor.getString(1));
            like.setIsLiked(cursor.getInt(2));
        }

        return like;
    }

    public int delete(Like like) {
        return database.delete(DBHelper.LIKE_TABLE, WHERE_ID_EQUALS,
                new String[]{like.getId() + ""});
    }

}
