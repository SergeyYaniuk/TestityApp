package com.sergeyyaniuk.testity.data.database;

import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.model.User;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

public class DatabaseManager {

    private final TestityDatabase testityDatabase;

    public DatabaseManager(TestityDatabase testityDatabase) {
        this.testityDatabase = testityDatabase;
    }

    /** _________________USER METHODS_____________________*/

    public Observable<Boolean> insertUser(final User user){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.userDao().insert(user);
                return true;
            }
        });
    }

    public Observable<Boolean> updateUser(final User user){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.userDao().update(user);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteUser(final User user){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.userDao().delete(user);
                return true;
            }
        });
    }

    public Observable<User> getUserById(final long userId){
        return Observable.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return testityDatabase.userDao().getUserById(userId);
            }
        });
    }

    /** _________________TEST METHODS_____________________*/

    public Observable<Boolean> insertTest(final Test test){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.testDao().insert(test);
                return true;
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

    public Observable<Boolean> deleteTest(final Test test){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.testDao().delete(test);
                return true;
            }
        });
    }

    public Observable<List<Test>> getTestsByUserId(final long userId){
        return Observable.fromCallable(new Callable<List<Test>>() {
            @Override
            public List<Test> call() throws Exception {
                return testityDatabase.testDao().getTestsByUserId(userId);
            }
        });
    }

    public Observable<Test> getTestById(final long testId){
        return Observable.fromCallable(new Callable<Test>() {
            @Override
            public Test call() throws Exception {
                return testityDatabase.testDao().getTestById(testId);
            }
        });
    }

    /** _________________QUESTION METHODS_____________________*/

    public Observable<Boolean> insertQuestion(final Question question){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().insert(question);
                return true;
            }
        });
    }

    public Observable<Boolean> updateQuestion(final Question question){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().update(question);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteQuestion(final Question question){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().delete(question);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteManyQuestions(final List<Question> questions){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.questionDao().deleteMany(questions);
                return true;
            }
        });
    }

    public Observable<Question> getQuestionById(final long questionId){
        return Observable.fromCallable(new Callable<Question>() {
            @Override
            public Question call() throws Exception {
                return testityDatabase.questionDao().getQuestionById(questionId);
            }
        });
    }

    public Observable<List<Question>> getQuestionsSorted(final long testId){
        return Observable.fromCallable(new Callable<List<Question>>() {
            @Override
            public List<Question> call() throws Exception {
                return testityDatabase.questionDao().getQuestionsSorted(testId);
            }
        });
    }

    /** _________________ANSWER METHODS_____________________*/

    public Observable<Boolean> insertManyAnswers(final List<Answer> answers){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.answerDao().insertMany(answers);
                return true;
            }
        });
    }

    public Observable<Boolean> updateManyAnswers(final List<Answer> answers){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.answerDao().updateMany(answers);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteManyAnswers(final List<Answer> answers){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.answerDao().deleteMany(answers);
                return true;
            }
        });
    }

    public Observable<List<Answer>> getAnswersByQuestionId(final long questionId){
        return Observable.fromCallable(new Callable<List<Answer>>() {
            @Override
            public List<Answer> call() throws Exception {
                return testityDatabase.answerDao().getAnswersByQuestionId(questionId);
            }
        });
    }

    /** _________________RESULT METHODS_____________________*/

    public Observable<Boolean> insertResult(final Result result){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.resultDao().insert(result);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteResult(final Result result){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.resultDao().delete(result);
                return true;
            }
        });
    }

    public Observable<Boolean> deleteManyResults(final List<Result> results){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                testityDatabase.resultDao().deleteMany(results);
                return true;
            }
        });
    }

    public Observable<List<Result>> getResultsByTestId(final long testId){
        return Observable.fromCallable(new Callable<List<Result>>() {
            @Override
            public List<Result> call() throws Exception {
                return testityDatabase.resultDao().getResulstByTest(testId);
            }
        });
    }
}
