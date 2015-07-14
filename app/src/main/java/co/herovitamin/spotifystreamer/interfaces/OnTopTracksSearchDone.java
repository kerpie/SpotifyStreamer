package co.herovitamin.spotifystreamer.interfaces;

import java.util.ArrayList;

import co.herovitamin.spotifystreamer.models.MyTrack;

public interface OnTopTracksSearchDone {
    void onPrepareStuff();
    void onTaskDone(ArrayList<MyTrack> result);
}
