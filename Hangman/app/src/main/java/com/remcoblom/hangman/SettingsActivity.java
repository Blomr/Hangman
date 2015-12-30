/**
 * SettingsActivity.java
 *
 * User can change game preferences.
 * Word length, incorrect guesses and game mode are adjustable.
 */

package com.remcoblom.hangman;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity{

    SeekBar incGuessSeekbar;
    SeekBar wordLengthSeekbar;
    TextView numIncGuess;
    TextView numWordLength;
    Switch evilSwitch;
    boolean evil;
    SharedPreferences settingsSharedPref;

    /**
     * Create activity and set onCheckedChangeListener on evil mode switch button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // initialize layout variables
        incGuessSeekbar = (SeekBar) findViewById(R.id.seekbar1);
        numIncGuess = (TextView) findViewById(R.id.numIncGuess);
        wordLengthSeekbar = (SeekBar) findViewById(R.id.seekbar2);
        numWordLength = (TextView) findViewById(R.id.numWordLength);
        evilSwitch = (Switch) findViewById(R.id.evilSwitch);

        // get shared preferences
        settingsSharedPref = getSharedPreferences("settings", MODE_PRIVATE);
        int incGuess = settingsSharedPref.getInt("incGuess", 9);
        int wordLength = settingsSharedPref.getInt("wordLength", 5);
        evil = settingsSharedPref.getBoolean("evilMode", true);

        // set shared preferences in layout
        incGuessSeekbar.setProgress(incGuess);
        wordLengthSeekbar.setProgress(wordLength - 1);
        String stringIncGuess = Integer.toString(incGuess);
        numIncGuess.setText(stringIncGuess);
        String stringWordLength = Integer.toString(wordLength);
        numWordLength.setText(stringWordLength);
        evilSwitch.setChecked(evil);

        // seekbar handler for word length and incorrect guesses
        seekBarHandler(incGuessSeekbar, numIncGuess, 0);
        seekBarHandler(wordLengthSeekbar, numWordLength, 1);

        // set onCheckedChangeListener on evil mode switch button
        evilSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                evil = isChecked;
            }
        });
    }

    /**
     * Handles changes in seekbar and displays accessory value
     */
    private void seekBarHandler (SeekBar seekBar, final TextView num, final int min) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            String stringProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stringProgress = Integer.toString(progress + min);
                num.setText(stringProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /**
     * If ok button is pressed, save changes settings in shared preferences
     * and go back to gameplay.
     */
    public void onClickOkSettings(View view) {
        settingsSharedPref = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor spEditor = settingsSharedPref.edit();
        spEditor.putInt("incGuess", incGuessSeekbar.getProgress());
        spEditor.putInt("wordLength", wordLengthSeekbar.getProgress() + 1);
        spEditor.putBoolean("evilMode", evil);
        spEditor.apply();
        finish();
    }

    /**
     * If cancel button is pressed, save nothing and go back to gameplay.
     */
    public void onClickCancelSettings(View view) {
        finish();
    }
}