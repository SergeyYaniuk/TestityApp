package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.MainModule;
import com.sergeyyaniuk.testity.ui.main.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    MainActivity inject(MainActivity mainActivity);
}
