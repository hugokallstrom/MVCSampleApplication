package com.hugo.mvcsampleapplication.utils;

import rx.Scheduler;

public interface PostExecutionThread {
  Scheduler getScheduler();
}
