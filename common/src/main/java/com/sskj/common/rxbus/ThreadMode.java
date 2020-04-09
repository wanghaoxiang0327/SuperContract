package com.sskj.common.rxbus;

/**
 * Created by lvzhihao on 17-7-25.
 */

public enum ThreadMode {
    CURRENT_THREAD,
    MAIN,
    NEW_THREAD;

    private ThreadMode() {
    }
}
