package com.trivia.quizapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {ScoreEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class QuizDb extends RoomDatabase {

    public static final String DATABASE_NAME = "QuizDatabase.db";

    private static volatile QuizDb quizDbInstance;
    private static final Object LOCK = new Object();

    public abstract ScoreDao scoreDao();

    public static QuizDb getInstance(Context context) {
        if (quizDbInstance == null) {
            synchronized (LOCK) {
                if (quizDbInstance == null) {
                    quizDbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            QuizDb.class, DATABASE_NAME).build();
                }
            }
        }
        return quizDbInstance;
    }
}
