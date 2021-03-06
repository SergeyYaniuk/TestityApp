package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.database.DatabaseManager;
import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.firebase.Firestore;
import com.sergeyyaniuk.testity.ui.login.LoginActivity;
import com.sergeyyaniuk.testity.ui.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private LoginActivity mActivity;

    public LoginModule(LoginActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    LoginActivity provideLoginActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    LoginPresenter provideLoginPresenter(Authentication authentication, DatabaseManager databaseManager,
                                         Firestore firestore, PrefHelper prefHelper){
        return new LoginPresenter(mActivity, authentication, databaseManager, firestore, prefHelper);
    }
}
