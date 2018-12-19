package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.results.LocalResultsFragment;
import com.sergeyyaniuk.testity.ui.results.LocalResultsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalResultsModule {

    private LocalResultsFragment mFragment;

    public LocalResultsModule(LocalResultsFragment fragment){
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    LocalResultsFragment provideFragment(){
        return mFragment;
    }

    @FragmentScope
    @Provides
    LocalResultsPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new LocalResultsPresenter(mFragment, databaseManager, firestore, prefHelper);
    }
}
