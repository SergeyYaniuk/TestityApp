package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.results.OnlineResultsFragment;
import com.sergeyyaniuk.testity.ui.results.OnlineResultsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class OnlineResultsModule {

    private OnlineResultsFragment mFragment;

    public OnlineResultsModule(OnlineResultsFragment fragment){
        this.mFragment = fragment;
    }

    @FragmentScope
    @Provides
    OnlineResultsFragment provideFragment(){
        return mFragment;
    }

    @FragmentScope
    @Provides
    OnlineResultsPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new OnlineResultsPresenter(mFragment, databaseManager, firestore, prefHelper);
    }
}
