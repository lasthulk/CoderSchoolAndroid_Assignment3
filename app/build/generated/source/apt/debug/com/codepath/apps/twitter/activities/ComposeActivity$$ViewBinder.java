// Generated code from Butter Knife. Do not modify!
package com.codepath.apps.twitter.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ComposeActivity$$ViewBinder<T extends com.codepath.apps.twitter.activities.ComposeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492983, "field 'ivClose'");
    target.ivClose = finder.castView(view, 2131492983, "field 'ivClose'");
    view = finder.findRequiredView(source, 2131492984, "field 'etTweetInput'");
    target.etTweetInput = finder.castView(view, 2131492984, "field 'etTweetInput'");
    view = finder.findRequiredView(source, 2131492985, "field 'tvCharactersLeft'");
    target.tvCharactersLeft = finder.castView(view, 2131492985, "field 'tvCharactersLeft'");
    view = finder.findRequiredView(source, 2131492986, "field 'bnTweet'");
    target.bnTweet = finder.castView(view, 2131492986, "field 'bnTweet'");
    view = finder.findRequiredView(source, 2131492980, "field 'ivComposeProfileImage'");
    target.ivComposeProfileImage = finder.castView(view, 2131492980, "field 'ivComposeProfileImage'");
    view = finder.findRequiredView(source, 2131492982, "field 'tvComposeUserName'");
    target.tvComposeUserName = finder.castView(view, 2131492982, "field 'tvComposeUserName'");
    view = finder.findRequiredView(source, 2131492981, "field 'tvComposeName'");
    target.tvComposeName = finder.castView(view, 2131492981, "field 'tvComposeName'");
  }

  @Override public void unbind(T target) {
    target.ivClose = null;
    target.etTweetInput = null;
    target.tvCharactersLeft = null;
    target.bnTweet = null;
    target.ivComposeProfileImage = null;
    target.tvComposeUserName = null;
    target.tvComposeName = null;
  }
}
