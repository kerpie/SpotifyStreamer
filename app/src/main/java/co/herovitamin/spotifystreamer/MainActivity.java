package co.herovitamin.spotifystreamer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.herovitamin.spotifystreamer.adapters.ArtistAdapter;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.artist_list)
    RecyclerView artist_list;
    @Bind(R.id.error_message)
    TextView error_message;
    @Bind(R.id.artist_loader)
    ProgressBar loader;
    @Bind(R.id.search_query)
    EditText query;
    @Bind(R.id.main_toolbar)
    Toolbar main_toolbar;

    ArtistAdapter artist_adapter;
    RecyclerView.LayoutManager layout_manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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

                    boolean is_network_available = check_connectivity();
                    if(is_network_available)
                        new SearchArtist(query.getText().toString()).execute();
                    else
                        Toast.makeText(MainActivity.this, R.string.connection_error, Toast.LENGTH_SHORT ).show();

                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(query.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    private boolean check_connectivity() {

        ConnectivityManager connectivity_manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivity_manager.getActiveNetworkInfo();

        return network != null && network.isConnectedOrConnecting();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {



        super.onSaveInstanceState(outState);
    }

    public class SearchArtist extends AsyncTask<Void, Integer, Void>{

        ArtistsPager results;
        String artist_name;
        List<Artist> result_artists;

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

            result_artists = results.artists.items;

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