package com.sergeyyaniuk.testity.ui.create;

import com.sergeyyaniuk.testity.data.model.Test;

public interface TestContract {

    interface View{

    }

    interface UserActionListener{

        Test loadTest(final long testId);
    }
}
