package com.sergeyyaniuk.testity.ui.base;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter {

    CompositeDisposable mCompositeDisposable;

    public void onAttach(){

    }

    public void onDetach(){
        mCompositeDisposable.dispose();
    }

    //to manage subscription
    public CompositeDisposable getCompositeDisposable(){
        return mCompositeDisposable;
    }
}
