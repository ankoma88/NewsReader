package com.test.genesis.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ankoma88.newsreader.R;
import com.test.genesis.model.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.UsersViewHolder> {
    private List<Post> news = new ArrayList<>();
    private final OnItemClickListener listener;

    public NewsAdapter(List<Post> news, OnItemClickListener listener) {
        if (news != null) {
            this.news = news;
        }
        this.listener = listener;
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTitle)
        public TextView tvTitle;
        @Bind(R.id.tvDate)
        public TextView tvDate;
        @Bind(R.id.tvUrl)
        public TextView tvUrl;

        public UsersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Post post, final OnItemClickListener listener) {
            tvTitle.setText(post.getTitle());
            tvDate.setText(post.getDate());
            tvUrl.setText(post.getUrl());

            itemView.setOnClickListener(v -> listener.onItemClick(post));
        }
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new UsersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        holder.bind(news.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Post post);
    }

}
