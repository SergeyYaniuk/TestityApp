package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.RateTestModule;
import com.sergeyyaniuk.testity.ui.find.findPass.endTest.rateTest.RateTestActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = RateTestModule.class)
public interface RateComponent {

    RateTestActivity inject(RateTestActivity rateTestActivity);
}
