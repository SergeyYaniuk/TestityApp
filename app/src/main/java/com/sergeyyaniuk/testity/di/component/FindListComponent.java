package com.sergeyyaniuk.testity.di.component;

import com.sergeyyaniuk.testity.di.ActivityScope;
import com.sergeyyaniuk.testity.di.module.FindListModule;
import com.sergeyyaniuk.testity.ui.find.findList.FindListActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = FindListModule.class)
public interface FindListComponent {

    FindListActivity inject(FindListActivity findListActivity);
}
