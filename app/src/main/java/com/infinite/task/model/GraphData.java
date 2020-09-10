package com.infinite.task.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GraphData implements Parcelable {

    private String description;
    private float value;

    private String title;

    protected GraphData(Parcel in) {
        description = in.readString();
        value = in.readFloat();
        title = in.readString();
    }

    public static final Creator<GraphData> CREATOR = new Creator<GraphData>() {
        @Override
        public GraphData createFromParcel(Parcel in) {
            return new GraphData(in);
        }

        @Override
        public GraphData[] newArray(int size) {
            return new GraphData[size];
        }
    };

    public GraphData(String title, float value, String description) {
        this.title = title;
        this.value = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getvalue() {
        return value;
    }

    public void setvalue(float value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeFloat(value);
        parcel.writeString(title);
    }
}
