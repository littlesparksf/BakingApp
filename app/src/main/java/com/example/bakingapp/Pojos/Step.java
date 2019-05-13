package com.example.bakingapp.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {

    protected Step(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoUrl = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    private int id;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    /** No args constructor for use in serialization */
    public Step(){
    }

    /** Constructor */

    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl){
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    /** Getters and Setters here */

    public int getId() {return id;}
    public void setId(int id){this.id = id;}

    public String getShortDescription() {return shortDescription;}
    public void setShortDescription (String shortDescription) {this.shortDescription = shortDescription;}

    public String getDescription() {return  description;}
    public void setDescription (String description) {this.description = description;}

    public String getVideoUrl() {return  videoUrl;}
    public void setVideoUrl (String videoUrl) {this.videoUrl = videoUrl;}

    public String getThumbnailUrl() {return  thumbnailUrl;}
    public void setThumbnailUrl (String thumbnailUrl) {this.thumbnailUrl = thumbnailUrl;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoUrl);
        dest.writeString(this.thumbnailUrl);
    }
}