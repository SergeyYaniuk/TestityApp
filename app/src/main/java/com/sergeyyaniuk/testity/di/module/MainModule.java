package com.sergeyyaniuk.testity.di.module;

import com.sergeyyaniuk.testity.data.preferences.PrefHelper;
import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.firebase.Authentication;
import com.sergeyyaniuk.testity.ui.main.MainActivity;
import com.sergeyyaniuk.testity.ui.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainActivity mActivity;

    public MainModule(MainActivity activity){
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity(){
        return mActivity;
    }

    @ActivityScope
    @Provides
    MainPresenter provideMainPresenter(Authentication authentication, PrefHelper prefHelper){
        return new MainPresenter(mActivity, authentication, prefHelper);
    }
}
