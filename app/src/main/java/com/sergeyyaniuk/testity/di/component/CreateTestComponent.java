package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.CreateTestModule;
import com.sergeyyaniuk.testity.ui.create.createTest.CreateTestActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = CreateTestModule.class)
public interface CreateTestComponent {

    CreateTestActivity inject(CreateTestActivity createTestActivity);
}
