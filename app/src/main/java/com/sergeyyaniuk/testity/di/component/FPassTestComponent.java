package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.FPassTestModule;
import com.sergeyyaniuk.testity.ui.find.findPass.passTest.FPassTestActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FPassTestModule.class)
public interface FPassTestComponent {

    FPassTestActivity inject(FPassTestActivity fPassTestActivity);
}
