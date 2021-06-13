/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener mcomplete=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }

        }
    };
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words = new ArrayList<word>();
        words.add(new word("Where are you going?", "minto wuksus",-1,R.raw.phrase_where_are_you_going));
        words.add(new word("What is your name?", "tinnә oyaase'nә",-1,R.raw.phrase_what_is_your_name));
        words.add(new word("My name is...", "oyaaset...",-1,R.raw.phrase_my_name_is));
        words.add(new word("How are you feeling?", "michәksәs?",-1,R.raw.phrase_how_are_you_feeling));
        words.add(new word("I’m feeling good.", "kuchi achit",-1,R.raw.phrase_im_feeling_good));
        words.add(new word("Are you coming?", "әәnәs'aa?",-1,R.raw.phrase_are_you_coming));
        words.add(new word("Yes, I’m coming.", "hәә’ әәnәm",-1,R.raw.phrase_yes_im_coming));
        words.add(new word("I’m coming.", "әәnәm",-1,R.raw.phrase_im_coming));
        words.add(new word("Let’s go.", "yoowutis",-1,R.raw.phrase_lets_go));
        words.add(new word("Come here", "әnni'nem",-1,R.raw.phrase_come_here));
        WordAdapter itemAdapter = new WordAdapter(this, words,R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, words.get(position).getMediaResource());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mcomplete);
                }
            }

        });
    }
}
