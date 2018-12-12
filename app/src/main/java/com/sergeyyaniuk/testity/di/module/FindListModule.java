package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.find.findList.FindListActivity;
import com.sergeyyaniuk.testity.ui.find.findList.FindListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class FindListModule {

    private FindListActivity mActivity;

    public FindListModule(FindListActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    FindListActivity provideActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    FindListPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new FindListPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
