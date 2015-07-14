package co.herovitamin.spotifystreamer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MyTrack implements Parcelable {

    String track_name;
    String track_album;
    String track_artist_name;
    String track_image_url;

    public MyTrack(String track_name, String track_album, String track_artist_name, String track_image_url){
        this.track_name = track_name;
        this.track_album = track_album;
        this.track_artist_name = track_artist_name;
        this.track_image_url = track_image_url;
    }

    private MyTrack(Parcel in) {
        track_name = in.readString();
        track_album= in.readString();
        track_artist_name = in.readString();
    }

    public String getTrack_name() {
        return track_name;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }

    public String getTrack_album() {
        return track_album;
    }

    public void setTrack_album(String track_album) {
        this.track_album = track_album;
    }

    public String getTrack_artist_name() {
        return track_artist_name;
    }

    public void setTrack_artist_name(String track_artist_name) {
        this.track_artist_name = track_artist_name;
    }

    public String getTrack_image_url() {
        return track_image_url;
    }

    public void setTrack_image_url(String track_image_url) {
        this.track_image_url = track_image_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(track_name);
        dest.writeString(track_album);
        dest.writeString(track_artist_name);
    }

    public static final Parcelable.Creator<MyTrack> CREATOR = new Parcelable.Creator<MyTrack>() {
        public MyTrack createFromParcel(Parcel in) {
            return new MyTrack(in);
        }

        public MyTrack[] newArray(int size) {
            return new MyTrack[size];
        }
    };
}