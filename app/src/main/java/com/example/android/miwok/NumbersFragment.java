package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {


    public NumbersFragment() {
        // Required empty public constructor
    }

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                // Pause playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playbac
                mediaPlayer. start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }

        }
    };

    /**
     * this listner  gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
       //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        String [] numbersEnglish2 = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine", "Ten"};
        String [] numbersMiwok2 = {"Lutti" , "oṭiiko", "tolookosu","oyyiisa" ,"massokka","temmokka","kenekaku","kawinṭa","wo'e","na'aacha"};
        int [] numbersPics ={R.drawable.number_one,R.drawable.number_two,R.drawable.number_three , R.drawable.number_four, R.drawable.number_five, R.drawable.number_six, R.drawable.number_seven,R.drawable.number_eight,R.drawable.number_nine,R.drawable.number_ten};
        final int [] numbersAudio={R.raw.number_one,R.raw.number_two,R.raw.number_three , R.raw.number_four, R.raw.number_five, R.raw.number_six, R.raw.number_seven,R.raw.number_eight,R.raw.number_nine,R.raw.number_ten};
        final ArrayList<Word> numbers = new ArrayList<Word>();
        for(int i =0; i<numbersEnglish2.length ; i++) {
            numbers.add(new Word(numbersEnglish2[i],numbersMiwok2[i],numbersPics[i],numbersAudio[i]));
        }
        WordAdapter adapter = new WordAdapter(getActivity(), numbers , R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Word word=numbers.get(position);
                // Release the media player if it currently exists because we are about to
                //  play a different  media file
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT      );

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    /**
     * Clean up the media player by releasing its resources.
     */
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

}
