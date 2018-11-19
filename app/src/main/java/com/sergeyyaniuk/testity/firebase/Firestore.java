package com.sergeyyaniuk.testity.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.data.model.User;

public class Firestore {

    public static final String USERS = "users";
    public static final String TESTS = "tests";
    public static final String QUESTIONS = "questions";

    FirebaseFirestore db;

    public Firestore() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(String id, String name, String email, String loginWith){
        User user = new User(id, name, email, loginWith);
        db.collection(USERS).document(id).set(user);
    }

    public void addTest(Test test){
        db.collection(TESTS).document(test.getId()).set(test);
    }

    public void addQuestion(Question question){
        db.collection(QUESTIONS).document(question.getId()).set(question);
    }
}
