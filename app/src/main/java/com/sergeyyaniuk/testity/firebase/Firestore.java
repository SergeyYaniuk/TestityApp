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

    public void addUser(String id, String name, String email, String loginWith){
        User user = new User(id, name, email, loginWith);
        db.collection(USERS).document(id).set(user);
    }

    public Task<Void> addTest(Test test){
        return db.collection(TESTS).document(test.getId()).set(test);
    }

    public Task<Void> addQuestion(Question question){
        return db.collection(QUESTIONS).document(question.getId()).set(question);
    }

    public Task<Void> deleteQuestion(String questionId){
        return db.collection(QUESTIONS).document(questionId).delete();
    }

    public Task<Void> addAnswerList(List<Answer> answers){
        WriteBatch batch = db.batch();
        for (Answer answer : answers){
            batch.set(db.collection(ANSWERS).document(answer.getId()), answer);
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
