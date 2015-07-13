package co.herovitamin.spotifystreamer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.Map;

import co.herovitamin.spotifystreamer.adapters.TopTrackAdapter;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;


public class TopTracksActivity extends AppCompatActivity {

    String artist_name, artist_image, artist_id;
    Toolbar top_tracks_toolbar;
    RecyclerView top_tracks;
    RecyclerView.LayoutManager layout_manager;
    TextView error_message;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);

        top_tracks_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        top_tracks = (RecyclerView) findViewById(R.id.top_tracks_list);
        error_message = (TextView) findViewById(R.id.error_message);
        loader = (ProgressBar) findViewById(R.id.artist_loader);

        artist_name = getIntent().getStringExtra("artist_name");
        artist_id = getIntent().getStringExtra("artist_id");
        artist_image = getIntent().getStringExtra("artist_image");

        layout_manager = new LinearLayoutManager(this);
        top_tracks.setLayoutManager(layout_manager);

        setSupportActionBar(top_tracks_toolbar);
        setTitle(artist_name + "'s top tracks");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new SearchTopTracks().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SearchTopTracks extends AsyncTask<Void, Integer, Void>{

        Tracks tracks_result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            error_message.setVisibility(View.GONE);
            top_tracks.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            Map<String, Object> options =new Hashtable<String, Object>();
            options.put("country", "US");
            tracks_result = spotify.getArtistTopTrack(artist_id, options);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(tracks_result.tracks != null && tracks_result.tracks.size() > 0) {
                top_tracks.setAdapter(new TopTrackAdapter(TopTracksActivity.this, tracks_result.tracks));
                error_message.setVisibility(View.GONE);
                top_tracks.setVisibility(View.VISIBLE);
            }
            else{
                error_message.setVisibility(View.VISIBLE);
                error_message.setText(R.string.top_error_message);
                top_tracks.setVisibility(View.GONE);
            }
            loader.setVisibility(View.GONE);
        }
    }
}
