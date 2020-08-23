package com.junmeng.mylibrary;

import android.os.Parcel;
import android.os.Parcelable;

public class TestParceable implements Parcelable {

    public String name;
    public int age;

    public TestParceable() {

    }

    protected TestParceable(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<TestParceable> CREATOR = new Creator<TestParceable>() {
        @Override
        public TestParceable createFromParcel(Parcel in) {
            return new TestParceable(in);
        }

        @Override
        public TestParceable[] newArray(int size) {
            return new TestParceable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
