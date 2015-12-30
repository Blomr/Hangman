/**
 * LoadingActivity.java
 *
 * Load words into memory.
 * Other activities have access by static arraylists.
 * After loading, screen changes to ending screen.
 */

package com.remcoblom.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity{

    static ArrayList<String> letters1 = new ArrayList<>();
    static ArrayList<String> letters2 = new ArrayList<>();
    static ArrayList<String> letters3 = new ArrayList<>();
    static ArrayList<String> letters4 = new ArrayList<>();
    static ArrayList<String> letters5 = new ArrayList<>();
    static ArrayList<String> letters6 = new ArrayList<>();
    static ArrayList<String> letters7 = new ArrayList<>();
    static ArrayList<String> letters8 = new ArrayList<>();
    static ArrayList<String> letters9 = new ArrayList<>();
    static ArrayList<String> letters10 = new ArrayList<>();
    static ArrayList<String> letters11 = new ArrayList<>();
    static ArrayList<String> letters12 = new ArrayList<>();
    static ArrayList<String> letters13 = new ArrayList<>();
    static ArrayList<String> letters14 = new ArrayList<>();
    static ArrayList<String> letters15 = new ArrayList<>();
    ImageView logo;
    TextView loading;
    TextView thanksForPlaying;
    ImageView loadingBG;

    /**
     * Create activity and load words from xml into arraylists. When ready, go to gameplay.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // initialize layout variables
        logo = (ImageView) findViewById(R.id.logo);
        loading = (TextView) findViewById(R.id.loading);
        thanksForPlaying = (TextView) findViewById(R.id.thanksForPlaying);
        loadingBG = (ImageView) findViewById(R.id.loadingBG);

        // load words into memory
        this.loadWords(R.array.words);

        // get game mode from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean evilMode = sharedPreferences.getBoolean("evilMode", true);

        // go to good or evil gameplay
        if (evilMode) {
            Intent goToEvilGameplay = new Intent(LoadingActivity.this, EvilGameplayActivity.class);
            startActivity(goToEvilGameplay);
        }
        else {
            Intent goToGoodGameplay = new Intent(LoadingActivity.this, GoodGameplayActivity.class);
            startActivity(goToGoodGameplay);
        }

        // change text to create ending screen
        loading.setText(getString(R.string.thanksForPlaying));
        thanksForPlaying.setVisibility(View.VISIBLE);

        // touch screen to close app
        loadingBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Takes words from xml and put them into arraylists of different word lengths.
     */
    private void loadWords(int wordList) {
        String[] words = getResources().getStringArray(wordList);

        // iterate over all words
        for (String wordsString : words) {
            int wordLength = wordsString.length();

            // depending on length of word, put word in right arraylist
            switch (wordLength) {
                case 1:
                    letters1.add(wordsString);
                    break;
                case 2:
                    letters2.add(wordsString);
                    break;
                case 3:
                    letters3.add(wordsString);
                    break;
                case 4:
                    letters4.add(wordsString);
                    break;
                case 5:
                    letters5.add(wordsString);
                    break;
                case 6:
                    letters6.add(wordsString);
                    break;
                case 7:
                    letters7.add(wordsString);
                    break;
                case 8:
                    letters8.add(wordsString);
                    break;
                case 9:
                    letters9.add(wordsString);
                    break;
                case 10:
                    letters10.add(wordsString);
                    break;
                case 11:
                    letters11.add(wordsString);
                    break;
                case 12:
                    letters12.add(wordsString);
                    break;
                case 13:
                    letters13.add(wordsString);
                    break;
                case 14:
                    letters14.add(wordsString);
                    break;
                default:
                    letters15.add(wordsString);
                    break;
            }
        }
    }
}