// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitter.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TimelineActivity$$ViewBinder<T extends com.codepath.apps.twitter.activities.TimelineActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492987, "field 'rvTweets'");
    target.rvTweets = finder.castView(view, 2131492987, "field 'rvTweets'");
    view = finder.findRequiredView(source, 2131492978, "field 'bnOpenCompose'");
    target.bnOpenCompose = finder.castView(view, 2131492978, "field 'bnOpenCompose'");
  }

  @Override public void unbind(T target) {
    target.rvTweets = null;
    target.bnOpenCompose = null;
  }
}
