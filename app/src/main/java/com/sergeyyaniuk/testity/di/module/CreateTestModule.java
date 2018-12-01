package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.create.createTest.CreateTestActivity;
import com.sergeyyaniuk.testity.ui.create.createTest.CreateTestPresenter;

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
