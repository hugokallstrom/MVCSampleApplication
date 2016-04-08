package com.hugo.mvcsampleapplication.utils.dependencyinjection.modules;

import com.hugo.mvcsampleapplication.features.UseCase;
import com.hugo.mvcsampleapplication.features.searchuser.SearchUserUseCase;
import com.hugo.mvcsampleapplication.features.userdetails.LoadUserDetailsUseCase;
import com.hugo.mvcsampleapplication.model.network.GitHubService;
import com.hugo.mvcsampleapplication.utils.PostExecutionThread;
import com.hugo.mvcsampleapplication.utils.ThreadExecutor;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.PerActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Created by hugo on 3/11/16.
 */
@Module
public class UserModule {

  public UserModule() {
  }

  @Provides
  @PerActivity
  @Named("searchUser")
  public UseCase provideSearchUserUseCase(GitHubService gitHubService, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new SearchUserUseCase(gitHubService, threadExecutor, postExecutionThread);
  }

  @Provides
  @PerActivity
  @Named("userDetails")
  public UseCase provideUserDetailsUseCase(GitHubService gitHubService, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new LoadUserDetailsUseCase(gitHubService, threadExecutor, postExecutionThread);
  }
}

