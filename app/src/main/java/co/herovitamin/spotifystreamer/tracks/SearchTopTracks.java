package co.herovitamin.spotifystreamer.tracks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import co.herovitamin.spotifystreamer.interfaces.OnTaskDone;
import co.herovitamin.spotifystreamer.models.MyTrack;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import retrofit.RetrofitError;

public class SearchTopTracks extends AsyncTask<Void, Integer, Void> {

    List<Track> tracks_result;
    OnTaskDone listener;
    ArrayList<MyTrack> my_tracks = null;

    String artist_id, artist_name;

    public SearchTopTracks(OnTaskDone listener, String artist_name, String artist_id){
        this.artist_id = artist_id;
        this.artist_name = artist_name;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPrepareStuff();
    }

    @Override
    protected Void doInBackground(Void... params) {

        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        Map<String, Object> options =new Hashtable<>();
        options.put("country", "US");
        try {
            tracks_result = spotify.getArtistTopTrack(artist_id, options).tracks;
        } catch (RetrofitError error){
            tracks_result = null;
        }
        if(tracks_result != null && tracks_result.size() > 0){
            my_tracks = new ArrayList<>();
            for (Track track:tracks_result){
                String tmp_url = track.album.images.size() > 0 ? null : track.album.images.get(0).url;
                my_tracks.add(new MyTrack(track.name, track.album.name, artist_name, tmp_url));
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        listener.onTaskDone(my_tracks);
    }
}