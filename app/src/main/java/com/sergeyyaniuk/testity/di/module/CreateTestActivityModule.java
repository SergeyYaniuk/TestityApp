package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.ui.create.CreateTestActivity;
import com.sergeyyaniuk.testity.ui.create.CreateTestPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateTestActivityModule {

    private CreateTestActivity mActivity;

    public CreateTestActivityModule(CreateTestActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    CreateTestActivity provideCreateTestActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    CreateTestPresenter provideCreateTestPresenter(DatabaseManager databaseManager){
        return new CreateTestPresenter(mActivity, databaseManager);
    }
}
