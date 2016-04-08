package com.hugo.mvcsampleapplication.utils;

import rx.Scheduler;

public interface ThreadExecutor {
    Scheduler getScheduler();
}
