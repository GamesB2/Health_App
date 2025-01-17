package com.example.b016104b.healthapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b016104b.healthapplication.Activities.MainActivity;
import com.example.b016104b.healthapplication.R;
import com.example.b016104b.healthapplication.app.AppConfig;
import com.example.b016104b.healthapplication.Helper.RemoteSQLHandler;
import com.example.b016104b.healthapplication.Helper.Post;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;

public class MultiMedia extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    // YouTube player view
    private YouTubePlayerView youTubeView;
    private Button btnClose;
    private TextView txtSummary;
    private TextView txtMoreInfo;
    private TextView txtName;
    private boolean open = false;
    private Post p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.shop_fragment);

//        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
//        txtSummary = (TextView) findViewById(R.id.txtAbout);
//        txtMoreInfo = (TextView) findViewById(R.id.txtMoreInfo);
//        txtName = (TextView) findViewById(R.id.txtInfoName);
        final String method="register";


        Intent mIntent1 = getIntent();
        final int intValue = mIntent1.getIntExtra("id", 0);

//        if( intValue > 0)
//        {
//            for(int i =0;i< DatabaseRetrieval.postsAl.size();i++)
//            {
//                if(DatabaseRetrieval.postsAl.get(i).getIdI() == intValue)
//                {
//                    p = DatabaseRetrieval.postsAl.get(i);
//                }
//            }
//
//        }
//        else
//        {
//            p = DatabaseRetrieval.postsAl.get(2);
//        }
      
        txtSummary.setText(p.getSummary());
        txtName.setText(p.getName());


        // Initializing video player with developer key
        youTubeView.initialize(AppConfig.DEVELOPER_KEY, this);
        //btnClose = (Button)findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//                BackgroundTaskPosts backgroundTask=new BackgroundTaskPosts(MultiMedia.this);
//
//                backgroundTask.execute(method, MainActivity.userDetails.getEmail(), p.getId());

                Toast.makeText(getApplicationContext(),
                        "You Sucessfully Added This Post To Your Collection:\n" + p.getName(),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

        txtMoreInfo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (open){
                    txtMoreInfo.setText("see more...");
                    open = false;
                }else {
                    txtMoreInfo.setText(p.getTxt());
                    open = true;
                }
            }
        });

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
//            String errorMessage = String.format(
//                    getString(R.string.error_player), errorReason.toString());
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            //player.loadVideo(AppConfig.YOUTUBE_VIDEO_CODE);
            player.loadVideo(p.getVideo());

            // Hiding player controls
            player.setPlayerStyle(PlayerStyle.CHROMELESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(AppConfig.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        //return (YouTubePlayerView) findViewById(R.id.youtube_view);
        return null;
    }

}