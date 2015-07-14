package co.herovitamin.spotifystreamer.interfaces;

import java.util.ArrayList;

import co.herovitamin.spotifystreamer.models.MyArtist;

/**
 * Created by kerry on 14/07/15.
 */
public interface OnArtistSearchDone {

    void prepare_stuff_before_search_for_artist();
    void on_search_done(ArrayList<MyArtist> result);

}
