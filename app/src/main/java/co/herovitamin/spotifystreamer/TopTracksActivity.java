package co.herovitamin.spotifystreamer;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.herovitamin.spotifystreamer.adapters.TopTrackAdapter;
import co.herovitamin.spotifystreamer.interfaces.OnTaskDone;
import co.herovitamin.spotifystreamer.models.MyTrack;
import co.herovitamin.spotifystreamer.tracks.SearchTopTracks;


public class TopTracksActivity extends AppCompatActivity implements OnTaskDone{

    @Bind(R.id.main_toolbar)
    Toolbar top_tracks_toolbar;
    @Bind(R.id.top_tracks_list)
    RecyclerView top_tracks;
    @Bind(R.id.error_message)
    TextView error_message;
    @Bind(R.id.artist_loader)
    ProgressBar loader;

    RecyclerView.LayoutManager layout_manager;
    String artist_name, artist_image, artist_id;
    ArrayList<MyTrack> my_tracks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);

        ButterKnife.bind(this);

        artist_name = getIntent().getStringExtra("artist_name");
        artist_id = getIntent().getStringExtra("artist_id");
        artist_image = getIntent().getStringExtra("artist_image");

        layout_manager = new LinearLayoutManager(this);
        top_tracks.setLayoutManager(layout_manager);

        setSupportActionBar(top_tracks_toolbar);
        setTitle(artist_name + "'s top tracks");

        if(savedInstanceState != null){
            my_tracks = savedInstanceState.getParcelableArrayList("result");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(my_tracks != null){
            top_tracks.setAdapter(new TopTrackAdapter(TopTracksActivity.this, my_tracks));
        }
        else{
            boolean is_network_available = check_connectivity();
            if(is_network_available)
                new SearchTopTracks(this, artist_name, artist_id).execute();
            else
                Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if(my_tracks != null)
            outState.putParcelableArrayList("result", my_tracks);

        super.onSaveInstanceState(outState);
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

    private boolean check_connectivity() {

        ConnectivityManager connectivity_manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivity_manager.getActiveNetworkInfo();

        return network != null && network.isConnectedOrConnecting();
    }

    @Override
    public void onPrepareStuff() {
        error_message.setVisibility(View.GONE);
        top_tracks.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskDone(ArrayList<MyTrack> tracks_result) {
        if(my_tracks != null) {
            my_tracks = tracks_result;
            if (tracks_result != null && tracks_result.size() > 0) {
                top_tracks.setAdapter(new TopTrackAdapter(TopTracksActivity.this, my_tracks));
                error_message.setVisibility(View.GONE);
                top_tracks.setVisibility(View.VISIBLE);
            } else {
                error_message.setVisibility(View.VISIBLE);
                error_message.setText(R.string.top_error_message);
                top_tracks.setVisibility(View.GONE);
            }
            loader.setVisibility(View.GONE);
        }
    }
}
