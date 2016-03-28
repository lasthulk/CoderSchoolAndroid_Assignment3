
package com.codepath.apps.twitter.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.ParcelWrapper;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2016-03-28T20:33+0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class User$$Parcelable
    implements Parcelable, ParcelWrapper<com.codepath.apps.twitter.models.User>
{

    private com.codepath.apps.twitter.models.User user$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static User$$Parcelable.Creator$$0 CREATOR = new User$$Parcelable.Creator$$0();

    public User$$Parcelable(android.os.Parcel parcel$$0) {
        com.codepath.apps.twitter.models.User user$$2;
        if (parcel$$0 .readInt() == -1) {
            user$$2 = null;
        } else {
            user$$2 = readcom_codepath_apps_twitter_models_User(parcel$$0);
        }
        user$$0 = user$$2;
    }

    public User$$Parcelable(com.codepath.apps.twitter.models.User user$$4) {
        user$$0 = user$$4;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$1, int flags) {
        if (user$$0 == null) {
            parcel$$1 .writeInt(-1);
        } else {
            parcel$$1 .writeInt(1);
            writecom_codepath_apps_twitter_models_User(user$$0, parcel$$1, flags);
        }
    }

    private com.codepath.apps.twitter.models.User readcom_codepath_apps_twitter_models_User(android.os.Parcel parcel$$2) {
        com.codepath.apps.twitter.models.User user$$1;
        user$$1 = new com.codepath.apps.twitter.models.User();
        user$$1 .uid = parcel$$2 .readLong();
        user$$1 .name = parcel$$2 .readString();
        user$$1 .screenName = parcel$$2 .readString();
        user$$1 .profileImageUrl = parcel$$2 .readString();
        return user$$1;
    }

    private void writecom_codepath_apps_twitter_models_User(com.codepath.apps.twitter.models.User user$$3, android.os.Parcel parcel$$3, int flags$$0) {
        parcel$$3 .writeLong(user$$3 .uid);
        parcel$$3 .writeString(user$$3 .name);
        parcel$$3 .writeString(user$$3 .screenName);
        parcel$$3 .writeString(user$$3 .profileImageUrl);
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.codepath.apps.twitter.models.User getParcel() {
        return user$$0;
    }

    public final static class Creator$$0
        implements Creator<User$$Parcelable>
    {


        @Override
        public User$$Parcelable createFromParcel(android.os.Parcel parcel$$4) {
            return new User$$Parcelable(parcel$$4);
        }

        @Override
        public User$$Parcelable[] newArray(int size) {
            return new User$$Parcelable[size] ;
        }

    }

}
