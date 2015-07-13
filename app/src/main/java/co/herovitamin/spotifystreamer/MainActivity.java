package co.herovitamin.spotifystreamer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import co.herovitamin.spotifystreamer.adapters.ArtistAdapter;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

public class MainActivity extends AppCompatActivity {

    RecyclerView artist_list;
    ArtistAdapter artist_adapter;
    TextView error_message;
    ProgressBar loader;
    EditText query;

    RecyclerView.LayoutManager layout_manager;
    Toolbar main_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artist_list = (RecyclerView) findViewById(R.id.artist_list);
        main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        error_message = (TextView) findViewById(R.id.error_message);
        loader = (ProgressBar) findViewById(R.id.artist_loader);
        query = (EditText) findViewById(R.id.search_query);

        layout_manager = new LinearLayoutManager(this);
        artist_list.setLayoutManager(layout_manager);


        setSupportActionBar(main_toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    new SearchArtist(query.getText().toString()).execute();

                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(query.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    public class SearchArtist extends AsyncTask<Void, Integer, Void>{

        ArtistsPager results;
        String artist_name;

        public SearchArtist(String artist_name){
            this.artist_name = artist_name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            error_message.setVisibility(View.GONE);
            artist_list.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            SpotifyApi api = new SpotifyApi();

            SpotifyService spotify = api.getService();

            results = spotify.searchArtists(artist_name);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            List<Artist> result_artists = results.artists.items;
            if(result_artists != null && result_artists.size() > 0) {
                artist_adapter = new ArtistAdapter(result_artists, MainActivity.this);
                artist_list.setAdapter(artist_adapter);
                error_message.setVisibility(View.GONE);
                artist_list.setVisibility(View.VISIBLE);
                Log.i("RESULT", "encontrado");
                Log.i("RESULT", "encontrados: " + result_artists.size());
            }
            else{
                error_message.setVisibility(View.VISIBLE);
                error_message.setText(R.string.artist_error_message);
                artist_list.setVisibility(View.GONE);
                Log.i("RESULT", "no encontrado");
            }
            loader.setVisibility(View.GONE);
        }
    }

}