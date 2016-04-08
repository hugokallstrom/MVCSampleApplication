package com.hugo.mvcsampleapplication.utils.dependencyinjection.modules;

import com.hugo.mvcsampleapplication.model.network.GitHubService;
import com.hugo.mvcsampleapplication.utils.JobExecutor;
import com.hugo.mvcsampleapplication.utils.PostExecutionThread;
import com.hugo.mvcsampleapplication.utils.ThreadExecutor;
import com.hugo.mvcsampleapplication.utils.UiThread;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class ApplicationModule {

  public ApplicationModule() {

  }

  @Provides
  @Singleton
  public GitHubService provideGitHubService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(GitHubService.class);
  }

  @Provides
  @Singleton
  public ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  public PostExecutionThread providePostExecutionThread(UiThread uiThread) {
    return uiThread;
  }

}

