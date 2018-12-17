package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.find.findPass.endTest.rateTest.RateTestActivity;
import com.sergeyyaniuk.testity.ui.find.findPass.endTest.rateTest.RateTestPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RateTestModule {

    private RateTestActivity mActivity;

    public RateTestModule(RateTestActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    RateTestActivity provideActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    RateTestPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new RateTestPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
