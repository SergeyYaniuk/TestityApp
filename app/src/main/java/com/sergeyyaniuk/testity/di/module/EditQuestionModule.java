package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditQuestionFragment;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditQuestionPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class EditQuestionModule {

    private EditQuestionFragment mFragment;

    public EditQuestionModule(EditQuestionFragment fragment){
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    EditQuestionFragment provideFragment(){
        return mFragment;
    }

    @FragmentScope
    @Provides
    EditQuestionPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new EditQuestionPresenter(mFragment, databaseManager, firestore, prefHelper);
    }
}
