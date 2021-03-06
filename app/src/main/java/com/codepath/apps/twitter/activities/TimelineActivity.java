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
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.network.TwitterApplication;
import com.codepath.apps.twitter.network.TwitterClient;
import com.codepath.apps.twitter.adapters.TweetsAdapter;
import com.codepath.apps.twitter.helpers.NetworkHelper;
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
    long maxId = 0;

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
//                Log.d(TAG, "onLoadMore page: " + String.valueOf(page));
//                getTimelineData(page, totalItemsCount);
                if (tweetArrayList.size() > 0) {
                    maxId = tweetArrayList.get(tweetArrayList.size()-1).getUid();
                }
                getTimelineData(maxId, totalItemsCount);
            }
        });
        getDefaultTimeline();
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
            if (!NetworkHelper.isOnline()) {
                Toast.makeText(TimelineActivity.this, "Cannot connect to Internet", Toast.LENGTH_SHORT).show();
                return;
            }
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

    private void getDefaultTimeline() {
        getTimelineData(0, 25);
//        tweetArrayList.clear();
//        tweetsAdapter.notifyItemRangeRemoved(0, 25);
//        rvTweets.smoothScrollToPosition(0);
    }

    ArrayList<Tweet> newTweets;

    private void getTimelineData(long maxId, int totalItemsCount) {
        try {
            if (!NetworkHelper.isOnline()) {
                Toast.makeText(TimelineActivity.this, "Cannot connect to Internet", Toast.LENGTH_SHORT).show();
                return;
            }
            this.client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Log.d(TAG, "onSuccess: " + response.toString());
                    newTweets = Tweet.fromJSONArray(response);
//                ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
//                tweetArrayList.addAll(tweets);
//                int currentSize = tweetsAdapter.getItemCount();
//                tweetsAdapter.notifyItemRangeInserted(currentSize, tweets.size() - 1);
                    //rvTweets.scrollToPosition(tweetsAdapter.getItemCount() - 1);

                    tweetArrayList.addAll(newTweets);
                    int currentSize = tweetsAdapter.getItemCount();
                    tweetsAdapter.notifyItemRangeInserted(currentSize, newTweets.size() - 1);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d(TAG, "onFailure: " + errorResponse.toString());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
