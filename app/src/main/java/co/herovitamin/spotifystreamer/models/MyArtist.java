package co.herovitamin.spotifystreamer.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kerry on 14/07/15.
 */
public class MyArtist implements Parcelable {

    String id;
    String name;
    String image_url;

    public MyArtist(String id, String name, String image_url){
        this.id = id;
        this.name = name;
        this.image_url = image_url;
    }

    private MyArtist(Parcel in){
        id = in.readString();
        name = in.readString();
        image_url = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image_url);
    }

    public static final Parcelable.Creator<MyArtist> CREATOR = new Parcelable.Creator<MyArtist>() {
        public MyArtist createFromParcel(Parcel in) {
            return new MyArtist(in);
        }

        public MyArtist[] newArray(int size) {
            return new MyArtist[size];
        }
    };
}
