package co.herovitamin.spotifystreamer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.herovitamin.spotifystreamer.R;
import co.herovitamin.spotifystreamer.TopTracksActivity;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

public class TopTrackAdapter extends RecyclerView.Adapter<TopTrackAdapter.ViewHolder> {

    List<Track> tracks;
    Context context;

    public TopTrackAdapter(Context context, List<Track> tracks) {
        this.context = context;
        this.tracks = tracks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView track_name;
        public TextView track_album;
        public ImageView track_image;
        public ViewHolder(View v) {
            super(v);
            track_name = (TextView) v.findViewById(R.id.song_title);
            track_album = (TextView) v.findViewById(R.id.album_name);
            track_image = (ImageView) v.findViewById(R.id.top_track_album);
        }
    }

    @Override
    public TopTrackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_track, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = tracks.get(position).name == null ? "no name" : tracks.get(position).name;
        String album_name = tracks.get(position).album.name == null ? "no album" : tracks.get(position).album.name;

        holder.track_name.setText(name);
        holder.track_album.setText(album_name);
        if(tracks.get(position).album.images.size() > 0){
            Picasso.with(context)
                    .load(tracks.get(position).album.images.get(0).url)
                    .into(holder.track_image);
        }
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }
}