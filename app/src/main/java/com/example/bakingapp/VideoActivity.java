package com.example.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.ui.PlayerControlView;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener, PlaybackPreparer, PlayerControlView.VisibilityListener {
    @Override
    public void onClick(View v) {
        
    }

    @Override
    public void preparePlayback() {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }
}
