package com.hugo.mvcsampleapplication.features.searchuser;

import com.hugo.mvcsampleapplication.features.UseCase;
import com.hugo.mvcsampleapplication.model.network.GitHubService;
import com.hugo.mvcsampleapplication.utils.PostExecutionThread;
import com.hugo.mvcsampleapplication.utils.ThreadExecutor;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.PerActivity;
import javax.inject.Inject;
import rx.Observable;

@PerActivity
public class SearchUserUseCase extends UseCase {

  @Inject
  public SearchUserUseCase(GitHubService gitHubService, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(gitHubService, threadExecutor, postExecutionThread);
  }

  @Override
  public Observable buildUseCase(String username) throws NullPointerException {
    if (username == null) {
      throw new NullPointerException("Query must not be null");
    }
    return getGitHubService().searchUser(username);
  }
}
