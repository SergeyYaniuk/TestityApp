package com.sergeyyaniuk.testity.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.model.User;

import java.util.List;

public class Firestore {

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
}
