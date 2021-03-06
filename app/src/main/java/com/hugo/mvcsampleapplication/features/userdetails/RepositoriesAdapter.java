package com.hugo.mvcsampleapplication.features.userdetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hugo.mvcsampleapplication.R;
import com.hugo.mvcsampleapplication.model.entities.Repository;
import java.util.Collections;
import java.util.List;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepoViewHolder> {

    private List<Repository> reposetories;

    public RepositoriesAdapter() {
        this.reposetories = Collections.emptyList();
    }

    public void setReposetories(List<Repository> users) {
        this.reposetories = users;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repo, parent, false);
        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        Repository repository = reposetories.get(position);
        holder.repoTitle.setText(repository.getName());
        holder.repoDescription.setText(repository.getDescription());
        holder.forks.setText(Integer.toString(repository.getForks()));
        holder.watchers.setText(Integer.toString(repository.getWatchers()));
        holder.stars.setText(Integer.toString(repository.getStars()));
    }

    @Override
    public int getItemCount() {
        return reposetories.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layout_content)
        View contentLayout;
        @Bind(R.id.text_repo_title)
        TextView repoTitle;
        @Bind(R.id.text_repo_description)
        TextView repoDescription;
        @Bind(R.id.text_forks)
        TextView forks;
        @Bind(R.id.text_watchers)
        TextView watchers;
        @Bind(R.id.text_stars)
        TextView stars;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
