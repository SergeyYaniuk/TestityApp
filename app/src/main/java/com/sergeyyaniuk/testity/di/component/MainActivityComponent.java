package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.MainActivityModule;
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent {

    MainActivity inject(MainActivity mainActivity);
}
