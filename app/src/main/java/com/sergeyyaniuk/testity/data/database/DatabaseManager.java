package com.sergeyyaniuk.testity.data.database;

import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.model.User;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class DatabaseManager {

    private final TestityDatabase testityDatabase;

    public DatabaseManager(TestityDatabase testityDatabase) {
        this.testityDatabase = testityDatabase;
    }

    public Observable<Boolean> insertUser(final User user){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.userDao().insert(user);
                return true;
            }
        });
    }

    public Observable<Boolean> insertTest(final Test test){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.testDao().insert(test);
                return true;
            }
        });
    }

    public Observable<Test> getTest(final String testId){
        return Observable.fromCallable(new Callable<Test>() {
            @Override
            public Test call() throws Exception {
                return testityDatabase.testDao().getTestById(testId);
            }
        });
    }

    public Observable<List<Test>> getTestList(String userId){
        return Observable.fromCallable(new Callable<List<Test>>() {
            @Override
            public List<Test> call() throws Exception {
                return testityDatabase.testDao().getTestsByUserId(userId);
            }
        });
    }

    public Observable<Boolean> updateTest(final Test test){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.testDao().update(test);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteTest(final String testId){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.testDao().deleteTestById(testId);
                return true;
            }
        });
    }

    public Observable<Question> getQuestion(final String questionId){
        return Observable.fromCallable(new Callable<Question>() {
            @Override
            public Question call() throws Exception {
                return testityDatabase.questionDao().getQuestionById(questionId);
            }
        });
    }

    public Observable<List<Question>> getQuestionList(final String testId){
        return Observable.fromCallable(new Callable<List<Question>>() {
            @Override
            public List<Question> call() throws Exception {
                return testityDatabase.questionDao().getQuestionsByTestId(testId);
            }
        });
    }

    public Observable<Boolean> insertQuestion(final Question question){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().insert(question);
                return true;
            }
        });
    }

    public Observable<Boolean> updateQuestion(final  Question question){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().update(question);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteQuestion(final String questionId){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().deleteQuestionById(questionId);
                return true;
            }
        });
    }

    public Observable<Boolean> insertAnswerList(final List<Answer> answers){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.answerDao().insertMany(answers);
                return true;
            }
        });
    }

    public Observable<Boolean> updateAnswerList(final List<Answer> answers){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.answerDao().updateMany(answers);
                return true;
            }
        });
    }

    public Observable<List<Answer>> getAnswerList(final String questionId){
        return Observable.fromCallable(new Callable<List<Answer>>() {
            @Override
            public List<Answer> call() throws Exception {
                return testityDatabase.answerDao().getAnswersByQuestionId(questionId);
            }
        });
    }

    public Observable<Boolean> deleteAnswerList(final  String questionId){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.answerDao().deleteAnswersByQuestionId(questionId);
                return true;
            }
        });
    }

    public Observable<Boolean> insertResult(final Result result){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.resultDao().insert(result);
                return true;
            }
        });
    }

    public Observable<List<Result>> getResultList(final String testId){
        return Observable.fromCallable(new Callable<List<Result>>() {
            @Override
            public List<Result> call() throws Exception {
                return testityDatabase.resultDao().getResulstByTest(testId);
            }
        });
    }

    public Observable<Boolean> deleteResult(final String resultId){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.resultDao().deleteById(resultId);
                return true;
            }
        });
    }
}
