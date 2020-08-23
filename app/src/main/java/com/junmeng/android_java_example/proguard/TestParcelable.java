package com.junmeng.android_java_example.proguard;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 虽然此类没有被任何人使用，但由于依赖的mylibrary的consumber-rules.pro的配置
 * -keep class * implements android.os.Parcelable {
 *   public static final android.os.Parcelable$Creator *;
 * }
 * 因此此类会被保留下来，不会被优化掉，因此mylibrary的consumber-rules.pro是不能随意配置的，最好只配置和它自己有关的
 */
public class TestParcelable implements Parcelable {
    public String name="test";

    public TestParcelable(){

    }

    protected TestParcelable(Parcel in) {
        name = in.readString();
    }

    public static final Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
        @Override
        public TestParcelable createFromParcel(Parcel in) {
            return new TestParcelable(in);
        }

        @Override
        public TestParcelable[] newArray(int size) {
            return new TestParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
