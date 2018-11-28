package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.createTest.questions.QuestionsActivity;
import com.sergeyyaniuk.testity.ui.createTest.questions.QuestionsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionsListModule {

    private QuestionsActivity mActivity;

    public QuestionsListModule(QuestionsActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    QuestionsActivity provideQuestionsActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    QuestionsPresenter provideQuestionsPresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new QuestionsPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
