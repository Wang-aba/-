package com.example.questsystem.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.questsystem.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    private Context context;
    DbHelper dbHelper;
    public String T_QUESTION = "question";

    public QuestionDao(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }


    public void insertQuestion(Question question) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("question", question.question);
            contentValues.put("keyA", question.keyA);
            contentValues.put("keyB", question.keyB);
            contentValues.put("keyC", question.keyC);
            contentValues.put("keyD", question.keyD);
            contentValues.put("answer", question.answer);
            db.insert(T_QUESTION, null, contentValues);
            db.close();
        }
    }

    public List<Question> queryAll() {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + T_QUESTION + " ORDER BY RANDOM() LIMIT 5", null);
            while (cursor.moveToNext()) {
                Question question = new Question();
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.keyA = cursor.getString(cursor.getColumnIndex("keyA"));
                question.keyB = cursor.getString(cursor.getColumnIndex("keyB"));
                question.keyC = cursor.getString(cursor.getColumnIndex("keyC"));
                question.keyD = cursor.getString(cursor.getColumnIndex("keyD"));
                question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                list.add(question);
            }

            cursor.close();
            db.close();
        }
        return list;
    }
}
