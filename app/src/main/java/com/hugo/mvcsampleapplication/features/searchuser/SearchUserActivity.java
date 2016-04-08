package com.hugo.mvcsampleapplication.features.searchuser;

import android.os.Bundle;
import android.util.Log;
import com.hugo.mvcsampleapplication.R;
import com.hugo.mvcsampleapplication.features.BaseActivity;
import com.hugo.mvcsampleapplication.features.userdetails.UserDetailsActivity;
import com.hugo.mvcsampleapplication.features.userdetails.UserDetailsFragment;

public class SearchUserActivity extends BaseActivity
    implements SearchUserFragment.ActivityListener {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_user);
    if (savedInstanceState == null) {
      addFragment(R.id.content_activity_search_user, SearchUserFragment.newInstance());
    }
  }

  @Override
  public void startUserDetails(String username) {
    UserDetailsFragment userDetailsFragment =
        (UserDetailsFragment) getSupportFragmentManager().findFragmentById(
            R.id.userDetailsFragment);
    if (userDetailsFragment == null) {
      Log.d("activity", "starting user details " + username);
      startActivity(UserDetailsActivity.newIntent(this, username));
    } else {
      userDetailsFragment.loadRepositories(username);
    }
  }
}
