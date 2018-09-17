package com.sergeyyaniuk.testity.ui.login;

import com.sergeyyaniuk.testity.ui.base.BasePresenterContract;
import com.sergeyyaniuk.testity.ui.base.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenterContract<View>{

    }
}
