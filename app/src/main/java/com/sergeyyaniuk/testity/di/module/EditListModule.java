package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditListFragment;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class EditListModule {

    private EditListFragment mFragment;

    public EditListModule(EditListFragment fragment){
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    EditListFragment provideFragment(){
        return mFragment;
    }

    @FragmentScope
    @Provides
    EditListPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new EditListPresenter(mFragment, databaseManager, firestore, prefHelper);
    }
}
