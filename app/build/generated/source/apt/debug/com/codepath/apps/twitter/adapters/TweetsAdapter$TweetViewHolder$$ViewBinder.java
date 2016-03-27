// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitter.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TweetsAdapter$TweetViewHolder$$ViewBinder<T extends com.codepath.apps.twitter.adapters.TweetsAdapter.TweetViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427447, "field 'ivProfileImage'");
    target.ivProfileImage = finder.castView(view, 2131427447, "field 'ivProfileImage'");
    view = finder.findRequiredView(source, 2131427449, "field 'tvBody'");
    target.tvBody = finder.castView(view, 2131427449, "field 'tvBody'");
    view = finder.findRequiredView(source, 2131427448, "field 'tvUserName'");
    target.tvUserName = finder.castView(view, 2131427448, "field 'tvUserName'");
  }

  @Override public void unbind(T target) {
    target.ivProfileImage = null;
    target.tvBody = null;
    target.tvUserName = null;
  }
}
