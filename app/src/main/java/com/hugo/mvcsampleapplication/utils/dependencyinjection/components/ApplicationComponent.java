package com.hugo.mvcsampleapplication.utils.dependencyinjection.components;

import com.hugo.mvcsampleapplication.features.BaseActivity;
import com.hugo.mvcsampleapplication.model.network.GitHubService;
import com.hugo.mvcsampleapplication.utils.PostExecutionThread;
import com.hugo.mvcsampleapplication.utils.ThreadExecutor;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.modules.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  GitHubService gitHubService();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();

}