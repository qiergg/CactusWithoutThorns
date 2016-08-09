package com.weifeng_cactus.moon;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by maiya on 16/8/8.
 */
public class AudioPlayer
{

    private MediaPlayer mMediaPlayer;

    public void stop(){
        if(mMediaPlayer!=null){
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }
    public void play(Context context){
        stop();


        mMediaPlayer = MediaPlayer.create(context, R.raw.one_small_step);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });
        mMediaPlayer.start();
    }
}
