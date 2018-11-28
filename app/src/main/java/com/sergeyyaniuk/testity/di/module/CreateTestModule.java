package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.createTest.create.CreateTestActivity;
import com.sergeyyaniuk.testity.ui.createTest.create.CreateTestPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateTestModule {

    private CreateTestActivity mActivity;

    public CreateTestModule(CreateTestActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    CreateTestActivity provideCreateTestActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    CreateTestPresenter provideCreateTestPresenter(DatabaseManager databaseManager, Firestore firestore, PrefHelper prefHelper){
        return new CreateTestPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
