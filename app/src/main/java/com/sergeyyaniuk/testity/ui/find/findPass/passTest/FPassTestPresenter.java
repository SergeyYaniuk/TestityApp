package com.sergeyyaniuk.testity.ui.find.findPass.passTest;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.model.Question;
import com.sergeyyaniuk.testity.data.model.Result;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class FPassTestPresenter extends BasePresenter {

    private static final String TAG = "MyLog";

    private FPassTestActivity mActivity;
    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;

    public FPassTestPresenter(FPassTestActivity activity, DatabaseManager database,
                             Firestore firestore, PrefHelper prefHelper) {
        this.mActivity = activity;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public int getNumberOfCorrect(){
        return mPrefHelper.getNumOfCorAnsw();
    }

    public void cleanTotalCorr(){
        mPrefHelper.cleanNumOfCorAnsw();
    }

    public String getUserId(){
        return mPrefHelper.getCurrentUserId();
    }

    public void loadQuestions(String testId){
        mFirestore.getQuestionList(testId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Question> questions = queryDocumentSnapshots.toObjects(Question.class);
                mActivity.setQuestionList(questions);
                getNumberOfCorrectAnswers(questions);
            }
        });
    }

    public Task<QuerySnapshot> loadAnswers(String questionId) {
        return mFirestore.getAnswerList(questionId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                //cannot make querySnapshot to object, don't read boolean
                List<Answer> answers = new ArrayList<>();
                for (DocumentSnapshot document : queryDocumentSnapshots){
                    Answer answer = new Answer();
                    answer.setId(document.getString("id"));
                    answer.setQuestionId(document.getString("questionId"));
                    answer.setCorrect(document.getBoolean("correct"));
                    answer.setAnswerText(document.getString("answerText"));
                    answers.add(answer);
                }
                mActivity.updateAnswers(answers);
            }
        });
    }

    public void getNumberOfCorrectAnswers(List<Question> questions){
        for (Question question : questions){
            mFirestore.getCorrectAnswers(question.getId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    int number = queryDocumentSnapshots.size();
                    mPrefHelper.addCorrAnswer(number);
                    Log.d(TAG, "onSuccess: " + mPrefHelper.getNumOfCorAnsw());
                }
            });
        }
    }

    public void saveResult(Result result){
        mFirestore.addResult(result).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Error");
            }
        });
    }
}
