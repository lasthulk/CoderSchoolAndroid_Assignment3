package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.adapters.TweetsAdapter;
import com.codepath.apps.twitter.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

//import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private static final String TAG = TimelineActivity.class.getSimpleName();

    ArrayList<Tweet> tweetArrayList;
    TwitterClient client;
    @Bind(R.id.rvTweets)
    RecyclerView rvTweets;
    TweetsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        rvTweets.setItemAnimator(new SlideInUpAnimator());
        this.tweetArrayList = new ArrayList<>();
        this.client = TwitterApplication.getRestClient();
//        getTimelineData();
        adapter = new TweetsAdapter(this.tweetArrayList);
        rvTweets.setAdapter(adapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayout.scrollToPosition(0);
        rvTweets.setLayoutManager(linearLayout);
//        rvTweets.setHasFixedSize(true);
        getTimelineData();
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getTimelineData(page, totalItemsCount);
            }
        });
    }

    private void getTimelineData() {
        getTimelineData(0, 25);
    }

    private void getTimelineData(int page, int totalItemsCount) {
        this.client.getHomeTimeline(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, "onSuccess: " + response.toString());
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                tweetArrayList.addAll(tweets);
                int currentSize = adapter.getItemCount();
                adapter.notifyItemRangeInserted(currentSize, tweets.size());
                //rvTweets.scrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure: " + errorResponse.toString());
            }
        });
    }
}
