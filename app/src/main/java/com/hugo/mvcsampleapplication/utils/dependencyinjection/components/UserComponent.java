package com.hugo.mvcsampleapplication.utils.dependencyinjection.components;

import com.hugo.mvcsampleapplication.features.searchuser.SearchUserFragment;
import com.hugo.mvcsampleapplication.features.userdetails.UserDetailsFragment;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.PerActivity;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.modules.UserModule;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = UserModule.class)
public interface UserComponent {
  void inject(SearchUserFragment searchUserFragment);
  void inject(UserDetailsFragment userDetailsFragment);
}
