package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.results.ResultsActivity;
import com.sergeyyaniuk.testity.ui.results.ResultsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ResultsModule {

    private ResultsActivity mActivity;

    public ResultsModule(ResultsActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    ResultsActivity provideActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    ResultsPresenter providePresenter(Authentication authentication, DatabaseManager databaseManager,
                                      Firestore firestore, PrefHelper prefHelper){
        return new ResultsPresenter(mActivity, databaseManager, firestore, prefHelper);
    }
}
