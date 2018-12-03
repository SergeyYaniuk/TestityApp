package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.EditTestModule;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditTestActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = EditTestModule.class)
public interface EditComponent {

    EditTestActivity inject(EditTestActivity editTestActivity);
}
