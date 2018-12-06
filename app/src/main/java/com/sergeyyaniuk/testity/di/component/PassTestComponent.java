package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.PassTestModule;
import com.sergeyyaniuk.testity.ui.tests.passTest.passTest.PassTestActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = PassTestModule.class)
public interface PassTestComponent {

    PassTestActivity inject(PassTestActivity passTestActivity);
}
