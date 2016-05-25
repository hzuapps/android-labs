package com.example.zhenglide.exam;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenglide on 2016/4/4.
 */
public class DBservice {
    private SQLiteDatabase db;

    public DBservice() {
        db = SQLiteDatabase.openDatabase("data/data/com.example.zhenglide.exam/databases/question.db", null, SQLiteDatabase.OPEN_READONLY);
    }

    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<Question>();
        Cursor cursor = db.rawQuery("select * from question", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int count = cursor.getCount();
            for (int i = 0; i < count; i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                question.question = cursor.getString(cursor.getColumnIndex("question"));

                question.AnswerA = cursor.getString(cursor.getColumnIndex("answerA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("answerB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("answerC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("answerD"));
                question.answer = cursor.getInt(cursor.getColumnIndex("answer"));
                question.explaination = cursor.getString(cursor.getColumnIndex("explaination"));
                question.selectedAnswer = -1;
                list.add(question);


            }
        }return  list;
    }
}
