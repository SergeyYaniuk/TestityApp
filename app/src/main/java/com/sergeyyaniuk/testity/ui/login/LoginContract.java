package com.sergeyyaniuk.testity.ui.login;

import com.sergeyyaniuk.testity.ui.base.BasePresenterContract;
import com.sergeyyaniuk.testity.ui.base.BaseViewContract;

public interface LoginContract {

    interface View extends BaseViewContract{

    }

    interface Presenter<V extends LoginContract.View> extends BasePresenterContract<V> {

    }
}
