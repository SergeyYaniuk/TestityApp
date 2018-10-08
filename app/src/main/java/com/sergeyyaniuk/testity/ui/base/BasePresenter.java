package com.sergeyyaniuk.testity.ui.base;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter {

    CompositeDisposable mCompositeDisposable;

    public void onCreate(){
        mCompositeDisposable = new CompositeDisposable();
    }

    public void onDestroy(){
        mCompositeDisposable.dispose();
    }

    //to manage subscription
    public CompositeDisposable getCompositeDisposable(){
        return mCompositeDisposable;
    }
}
