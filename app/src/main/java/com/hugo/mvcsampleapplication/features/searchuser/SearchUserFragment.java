package com.hugo.mvcsampleapplication.features.searchuser;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hugo.mvcsampleapplication.R;
import com.hugo.mvcsampleapplication.features.BaseActivity;
import com.hugo.mvcsampleapplication.features.DefaultSubscriber;
import com.hugo.mvcsampleapplication.features.UseCase;
import com.hugo.mvcsampleapplication.model.entities.User;
import com.hugo.mvcsampleapplication.model.network.SearchResponse;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public class SearchUserFragment extends Fragment {

  private static final String SEARCH_USER_LIST_VISIBILITY = "searchUserListVisibility";
  private static final String PROGRESS_VISIBILITY = "progressVisibility";

  @Bind(R.id.search_user_list) RecyclerView userList;
  @Bind(R.id.progress_indicator) RelativeLayout progressIndicator;
  @Bind(R.id.button_search) ImageButton searchButton;
  @Bind(R.id.edit_text_username) EditText editTextUsername;

  private UserListAdapter userListAdapter;
  private ActivityListener activityListener;
  private LinearLayoutManager layoutManager;
  @Inject @Named("searchUser")UseCase searchUserUseCase;

  public interface ActivityListener {
    void startUserDetails(String username);
  }

  public SearchUserFragment() {}

  public static SearchUserFragment newInstance() {
    return new SearchUserFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    ((BaseActivity) getActivity()).getUserComponent().inject(this);
    setUpUserListAdapter();
  }

  private void setUpUserListAdapter() {
    userListAdapter = new UserListAdapter();
    userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(String username) {
        activityListener.startUserDetails(username);
      }
    });
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof ActivityListener) {
      this.activityListener = (ActivityListener) context;
    }
    layoutManager = new LinearLayoutManager(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.fragment_search_user, container, false);
    ButterKnife.bind(this, fragmentView);
    if (savedInstanceState != null) {
      restoreState(savedInstanceState);
    }
    setSearchButtonOnClickListener();
    setEditTextActionListener();
    userList.setLayoutManager(layoutManager);
    userList.setAdapter(userListAdapter);
    return fragmentView;
  }

  public void setSearchButtonOnClickListener() {
    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        searchUsers();
      }
    });
  }

  private void setEditTextActionListener() {
    editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
          searchUsers();
          return true;
        }
        return false;
      }
    });
  }

  public void searchUsers() {
    String username = editTextUsername.getText().toString();
    hideKeyBoard(editTextUsername);
    if(username.isEmpty()) {
      showMessage("Enter a username");
    } else {
      showProgressIndicator();
      searchUserUseCase.execute(new SearchUserSubscriber(), username);
    }
  }

  private void hideKeyBoard(View view) {
    view.clearFocus();
    InputMethodManager mgr =
        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public void showMessage(String message) {
    View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
    Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
    snackbar.show();
  }

  public void showProgressIndicator() {
    userList.setVisibility(View.INVISIBLE);
    progressIndicator.setVisibility(View.VISIBLE);
  }

  public void hideProgressIndicator() {
    progressIndicator.setVisibility(View.INVISIBLE);
    userList.setVisibility(View.VISIBLE);
  }

  public void showUsers(List<User> users) {
    userListAdapter.setUsers(users);
    userListAdapter.notifyDataSetChanged();
  }

  @SuppressWarnings("ResourceType")
  private void restoreState(Bundle savedInstanceState) {
    progressIndicator.setVisibility(savedInstanceState.getInt(PROGRESS_VISIBILITY));
    userList.setVisibility(savedInstanceState.getInt(SEARCH_USER_LIST_VISIBILITY));
  }

  @Override
  public void onSaveInstanceState(Bundle state) {
    super.onSaveInstanceState(state);
    state.putInt(PROGRESS_VISIBILITY, progressIndicator.getVisibility());
    state.putInt(SEARCH_USER_LIST_VISIBILITY, userList.getVisibility());
  }

  @Override
  public void onDetach() {
    super.onDetach();
    activityListener = null;
    layoutManager = null;
    ButterKnife.unbind(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    searchUserUseCase.unsubscribe();
  }

  private final class SearchUserSubscriber extends DefaultSubscriber<SearchResponse> {

    @Override
    public void onCompleted() {
      hideProgressIndicator();
    }

    @Override
    public void onError(Throwable e) {
      hideProgressIndicator();
      showMessage("Error loading users");
    }

    @Override
    public void onNext(SearchResponse searchResponse) {
      List<User> users = searchResponse.getUsers();
      if (users.isEmpty()) {
        showMessage("No users found");
      }
      showUsers(users);
    }

  }

}
