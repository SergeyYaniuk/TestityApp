package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditTestFragPresenter;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditTestFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class EditTestFragModule {

    private EditTestFragment mFragment;

    public EditTestFragModule(EditTestFragment fragment){
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    EditTestFragment provideFragment(){
        return mFragment;
    }

    @FragmentScope
    @Provides
    EditTestFragPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new EditTestFragPresenter(mFragment, databaseManager, firestore, prefHelper);
    }
}
