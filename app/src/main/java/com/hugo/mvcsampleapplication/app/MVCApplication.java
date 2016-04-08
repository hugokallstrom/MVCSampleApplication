package com.hugo.mvcsampleapplication.app;

import android.app.Application;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.components.ApplicationComponent;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.components.DaggerApplicationComponent;
import com.hugo.mvcsampleapplication.utils.dependencyinjection.modules.ApplicationModule;

public class MVCApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    if(applicationComponent == null) {
      applicationComponent =
          DaggerApplicationComponent.builder().applicationModule(new ApplicationModule()).build();
    }
  }

  public void setApplicationComponent(ApplicationComponent applicationComponent) {
    this.applicationComponent = applicationComponent;
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

}
