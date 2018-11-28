package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.createTest.questions.QuestionsListPresenter;
import com.sergeyyaniuk.testity.ui.createTest.questions.QuestionsListFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionsListFragModule {

    private QuestionsListFragment mFragment;

    public QuestionsListFragModule(QuestionsListFragment fragment){
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    QuestionsListFragment provideFragment(){
        return mFragment;
    }

    @FragmentScope
    @Provides
    QuestionsListPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new QuestionsListPresenter(mFragment, databaseManager, firestore, prefHelper);
    }
}
