package com.test.genesis.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ankoma88.newsreader.R;
import com.test.genesis.adapters.NewsAdapter;
import com.test.genesis.interfaces.NewsLoadListener;
import com.test.genesis.model.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class NewsFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = NewsFragment.class.getSimpleName();
    private static final String NEWSPOSTS = "NewsPosts";

    private NewsLoadListener newsLoadListener;
    private ArrayList<Post> posts;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.rvPosts)
    RecyclerView rvPosts;

    public NewsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newsLoadListener = (NewsLoadListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        newsLoadListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPosts.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(this);

        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            posts = savedInstanceState.getParcelableArrayList(NEWSPOSTS);
            setAdapter(posts);
        } else {
            newsLoadListener.loadNewsFeed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(NEWSPOSTS, posts);
    }

    public void onDataLoaded(List<Post> postList) {
        Log.d(TAG, "onDataLoaded size: " + postList.size());
        this.posts = (ArrayList<Post>) postList;
        setAdapter(posts);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private void setAdapter(List<Post> postList) {
        final NewsAdapter adapter = new NewsAdapter(postList, post
                -> newsLoadListener.openUrl(post.getUrl()));
        rvPosts.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        newsLoadListener.loadNewsFeed();
    }
}
