package com.sergeyyaniuk.testity.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

public class Firestore {

    FirebaseFirestore db;

    public Firestore() {
        db = FirebaseFirestore.getInstance();
    }
}
