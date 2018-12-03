package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.di.module.EditListModule;
import com.sergeyyaniuk.testity.ui.tests.editTest.EditListFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = EditListModule.class)
public interface EditListComponent {

    EditListFragment inject(EditListFragment editListFragment);
}
