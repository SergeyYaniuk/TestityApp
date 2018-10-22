package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.CreateTestActivityModule;
import com.sergeyyaniuk.testity.ui.create.CreateTestActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = CreateTestActivityModule.class)
public interface CreateTestActivityComponent {

    CreateTestActivity inject(CreateTestActivity createTestActivity);
}
