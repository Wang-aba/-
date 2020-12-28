package com.example.questsystem.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "data.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String questionSql = "CREATE TABLE IF NOT EXISTS question (_id integer primary key autoincrement  NOT NULL," +
                "question varchar," +
                "keyA varchar,keyB varchar,keyC varchar,keyD varchar,answer integer)";
        String user = "CREATE TABLE IF NOT EXISTS ranking (_id integer primary key autoincrement  NOT NULL,name varchar,score integer)";
        db.execSQL(questionSql);
        db.execSQL(user);
        //2张表创建成功
        Log.v("Table", "2张表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}