package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.tests.myTests.MyTestsActivity;
import com.sergeyyaniuk.testity.ui.tests.myTests.MyTestsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MyTestsModule {

    private MyTestsActivity mActivity;

    public MyTestsModule(MyTestsActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    MyTestsActivity provideMyTestsActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    MyTestsPresenter provideMyTestsPresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new MyTestsPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
