package com.test.genesis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ankoma88.newsreader.R;
import com.test.genesis.fragments.NewsFragment;
import com.test.genesis.interfaces.NewsLoadListener;
import com.test.genesis.model.Post;
import com.test.genesis.rest.RestClient;
import com.test.genesis.rest.model.BaseModel;
import com.test.genesis.rest.model.Results;

import java.text.DateFormat;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by ankoma88 on 18.06.16.
 */
public class NewsActivity extends AppCompatActivity implements NewsLoadListener, Callback<BaseModel> {
    public final static String TAG = NewsActivity.class.getSimpleName();
    private static final String TAG_NEWS_FRAGMENT = "newsFragment";

    private RestClient restClient;
    private NewsFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient(this);

        setContentView(R.layout.activity_news);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        showFragment(savedInstanceState);
    }

    private void showFragment(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragment = new NewsFragment();

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.container, fragment, TAG_NEWS_FRAGMENT)
                    .commit();
        } else {
            fragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag(TAG_NEWS_FRAGMENT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void loadNewsFeed() {
        restClient.loadData();
    }

    @Override
    public void openUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(browserIntent);
    }

    @Override
    public void onResponse(Response<BaseModel> response, Retrofit retrofit) {
        BaseModel baseModel = response.body();
        Results[] results = baseModel.results;
        ArrayList<Post> news = new ArrayList<>();
        for (Results result : results) {
            String formattedDate = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .format(Long.parseLong(result.date));
            final Post post = new Post(result.title, formattedDate, result.url);
            news.add(post);
        }

        if (fragment != null) {
            fragment.onDataLoaded(news);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        Log.e(TAG, throwable.getLocalizedMessage());
    }
}
