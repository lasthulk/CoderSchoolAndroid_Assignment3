package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApplication;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.apps.twitter.adapters.TweetsAdapter;
import com.codepath.apps.twitter.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

//import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private static final String TAG = TimelineActivity.class.getSimpleName();
    private static final int CREATE_TWEET_CODE = 305;
    static ArrayList<Tweet> tweetArrayList = new ArrayList<>();
    TwitterClient client;
    @Bind(R.id.rvTweets)
    RecyclerView rvTweets;

    @Bind(R.id.bnOpenCompose)
    FloatingActionButton bnOpenCompose;

    TweetsAdapter tweetsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        composeTweet();

        rvTweets.setItemAnimator(new SlideInUpAnimator());
        this.client = TwitterApplication.getRestClient();
//        getTimelineData();
        tweetsAdapter = new TweetsAdapter(tweetArrayList);
        rvTweets.setAdapter(tweetsAdapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayout.scrollToPosition(0);
        rvTweets.setLayoutManager(linearLayout);
//        rvTweets.setHasFixedSize(true);

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getTimelineData(page, totalItemsCount);
            }
        });
        getTimelineData();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_TWEET_CODE && resultCode == RESULT_OK) {
            User user = (User) Parcels.unwrap(data.getParcelableExtra("user"));
            String tweetContent = data.getStringExtra("tweetConent");
            final Tweet newTweet = new Tweet();
            newTweet.setUser(user);
            newTweet.setBody(tweetContent);

            tweetArrayList.add(0, newTweet);
            tweetsAdapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);
            client.postNewTweet(newTweet.getBody(), new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        super.onSuccess(statusCode, headers, response);
                        int previousTweetIndex = tweetArrayList.indexOf(newTweet);
                        tweetArrayList.set(previousTweetIndex, Tweet.fromJson(response));
                        tweetsAdapter.notifyItemChanged(previousTweetIndex);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        Log.d(TAG, "onFailure: " + errorResponse.toString());
                        tweetArrayList.remove(0);
                        tweetsAdapter.notifyItemRemoved(0);
                        rvTweets.scrollToPosition(0);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    private void composeTweet() {
        bnOpenCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                startActivityForResult(i, CREATE_TWEET_CODE);
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
                int currentSize = tweetsAdapter.getItemCount();
                tweetsAdapter.notifyItemRangeInserted(currentSize, tweets.size());
                //rvTweets.scrollToPosition(tweetsAdapter.getItemCount() - 1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG, "onFailure: " + errorResponse.toString());
            }
        });
    }
}
