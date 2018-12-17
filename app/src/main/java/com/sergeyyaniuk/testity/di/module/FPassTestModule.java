package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.find.findPass.passTest.FPassTestActivity;
import com.sergeyyaniuk.testity.ui.find.findPass.passTest.FPassTestPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class FPassTestModule {

    private FPassTestActivity mActivity;

    public FPassTestModule(FPassTestActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    FPassTestActivity provideActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    FPassTestPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new FPassTestPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
