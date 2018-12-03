package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.di.module.EditTestFragModule;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditTestFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = EditTestFragModule.class)
public interface EditFragComponent {

    EditTestFragment inject(EditTestFragment editTestFragment);
}
