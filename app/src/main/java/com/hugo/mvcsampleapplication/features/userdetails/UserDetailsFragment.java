package com.hugo.mvcsampleapplication.features.userdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hugo.mvcsampleapplication.R;
import com.hugo.mvcsampleapplication.features.BaseActivity;
import com.hugo.mvcsampleapplication.features.DefaultSubscriber;
import com.hugo.mvcsampleapplication.features.UseCase;
import com.hugo.mvcsampleapplication.model.entities.Repository;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public class UserDetailsFragment extends Fragment {

  @Bind(R.id.progress_indicator) RelativeLayout progressIndicator;
  @Bind(R.id.repositories_list) RecyclerView repositoriesList;

  @Inject @Named("userDetails")UseCase loadUserDetailsUseCase;
  private RepositoriesAdapter userDetailsAdapter;
  private LinearLayoutManager layoutManager;

  public UserDetailsFragment() {}

  public static UserDetailsFragment newInstance(String username) {
    UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
    Bundle bundle = new Bundle();
    bundle.putString("username", username);
    userDetailsFragment.setArguments(bundle);
    return userDetailsFragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    ((BaseActivity) getActivity()).getUserComponent().inject(this);
    userDetailsAdapter = new RepositoriesAdapter();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    layoutManager = new LinearLayoutManager(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_user_details, container, false);
    ButterKnife.bind(this, fragmentView);
    repositoriesList.setAdapter(userDetailsAdapter);
    repositoriesList.setLayoutManager(layoutManager);
    if (getArguments() != null && savedInstanceState == null) {
      String username = getArguments().getString("username");
      loadRepositories(username);
    }
    return fragmentView;
  }

  public void loadRepositories(String username) {
    showProgressIndicator();
    loadUserDetailsUseCase.execute(new LoadRepositoriesSubscriber(), username);
  }

  public void showMessage(String message) {
    View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
    Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
    snackbar.show();
  }

  public void showProgressIndicator() {
    repositoriesList.setVisibility(View.INVISIBLE);
    progressIndicator.setVisibility(View.VISIBLE);
  }

  public void hideProgressIndicator() {
    progressIndicator.setVisibility(View.INVISIBLE);
    repositoriesList.setVisibility(View.VISIBLE);
  }

  public void showRepositories(List<Repository> repositories) {
    userDetailsAdapter.setReposetories(repositories);
    userDetailsAdapter.notifyDataSetChanged();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    layoutManager = null;
    ButterKnife.unbind(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    loadUserDetailsUseCase.unsubscribe();
  }

  private final class LoadRepositoriesSubscriber extends DefaultSubscriber<List<Repository>> {

    @Override
    public void onCompleted() {
      hideProgressIndicator();
    }
    @Override
    public void onError(Throwable e) {
      hideProgressIndicator();
      showMessage("Error loading repositories");
      e.printStackTrace();
    }

    @Override
    public void onNext(List<Repository> repositories) {
      if (repositories.isEmpty()) {
        showMessage("No public repositories");
      }
      showRepositories(repositories);
    }

  }

}
