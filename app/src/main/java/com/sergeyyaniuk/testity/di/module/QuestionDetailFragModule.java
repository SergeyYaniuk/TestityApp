package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.createTest.questions.DetailQuestionFragment;
import com.sergeyyaniuk.testity.ui.createTest.questions.DetailQuestionPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionDetailFragModule {

    private DetailQuestionFragment mFragment;

    public QuestionDetailFragModule(DetailQuestionFragment fragment){
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    DetailQuestionFragment provideFragment(){
        return mFragment;
    }

    @FragmentScope
    @Provides
    DetailQuestionPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new DetailQuestionPresenter(mFragment, databaseManager, firestore, prefHelper);
    }
}
