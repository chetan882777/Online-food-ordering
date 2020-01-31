package com.example.github.ui.main.repos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.github.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoViewHolder> {

    private RepoItemClickListener listener;

    public ReposAdapter(RepoItemClickListener listener) {
        this.listener = listener;
    }

    interface RepoItemClickListener{
        void onRepoClick(String repoUrl);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView nameTextView;
        final TextView accessTextView;
        final TextView desriptionTextView;
        String repoUrl;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.repoName_textView);
            accessTextView = itemView.findViewById(R.id.repoAccess_textView);
            desriptionTextView = itemView.findViewById(R.id.repoDiscription_textView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRepoClick(repoUrl);
        }
    }


}
