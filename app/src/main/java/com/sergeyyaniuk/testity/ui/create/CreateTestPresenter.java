package com.sergeyyaniuk.testity.ui.create;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.model.Test;
import com.sergeyyaniuk.testity.ui.base.BasePresenter;


public class CreateTestPresenter extends BasePresenter {

    private DatabaseManager mDatabase;

    public CreateTestPresenter(DatabaseManager database) {
        this.mDatabase = database;
    }

    public void addTest(final Test test){
        getCompositeDisposable().add(mDatabase.insertTestWithId(test).subscribe(
                aLong -> {},
                throwable -> {}
                ));
    }

    public void loadQuestions(final long testId){
        getCompositeDisposable().add(mDatabase.getQuestionsSorted(testId).subscribe(
                questions -> {},
                throwable -> {}
                ));
    }


}