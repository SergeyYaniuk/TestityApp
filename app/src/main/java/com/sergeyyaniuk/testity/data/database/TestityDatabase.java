package com.sergeyyaniuk.testity.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.sergeyyaniuk.testity.data.database.dao.AnswerDao;
import com.sergeyyaniuk.testity.data.database.dao.QuestionDao;
import com.sergeyyaniuk.testity.data.database.dao.ResultDao;
import com.sergeyyaniuk.testity.data.database.dao.TestDao;
import com.sergeyyaniuk.testity.data.database.dao.UserDao;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.model.User;

@android.arch.persistence.room.Database(entities = {User.class, Test.class, Question.class, Answer.class, Result.class}, version = 7)
public abstract class TestityDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract TestDao testDao();

    public abstract QuestionDao questionDao();

    public abstract AnswerDao answerDao();

    public abstract ResultDao resultDao();

    public static final Migration MIGRATION_6_7 = new Migration(6,7) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tests ADD COLUMN date TEXT");
            database.execSQL("ALTER TABLE results ADD COLUMN user_id TEXT");
            database.execSQL("ALTER TABLE results ADD COLUMN user_name TEXT");
            database.execSQL("ALTER TABLE results ADD COLUMN date TEXT");
        }
    };

    public static final Migration MIGRATION_7_8 = new Migration(7,8) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE results ADD COLUMN test_name TEXT");
            database.execSQL("ALTER TABLE results ADD COLUMN time_spent INTEGER");
        }
    };
}
