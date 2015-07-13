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

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private static List<Artist> artists;
    private static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView artist_name;
        public ImageView artist_image;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            artist_name = (TextView) v.findViewById(R.id.artist_name);
            artist_image = (ImageView) v.findViewById(R.id.artist_image);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent( context, TopTracksActivity.class);

            intent.putExtra("artist_name", artists.get(getAdapterPosition()).name);
            intent.putExtra("artist_id", artists.get(getAdapterPosition()).id);
            String url = artists.get(getAdapterPosition()).images.size() > 0 ? artists.get(getAdapterPosition()).images.get(0).url : "" ;
            intent.putExtra("artist_image", url);
            context.startActivity(intent);
        }
    }

    public ArtistAdapter(List<Artist> artists, Context context) {
        this.artists = artists;
        this.context = context;
    }

    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.artist_name.setText(artists.get(position).name);
        if(artists.get(position).images.size() > 0){
            Picasso.with(context)
                    .load(artists.get(position).images.get(0).url)
                    .into(holder.artist_image);
        }


    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}