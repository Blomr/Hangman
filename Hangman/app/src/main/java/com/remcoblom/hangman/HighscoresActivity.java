/**
 * HighscoresActivity.java
 *
 * Shows highscore list at the end of a game.
 * Compares scores with scores in highscore list.
 * Places new highscores on the right place.
 * Saves scores in Shared Preferences.
 */

package com.remcoblom.hangman;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HighscoresActivity extends AppCompatActivity {

    EditText inputNamePlayer;
    Button submitButton;
    ImageView newButton;
    ImageView settingsButton;
    boolean okPressed;
    boolean win;
    String player1;
    String player2;
    String player3;
    String player4;
    String player5;
    String player6;
    String player7;
    String player8;
    String player9;
    String player10;
    String word1;
    String word2;
    String word3;
    String word4;
    String word5;
    String word6;
    String word7;
    String word8;
    String word9;
    String word10;
    String mode;
    int score1;
    int score2;
    int score3;
    int score4;
    int score5;
    int score6;
    int score7;
    int score8;
    int score9;
    int score10;
    int newScore;
    TextView player1View;
    TextView player2View;
    TextView player3View;
    TextView player4View;
    TextView player5View;
    TextView player6View;
    TextView player7View;
    TextView player8View;
    TextView player9View;
    TextView player10View;
    TextView word1View;
    TextView word2View;
    TextView word3View;
    TextView word4View;
    TextView word5View;
    TextView word6View;
    TextView word7View;
    TextView word8View;
    TextView word9View;
    TextView word10View;
    TextView score1View;
    TextView score2View;
    TextView score3View;
    TextView score4View;
    TextView score5View;
    TextView score6View;
    TextView score7View;
    TextView score8View;
    TextView score9View;
    TextView score10View;
    TextView modeView;
    SharedPreferences evilSharedPref;
    SharedPreferences goodSharedPref;
    SharedPreferences settingsSharedPref;
    SharedPreferences okPressedPref;
    SharedPreferences.Editor okPressedEdit;

    /**
     * Create activity, set onClickListener on new button and settings button.
     * Choose highscore list depending on game mode.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        // initialize layout variables
        inputNamePlayer = (EditText) findViewById(R.id.inputNamePlayer);
        submitButton = (Button) findViewById(R.id.okButtonHS);
        newButton = (ImageView) findViewById(R.id.newButtonHS);
        settingsButton = (ImageView) findViewById(R.id.settingsButtonHS);
        player1View = (TextView) findViewById(R.id.player1);
        player2View = (TextView) findViewById(R.id.player2);
        player3View = (TextView) findViewById(R.id.player3);
        player4View = (TextView) findViewById(R.id.player4);
        player5View = (TextView) findViewById(R.id.player5);
        player6View = (TextView) findViewById(R.id.player6);
        player7View = (TextView) findViewById(R.id.player7);
        player8View = (TextView) findViewById(R.id.player8);
        player9View = (TextView) findViewById(R.id.player9);
        player10View = (TextView) findViewById(R.id.player10);
        word1View = (TextView) findViewById(R.id.word1);
        word2View = (TextView) findViewById(R.id.word2);
        word3View = (TextView) findViewById(R.id.word3);
        word4View = (TextView) findViewById(R.id.word4);
        word5View = (TextView) findViewById(R.id.word5);
        word6View = (TextView) findViewById(R.id.word6);
        word7View = (TextView) findViewById(R.id.word7);
        word8View = (TextView) findViewById(R.id.word8);
        word9View = (TextView) findViewById(R.id.word9);
        word10View = (TextView) findViewById(R.id.word10);
        score1View = (TextView) findViewById(R.id.score1);
        score2View = (TextView) findViewById(R.id.score2);
        score3View = (TextView) findViewById(R.id.score3);
        score4View = (TextView) findViewById(R.id.score4);
        score5View = (TextView) findViewById(R.id.score5);
        score6View = (TextView) findViewById(R.id.score6);
        score7View = (TextView) findViewById(R.id.score7);
        score8View = (TextView) findViewById(R.id.score8);
        score9View = (TextView) findViewById(R.id.score9);
        score10View = (TextView) findViewById(R.id.score10);
        modeView = (TextView) findViewById(R.id.modeName);
        okPressedPref = getPreferences(MODE_PRIVATE);
        okPressedEdit = okPressedPref.edit();

        // set onClickListener on new button
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // show alert dialog before starting new game
                AlertDialog.Builder builder = new AlertDialog.Builder(HighscoresActivity.this);
                builder.setMessage("Start a new game?");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // before going to gampeplay, set pressed on ok button to false
                        okPressedEdit.putBoolean("okPressed", false);
                        okPressedEdit.apply();

                        // depending on settings, go to good or evil gameplay
                        settingsSharedPref = getSharedPreferences("settings", MODE_PRIVATE);
                        boolean mode = settingsSharedPref.getBoolean("evilMode", true);
                        if (mode) {
                            Intent goToEvilMode = new Intent(HighscoresActivity.this, EvilGameplayActivity.class);
                            startActivity(goToEvilMode);
                            finish();
                        }
                        else {
                            Intent goToGoodMode = new Intent(HighscoresActivity.this, GoodGameplayActivity.class);
                            startActivity(goToGoodMode);
                            finish();
                        }
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // set onClickListener on settings button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettings = new Intent(HighscoresActivity.this, SettingsActivity.class);
                startActivity(goToSettings);
            }
        });

        // take game mode from intent
        Intent getIntent = getIntent();
        mode = getIntent.getStringExtra("mode");
        modeView.setText(mode);

        // if game mode is evil, take evil highscores from shared preferences
        if (mode.equals("Evil mode")) {
            evilSharedPref = getSharedPreferences("evilHighscores", MODE_PRIVATE);

            player1 = evilSharedPref.getString("player1", "?");
            word1 = evilSharedPref.getString("word1", "?");
            score1 = evilSharedPref.getInt("score1", -1000);

            player2 = evilSharedPref.getString("player2", "?");
            word2 = evilSharedPref.getString("word2", "?");
            score2 = evilSharedPref.getInt("score2", -1000);

            player3 = evilSharedPref.getString("player3", "?");
            word3 = evilSharedPref.getString("word3", "?");
            score3 = evilSharedPref.getInt("score3", -1000);

            player4 = evilSharedPref.getString("player4", "?");
            word4 = evilSharedPref.getString("word4", "?");
            score4 = evilSharedPref.getInt("score4", -1000);

            player5 = evilSharedPref.getString("player5", "?");
            word5 = evilSharedPref.getString("word5", "?");
            score5 = evilSharedPref.getInt("score5", -1000);

            player6 = evilSharedPref.getString("player6", "?");
            word6 = evilSharedPref.getString("word6", "?");
            score6 = evilSharedPref.getInt("score6", -1000);

            player7 = evilSharedPref.getString("player7", "?");
            word7 = evilSharedPref.getString("word7", "?");
            score7 = evilSharedPref.getInt("score7", -1000);

            player8 = evilSharedPref.getString("player8", "?");
            word8 = evilSharedPref.getString("word8", "?");
            score8 = evilSharedPref.getInt("score8", -1000);

            player9 = evilSharedPref.getString("player9", "?");
            word9 = evilSharedPref.getString("word9", "?");
            score9 = evilSharedPref.getInt("score9", -1000);

            player10 = evilSharedPref.getString("player10", "?");
            word10 = evilSharedPref.getString("word10", "?");
            score10 = evilSharedPref.getInt("score10", -1000);
        }

        // if game mode is good, take good highscores from shared preferences
        else {
            goodSharedPref = getSharedPreferences("goodHighscores", MODE_PRIVATE);

            player1 = goodSharedPref.getString("player1", "?");
            word1 = goodSharedPref.getString("word1", "?");
            score1 = goodSharedPref.getInt("score1", -1000);

            player2 = goodSharedPref.getString("player2", "?");
            word2 = goodSharedPref.getString("word2", "?");
            score2 = goodSharedPref.getInt("score2", -1000);

            player3 = goodSharedPref.getString("player3", "?");
            word3 = goodSharedPref.getString("word3", "?");
            score3 = goodSharedPref.getInt("score3", -1000);

            player4 = goodSharedPref.getString("player4", "?");
            word4 = goodSharedPref.getString("word4", "?");
            score4 = goodSharedPref.getInt("score4", -1000);

            player5 = goodSharedPref.getString("player5", "?");
            word5 = goodSharedPref.getString("word5", "?");
            score5 = goodSharedPref.getInt("score5", -1000);

            player6 = goodSharedPref.getString("player6", "?");
            word6 = goodSharedPref.getString("word6", "?");
            score6 = goodSharedPref.getInt("score6", -1000);

            player7 = goodSharedPref.getString("player7", "?");
            word7 = goodSharedPref.getString("word7", "?");
            score7 = goodSharedPref.getInt("score7", -1000);

            player8 = goodSharedPref.getString("player8", "?");
            word8 = goodSharedPref.getString("word8", "?");
            score8 = goodSharedPref.getInt("score8", -1000);

            player9 = goodSharedPref.getString("player9", "?");
            word9 = goodSharedPref.getString("word9", "?");
            score9 = goodSharedPref.getInt("score9", -1000);

            player10 = goodSharedPref.getString("player10", "?");
            word10 = goodSharedPref.getString("word10", "?");
            score10 = goodSharedPref.getInt("score10", -1000);
        }

        // if name for new highscore already has been filled in, ignore intent
        okPressed = okPressedPref.getBoolean("okPressed", false);
        if (!okPressed) {
            win = getIntent.getBooleanExtra("win", false);
            newScore = getIntent.getIntExtra("score", -1000);
        }
        else {
            win = false;
            newScore = -1000;
        }

        // if game is won and score is higher than score of place 10, put score in highscore list
        if (win && newScore > score10) {
            String newWord = getIntent.getStringExtra("word");

            // set editText and button visisble
            inputNamePlayer.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.VISIBLE);

            // search right place in highscore list and move older scores
            if (newScore > score1) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = player8;
                word9 = word8;
                score9 = score8;

                player8 = player7;
                word8 = word7;
                score8 = score7;

                player7 = player6;
                word7 = word6;
                score7 = score6;

                player6 = player5;
                word6 = word5;
                score6 = score5;

                player5 = player4;
                word5 = word4;
                score5 = score4;

                player4 = player3;
                word4 = word3;
                score4 = score3;

                player3 = player2;
                word3 = word2;
                score3 = score2;

                player2 = player1;
                word2 = word1;
                score2 = score1;

                player1 = "...";
                word1 = newWord;
                score1 = newScore;
            }
            else if (newScore > score2) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = player8;
                word9 = word8;
                score9 = score8;

                player8 = player7;
                word8 = word7;
                score8 = score7;

                player7 = player6;
                word7 = word6;
                score7 = score6;

                player6 = player5;
                word6 = word5;
                score6 = score5;

                player5 = player4;
                word5 = word4;
                score5 = score4;

                player4 = player3;
                word4 = word3;
                score4 = score3;

                player3 = player2;
                word3 = word2;
                score3 = score2;

                player2 = "...";
                word2 = newWord;
                score2 = newScore;
            }
            else if (newScore > score3) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = player8;
                word9 = word8;
                score9 = score8;

                player8 = player7;
                word8 = word7;
                score8 = score7;

                player7 = player6;
                word7 = word6;
                score7 = score6;

                player6 = player5;
                word6 = word5;
                score6 = score5;

                player5 = player4;
                word5 = word4;
                score5 = score4;

                player4 = player3;
                word4 = word3;
                score4 = score3;

                player3 = "...";
                word3 = newWord;
                score3 = newScore;
            }
            else if (newScore > score4) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = player8;
                word9 = word8;
                score9 = score8;

                player8 = player7;
                word8 = word7;
                score8 = score7;

                player7 = player6;
                word7 = word6;
                score7 = score6;

                player6 = player5;
                word6 = word5;
                score6 = score5;

                player5 = player4;
                word5 = word4;
                score5 = score4;

                player4 = "...";
                word4 = newWord;
                score4 = newScore;
            }
            else if (newScore > score5) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = player8;
                word9 = word8;
                score9 = score8;

                player8 = player7;
                word8 = word7;
                score8 = score7;

                player7 = player6;
                word7 = word6;
                score7 = score6;

                player6 = player5;
                word6 = word5;
                score6 = score5;

                player5 = "...";
                word5 = newWord;
                score5 = newScore;
            }
            else if (newScore > score6) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = player8;
                word9 = word8;
                score9 = score8;

                player8 = player7;
                word8 = word7;
                score8 = score7;

                player7 = player6;
                word7 = word6;
                score7 = score6;

                player6 = "...";
                word6 = newWord;
                score6 = newScore;
            }
            else if (newScore > score7) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = player8;
                word9 = word8;
                score9 = score8;

                player8 = player7;
                word8 = word7;
                score8 = score7;

                player7 = "...";
                word7 = newWord;
                score7 = newScore;
            }
            else if (newScore > score8) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = player8;
                word9 = word8;
                score9 = score8;

                player8 = "...";
                word8 = newWord;
                score8 = newScore;
            }
            else if (newScore > score9) {
                player10 = player9;
                word10 = word9;
                score10 = score9;

                player9 = "...";
                word9 = newWord;
                score9 = newScore;
            }
            else {
                player10 = "...";
                word10 = newWord;
                score10 = newScore;
            }
        }

        // set highscore data in layout
        player1View.setText(player1);
        word1View.setText(word1);
        String stringScore1 = Integer.toString(score1);
        score1View.setText(stringScore1);

        player2View.setText(player2);
        word2View.setText(word2);
        String stringScore2 = Integer.toString(score2);
        score2View.setText(stringScore2);

        player3View.setText(player3);
        word3View.setText(word3);
        String stringScore3 = Integer.toString(score3);
        score3View.setText(stringScore3);

        player4View.setText(player4);
        word4View.setText(word4);
        String stringScore4 = Integer.toString(score4);
        score4View.setText(stringScore4);

        player5View.setText(player5);
        word5View.setText(word5);
        String stringScore5 = Integer.toString(score5);
        score5View.setText(stringScore5);

        player6View.setText(player6);
        word6View.setText(word6);
        String stringScore6 = Integer.toString(score6);
        score6View.setText(stringScore6);

        player7View.setText(player7);
        word7View.setText(word7);
        String stringScore7 = Integer.toString(score7);
        score7View.setText(stringScore7);

        player8View.setText(player8);
        word8View.setText(word8);
        String stringScore8 = Integer.toString(score8);
        score8View.setText(stringScore8);

        player9View.setText(player9);
        word9View.setText(word9);
        String stringScore9 = Integer.toString(score9);
        score9View.setText(stringScore9);

        player10View.setText(player10);
        word10View.setText(word10);
        String stringScore10 = Integer.toString(score10);
        score10View.setText(stringScore10);
    }

    /**
     * If Ok Button is pressed, take text from editText and put in highscore list and save new list
     * in shared preferences.
     */
    public void onClickOkHighscores (View view) {

        // take text from editText
        String namePlayer = inputNamePlayer.getText().toString();

        // alert user until editText is not empty
        if (namePlayer.equals("")) {
            Toast.makeText(HighscoresActivity.this, "Input is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            // remove editText and button
            inputNamePlayer.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);

            // save ok button has been pressed
            okPressedEdit.putBoolean("okPressed", true);
            okPressedEdit.apply();

            // remove keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            // find right place and put text from editText there
            if (player1.equals("...")) {
                player1 = namePlayer;
                player1View.setText(player1);
            }
            else if (player2.equals("...")) {
                player2 = namePlayer;
                player2View.setText(player2);
            }
            else if (player3.equals("...")) {
                player3 = namePlayer;
                player3View.setText(player3);
            }
            else if (player4.equals("...")) {
                player4 = namePlayer;
                player4View.setText(player4);
            }
            else if (player5.equals("...")) {
                player5 = namePlayer;
                player5View.setText(player5);
            }
            else if (player6.equals("...")) {
                player6 = namePlayer;
                player6View.setText(player6);
            }
            else if (player7.equals("...")) {
                player7 = namePlayer;
                player7View.setText(player7);
            }
            else if (player8.equals("...")) {
                player8 = namePlayer;
                player8View.setText(player8);
            }
            else if (player9.equals("...")) {
                player9 = namePlayer;
                player9View.setText(player9);
            }
            else {
                player10 = namePlayer;
                player10View.setText(player10);
            }

            // if game mode is evil, save highscores in shared preferences
            if (mode.equals("Evil mode")) {
                SharedPreferences.Editor evilPrefsEdit = evilSharedPref.edit();

                evilPrefsEdit.putString("player1", player1);
                evilPrefsEdit.putString("player2", player2);
                evilPrefsEdit.putString("player3", player3);
                evilPrefsEdit.putString("player4", player4);
                evilPrefsEdit.putString("player5", player5);
                evilPrefsEdit.putString("player6", player6);
                evilPrefsEdit.putString("player7", player7);
                evilPrefsEdit.putString("player8", player8);
                evilPrefsEdit.putString("player9", player9);
                evilPrefsEdit.putString("player10", player10);

                evilPrefsEdit.putString("word1", word1);
                evilPrefsEdit.putString("word2", word2);
                evilPrefsEdit.putString("word3", word3);
                evilPrefsEdit.putString("word4", word4);
                evilPrefsEdit.putString("word5", word5);
                evilPrefsEdit.putString("word6", word6);
                evilPrefsEdit.putString("word7", word7);
                evilPrefsEdit.putString("word8", word8);
                evilPrefsEdit.putString("word9", word9);
                evilPrefsEdit.putString("word10", word10);

                evilPrefsEdit.putInt("score1", score1);
                evilPrefsEdit.putInt("score2", score2);
                evilPrefsEdit.putInt("score3", score3);
                evilPrefsEdit.putInt("score4", score4);
                evilPrefsEdit.putInt("score5", score5);
                evilPrefsEdit.putInt("score6", score6);
                evilPrefsEdit.putInt("score7", score7);
                evilPrefsEdit.putInt("score8", score8);
                evilPrefsEdit.putInt("score9", score9);
                evilPrefsEdit.putInt("score10", score10);

                evilPrefsEdit.apply();
            }

            // if game mode is good, save highscores in shared preferences
            else {
                SharedPreferences.Editor goodPrefsEdit = goodSharedPref.edit();

                goodPrefsEdit.putString("player1", player1);
                goodPrefsEdit.putString("player2", player2);
                goodPrefsEdit.putString("player3", player3);
                goodPrefsEdit.putString("player4", player4);
                goodPrefsEdit.putString("player5", player5);
                goodPrefsEdit.putString("player6", player6);
                goodPrefsEdit.putString("player7", player7);
                goodPrefsEdit.putString("player8", player8);
                goodPrefsEdit.putString("player9", player9);
                goodPrefsEdit.putString("player10", player10);

                goodPrefsEdit.putString("word1", word1);
                goodPrefsEdit.putString("word2", word2);
                goodPrefsEdit.putString("word3", word3);
                goodPrefsEdit.putString("word4", word4);
                goodPrefsEdit.putString("word5", word5);
                goodPrefsEdit.putString("word6", word6);
                goodPrefsEdit.putString("word7", word7);
                goodPrefsEdit.putString("word8", word8);
                goodPrefsEdit.putString("word9", word9);
                goodPrefsEdit.putString("word10", word10);

                goodPrefsEdit.putInt("score1", score1);
                goodPrefsEdit.putInt("score2", score2);
                goodPrefsEdit.putInt("score3", score3);
                goodPrefsEdit.putInt("score4", score4);
                goodPrefsEdit.putInt("score5", score5);
                goodPrefsEdit.putInt("score6", score6);
                goodPrefsEdit.putInt("score7", score7);
                goodPrefsEdit.putInt("score8", score8);
                goodPrefsEdit.putInt("score9", score9);
                goodPrefsEdit.putInt("score10", score10);

                goodPrefsEdit.apply();
            }
        }
    }

    /**
     * Shows alert dialog for quiting game if user presses on back button.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HighscoresActivity.this);
        builder.setMessage("Exit the game?");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}