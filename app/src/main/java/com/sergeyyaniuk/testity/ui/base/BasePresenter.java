package com.sergeyyaniuk.testity.ui.base;

import android.content.Context;

import com.sergeyyaniuk.testity.App;

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

    public String getResources(Context context, int resource){
        return App.get(context).getResources().getString(resource);
    }
}
