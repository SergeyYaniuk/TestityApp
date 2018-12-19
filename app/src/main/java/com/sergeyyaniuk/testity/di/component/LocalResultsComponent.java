package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.di.module.LocalResultsModule;
import com.sergeyyaniuk.testity.ui.results.LocalResultsFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = LocalResultsModule.class)
public interface LocalResultsComponent {

    LocalResultsFragment inject(LocalResultsFragment localResultsFragment);
}
