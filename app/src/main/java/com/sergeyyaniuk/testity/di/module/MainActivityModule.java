package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.main.MainActivity;
import com.sergeyyaniuk.testity.ui.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private MainActivity mActivity;

    public MainActivityModule(MainActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    MainPresenter provideMainPresenter(Authentication authentication){
        return new MainPresenter(mActivity, authentication);
    }
}
