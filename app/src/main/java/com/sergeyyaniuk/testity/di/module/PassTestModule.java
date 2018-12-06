package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.tests.passTest.passTest.PassTestActivity;
import com.sergeyyaniuk.testity.ui.tests.passTest.passTest.PassTestPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PassTestModule {

    private PassTestActivity mActivity;

    public PassTestModule(PassTestActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    PassTestActivity provideActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    PassTestPresenter providePresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new PassTestPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
