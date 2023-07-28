package me.pickleswine.simplerootblockbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VidPlayer extends AppCompatActivity {
    @Override
    public void onBackPressed() {

        Bin.instance.player.stop();
        Bin.instance.player.release();
        Bin.instance.player=null;
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid_player);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Bin.instance.player != null){
            Bin.instance.player.stop();
            Bin.instance.player.release();
        }
        Bin.instance.player = new ExoPlayer.Builder(this).build();
        StyledPlayerView exoPlayerView = findViewById(R.id.player_view);


        exoPlayerView.setPlayer(Bin.instance.player);


        Bin.instance.player.addMediaItem(MediaItem.fromUri(getIntent().getStringExtra("uri")));
        Bin.instance.player.prepare();
        Bin.instance.player.play();
    }
}