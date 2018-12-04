package com.sergeyyaniuk.testity.ui.tests.editTest;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Answer;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditQuestionPresenter extends BasePresenter {

    private DatabaseManager mDatabase;
    private Firestore mFirestore;
    private PrefHelper mPrefHelper;
    private EditQuestionFragment mFragment;

    public EditQuestionPresenter(EditQuestionFragment fragment, DatabaseManager database,
                                  Firestore firestore, PrefHelper prefHelper) {
        this.mFragment = fragment;
        this.mDatabase = database;
        this.mFirestore = firestore;
        this.mPrefHelper = prefHelper;
    }

    public String getTestId(){
        return mPrefHelper.getCurrentTestId();
    }

    public void loadQuestion(String questionId) {
        getCompositeDisposable().add(mDatabase.getQuestion(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(question -> {
                    String questionText = question.getQuestionText();
                    mFragment.setQuestionTest(questionText);
                }, throwable -> {
                    String emptyText = " ";
                    mFragment.setQuestionTest(emptyText);
                }));
    }

    public void loadAnswers(String questionId) {
        getCompositeDisposable().add(mDatabase.getAnswerList(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answers -> {
                    mFragment.loadAnswerData(answers);
                }, throwable -> {
                    List<Answer> answers = new ArrayList<>();
                    mFragment.loadAnswerData(answers);
                }));
    }
}
