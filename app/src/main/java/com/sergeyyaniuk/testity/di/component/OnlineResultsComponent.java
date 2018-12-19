package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.FragmentScope;
import com.sergeyyaniuk.testity.di.module.OnlineResultsModule;
import com.sergeyyaniuk.testity.ui.results.OnlineResultsFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = OnlineResultsModule.class)
public interface OnlineResultsComponent {

    OnlineResultsFragment inject(OnlineResultsFragment onlineResultsFragment);
}
