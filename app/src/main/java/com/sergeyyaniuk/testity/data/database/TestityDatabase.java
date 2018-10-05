package com.sergeyyaniuk.testity.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

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

@android.arch.persistence.room.Database(entities = {User.class, Test.class, Question.class, Answer.class, Result.class}, version = 1)
public abstract class TestityDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract TestDao testDao();

    public abstract QuestionDao questionDao();

    public abstract AnswerDao answerDao();

    public abstract ResultDao resultDao();
}
