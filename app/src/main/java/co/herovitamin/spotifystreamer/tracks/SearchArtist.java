package co.herovitamin.spotifystreamer.tracks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.herovitamin.spotifystreamer.R;
import co.herovitamin.spotifystreamer.adapters.ArtistAdapter;
import co.herovitamin.spotifystreamer.interfaces.OnArtistSearchDone;
import co.herovitamin.spotifystreamer.models.MyArtist;
import co.herovitamin.spotifystreamer.models.MyTrack;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;

public class SearchArtist extends AsyncTask<Void, Integer, Void> {

    ArtistsPager results;
    String artist_name;
    ArrayList<MyArtist> artists = null;
    OnArtistSearchDone listener;

    public SearchArtist(OnArtistSearchDone listener, String artist_name){
        this.artist_name = artist_name;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.prepare_stuff_before_search_for_artist();
    }

    @Override
    protected Void doInBackground(Void... params) {

        SpotifyApi api = new SpotifyApi();

        SpotifyService spotify = api.getService();

        results = spotify.searchArtists(artist_name);

        if(results != null && results.artists.items.size() > 0) {
            artists = new ArrayList<>();
            for (Artist tmp_artist:results.artists.items){
                String tmp_url = tmp_artist.images.size() > 0 ? tmp_artist.images.get(0).url : null ;
                artists.add(new MyArtist(tmp_artist.id, tmp_artist.name, tmp_url));
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        listener.on_search_done(artists);

    }
}