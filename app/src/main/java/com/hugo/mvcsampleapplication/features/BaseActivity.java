package com.hugo.mvcsampleapplication.features;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.hugo.mvcsampleapplication.app.MVCApplication;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.components.ApplicationComponent;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.components.DaggerUserComponent;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.components.UserComponent;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.modules.UserModule;

public abstract class BaseActivity extends AppCompatActivity {

  private UserComponent userComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getApplicationComponent().inject(this);
    initializeInject();
  }

  protected void addFragment(int containerId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(containerId, fragment);
    fragmentTransaction.commit();
  }

  public ApplicationComponent getApplicationComponent() {
    return ((MVCApplication) getApplication()).getApplicationComponent();
  }

  private void initializeInject() {
    userComponent = DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .userModule(new UserModule())
        .build();
  }

  public UserComponent getUserComponent() {
    return userComponent;
  }
}
