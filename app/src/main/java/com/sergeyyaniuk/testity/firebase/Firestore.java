package com.sergeyyaniuk.testity.firebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.model.User;
import com.sergeyyaniuk.testity.ui.find.findList.Filters;

import java.util.ArrayList;
import java.util.List;

public class Firestore {

    private static final String TAG = "MyLog";

    public static final String USERS = "users";
    public static final String TESTS = "tests";
    public static final String QUESTIONS = "questions";
    public static final String ANSWERS = "answers";
    public static final String RESULTS = "results";

    private static final int LIMIT = 50;

    FirebaseFirestore db;

    public Firestore() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(User user){
        db.collection(USERS).document(user.getId()).set(user);
    }

    public Task<Void> addTest(Test test){
        return db.collection(TESTS).document(test.getId()).set(test);
    }

    public Task<Void> updateTestEditFive(Test test){
        WriteBatch batch = db.batch();
        batch.update(db.collection(TESTS).document(test.getId()), "title", test.getTitle());
        batch.update(db.collection(TESTS).document(test.getId()), "category", test.getCategory());
        batch.update(db.collection(TESTS).document(test.getId()), "language", test.getLanguage());
        batch.update(db.collection(TESTS).document(test.getId()), "description", test.getDescription());
        batch.update(db.collection(TESTS).document(test.getId()), "isOnline", test.isOnline());
        return batch.commit();
    }

    public Task<Void> deleteTest(String testId){
        return db.collection(TESTS).document(testId).delete();
    }

    public Task<Void> updateTestAddQuesNum(Test test){
        WriteBatch batch = db.batch();
        batch.update(db.collection(TESTS).document(test.getId()), "numberOfQuestions", test.getNumberOfQuestions());
        return batch.commit();
    }

    public Task<Void> addQuestion(Question question){
        return db.collection(QUESTIONS).document(question.getId()).set(question);
    }

    public Task<Void> updateQuestion(Question question){
        return db.collection(QUESTIONS).document(question.getId())
                .update("questionText", question.getQuestionText());
    }

    public Task<Void> deleteQuestion(String questionId){
        return db.collection(QUESTIONS).document(questionId).delete();
    }

    public Task<Void> deleteQuestionList(String testId){
        List<Question> questionList = new ArrayList<>();
        db.collection(QUESTIONS).whereEqualTo("testId", testId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                questionList.add(document.toObject(Question.class));
                            }
                        } else{
                            Log.d(TAG, "deleteQuestionList: Error");
                        }
                    }
                });
        WriteBatch batch = db.batch();
        for (Question question : questionList){
            batch.delete(db.collection(QUESTIONS).document(question.getId()));
        }
        return batch.commit();
    }

    public Task<Void> addAnswerList(List<Answer> answers){
        WriteBatch batch = db.batch();
        for (Answer answer : answers){
            batch.set(db.collection(ANSWERS).document(answer.getId()), answer);
        }
        return batch.commit();
    }

    public Task<Void> updateAnswerList(List<Answer> answers){
        WriteBatch batch = db.batch();
        for (Answer answer : answers){
            batch.update(db.collection(ANSWERS).document(answer.getId()), "answerText", answer.getAnswerText());
            batch.update(db.collection(ANSWERS).document(answer.getId()), "isCorrect", answer.isCorrect());
        }
        return batch.commit();
    }

    public Task<Void> deleteAnswerList(String questionId){
        List<Answer> answerList = new ArrayList<>();
        db.collection(ANSWERS).whereEqualTo("questionId", questionId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        answerList.add(document.toObject(Answer.class));
                    }
                } else {
                    Log.d(TAG, "deleteAnswerList: Error");
                }
            }
        });
        WriteBatch batch = db.batch();
        for (Answer answer : answerList){
            batch.delete(db.collection(ANSWERS).document(answer.getId()));
        }
        return batch.commit();
    }

    public Query getTop50Tests(){
        return db.collection(TESTS).orderBy("title", Query.Direction.DESCENDING).limit(LIMIT);
    }

    public Query getFilterTests(Filters filters){
        Query query = db.collection(TESTS);
        if (filters.hasCategory()){
            query = query.whereEqualTo("category", filters.getCategory());
        } if (filters.hasLanguage()){
            query = query.whereEqualTo("language", filters.getLanguage());
        } if (filters.hasSortBy()) {
            query = query.orderBy(filters.getSortBy(), Query.Direction.DESCENDING);
        } if (filters.hasAuthor()){
            query = query.whereEqualTo("userName", filters.getAuthor());
        }
        return query = query.limit(LIMIT);
    }

    public Query getQuestionList(String testId){
        return db.collection(QUESTIONS).whereEqualTo("testId", testId);
    }

    public Query getAnswerList(String questionId){
        return db.collection(ANSWERS).whereEqualTo("questionId", questionId);
    }

    public Query getCorrectAnswers(String questionId){
        Query query = db.collection(ANSWERS);
        query = query.whereEqualTo("questionId", questionId);
        query = query.whereEqualTo("correct", true);
        return  query;
    }

    public Task<Void> addResult(Result result){
        return db.collection(RESULTS).document(result.getId()).set(result);
    }

    public Task<Void> addRating(String testId, double rating){
        return db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentReference testRef = db.collection(TESTS).document(testId);
                Test test = transaction.get(testRef).toObject(Test.class);
                int newNumRating = test.getNumRatings() + 1;
                double oldRatingTotal = test.getAvgRating() * test.getNumRatings();
                double newAvgRating = (oldRatingTotal + rating) / newNumRating;
                test.setNumRatings(newNumRating);
                test.setAvgRating(newAvgRating);
                transaction.set(testRef, test);
                return null;
            }
        });
    }
}
