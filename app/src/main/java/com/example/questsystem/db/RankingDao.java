package com.example.questsystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.questsystem.RankingBean;

import java.util.ArrayList;
import java.util.List;

public class RankingDao {
    private Context context;
    DbHelper dbHelper;

    public RankingDao(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }


    public void insert(RankingBean rankingBean) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", rankingBean.name);
            contentValues.put("score", rankingBean.score);
            db.insert("ranking", null, contentValues);


            db.close();
        }
    }


    public List<RankingBean> query() {
        List<RankingBean> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query("ranking", null, null, null, null, null, " score desc");
            while (cursor.moveToNext()) {
                RankingBean rankingBean = new RankingBean();
                rankingBean._id = cursor.getString(cursor.getColumnIndex("_id"));
                rankingBean.name = cursor.getString(cursor.getColumnIndex("name"));
                rankingBean.score = cursor.getInt(cursor.getColumnIndex("score"));

                list.add(rankingBean);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public void delete(RankingBean rankingBean) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("name", rankingBean.name);
//            contentValues.put("score", rankingBean.score);
//            db.insert("ranking", null, contentValues);
            db.delete("ranking", " _id = ?", new String[]{rankingBean._id});

            db.close();
        }
    }
}
