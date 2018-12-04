package com.sergeyyaniuk.testity.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class Firestore {

    private static final String TAG = "MyLog";

    public static final String USERS = "users";
    public static final String TESTS = "tests";
    public static final String QUESTIONS = "questions";
    public static final String ANSWERS = "answers";

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

    public Task<Void> updateTestAddTwo(Test test){
        WriteBatch batch = db.batch();
        batch.update(db.collection(TESTS).document(test.getId()), "numberOfQuestions", test.getNumberOfQuestions());
        batch.update(db.collection(TESTS).document(test.getId()), "numberOfCorrectAnswers", test.getNumberOfCorrectAnswers());
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
}
