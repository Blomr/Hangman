/**
 * Gameplay.java
 *
 * Abstract class for EvilGameplayActivity and GoodGameplayActivity.
 * Create the layout for gameplay, handles the drawing on the blackboard
 * and chooses the word list or word, depending on game mode.
 */

package com.remcoblom.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

abstract class Gameplay extends AppCompatActivity {

    EditText editText;
    Button okButton;
    ImageView drawing;
    ImageView settingsButton;
    ImageView newButton;
    TextView wordView;
    TextView incGuessView;
    TextView wrongLettersView;
    TextView scoreView;
    String scoreString;
    String word;
    String hiddenWord;
    String wrongLetters;
    char[] wordChars;
    char[] hiddenChars;
    int score;
    int wordLength;
    int incGuess;
    int correctLetters;
    boolean evilMode;
    boolean gameOver;
    boolean pressedA;
    boolean pressedB;
    boolean pressedC;
    boolean pressedD;
    boolean pressedE;
    boolean pressedF;
    boolean pressedG;
    boolean pressedH;
    boolean pressedI;
    boolean pressedJ;
    boolean pressedK;
    boolean pressedL;
    boolean pressedM;
    boolean pressedN;
    boolean pressedO;
    boolean pressedP;
    boolean pressedQ;
    boolean pressedR;
    boolean pressedS;
    boolean pressedT;
    boolean pressedU;
    boolean pressedV;
    boolean pressedW;
    boolean pressedX;
    boolean pressedY;
    boolean pressedZ;
    ArrayList<String> wordList;
    SharedPreferences settingsSharedPref;
    SharedPreferences savegameSharedPref;
    SharedPreferences.Editor savegameEditor;

    /**
     * Create layout, set onClickListener on new button and settings button.
     * Initialize variables for game start.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        // initialize layout variables
        wordView = (TextView) findViewById(R.id.word);
        editText = (EditText) findViewById(R.id.editText);
        okButton = (Button) findViewById(R.id.okButton);
        incGuessView = (TextView) findViewById(R.id.incGuessLeft);
        drawing = (ImageView) findViewById(R.id.drawing);
        settingsButton = (ImageView) findViewById(R.id.settingsButtonGP);
        newButton = (ImageView) findViewById(R.id.newButtonGP);
        wrongLettersView = (TextView) findViewById(R.id.wrongLetters);
        scoreView = (TextView) findViewById(R.id.score);

        savegameSharedPref = getSharedPreferences("savegame", MODE_PRIVATE);
        savegameEditor = savegameSharedPref.edit();
        gameOver = savegameSharedPref.getBoolean("gameOver", true);

        // if last game wasn't over, continue the game
        if (!gameOver) {
            incGuess = savegameSharedPref.getInt("incGuess", 9);
            evilMode = savegameSharedPref.getBoolean("evilMode", true);
            wordLength = savegameSharedPref.getInt("wordLength", 5);
            score = savegameSharedPref.getInt("score", 0);
            correctLetters = savegameSharedPref.getInt("correctLetters", 0);
            hiddenWord = savegameSharedPref.getString("hiddenWord", null);
            wrongLetters = savegameSharedPref.getString("wrongLetters", null);

            pressedA = savegameSharedPref.getBoolean("pressedA", false);
            pressedB = savegameSharedPref.getBoolean("pressedB", false);
            pressedC = savegameSharedPref.getBoolean("pressedC", false);
            pressedD = savegameSharedPref.getBoolean("pressedD", false);
            pressedE = savegameSharedPref.getBoolean("pressedE", false);
            pressedF = savegameSharedPref.getBoolean("pressedF", false);
            pressedG = savegameSharedPref.getBoolean("pressedG", false);
            pressedH = savegameSharedPref.getBoolean("pressedH", false);
            pressedI = savegameSharedPref.getBoolean("pressedI", false);
            pressedJ = savegameSharedPref.getBoolean("pressedJ", false);
            pressedK = savegameSharedPref.getBoolean("pressedK", false);
            pressedL = savegameSharedPref.getBoolean("pressedL", false);
            pressedM = savegameSharedPref.getBoolean("pressedM", false);
            pressedN = savegameSharedPref.getBoolean("pressedN", false);
            pressedO = savegameSharedPref.getBoolean("pressedO", false);
            pressedP = savegameSharedPref.getBoolean("pressedP", false);
            pressedQ = savegameSharedPref.getBoolean("pressedQ", false);
            pressedR = savegameSharedPref.getBoolean("pressedR", false);
            pressedS = savegameSharedPref.getBoolean("pressedS", false);
            pressedT = savegameSharedPref.getBoolean("pressedT", false);
            pressedU = savegameSharedPref.getBoolean("pressedU", false);
            pressedV = savegameSharedPref.getBoolean("pressedV", false);
            pressedW = savegameSharedPref.getBoolean("pressedW", false);
            pressedX = savegameSharedPref.getBoolean("pressedX", false);
            pressedY = savegameSharedPref.getBoolean("pressedY", false);
            pressedZ = savegameSharedPref.getBoolean("pressedZ", false);

            if (evilMode) {
                Set<String> setWordList = savegameSharedPref.getStringSet("wordList", null);
                wordList = new ArrayList<>(setWordList);
            } else {
                word = savegameSharedPref.getString("word", null);
            }
        }

        // if there is no savegame, start a new game
        else {
            // get shared preferences from settings
            settingsSharedPref = getSharedPreferences("settings", MODE_PRIVATE);
            incGuess = settingsSharedPref.getInt("incGuess", 9);
            evilMode = settingsSharedPref.getBoolean("evilMode", true);
            wordLength = settingsSharedPref.getInt("wordLength", 5);

            score = 0;
            correctLetters = 0;
            wrongLetters = "";

            // put new game in savegame
            savegameEditor.putInt("incGuess", incGuess);
            savegameEditor.putBoolean("evilMode", evilMode);
            savegameEditor.putInt("wordLength", wordLength);
            savegameEditor.putInt("score", score);
            savegameEditor.putInt("correctLetters", correctLetters);
            savegameEditor.putString("wrongLetters", wrongLetters);

            pressedA = false;
            pressedB = false;
            pressedC = false;
            pressedD = false;
            pressedE = false;
            pressedF = false;
            pressedG = false;
            pressedH = false;
            pressedI = false;
            pressedJ = false;
            pressedK = false;
            pressedL = false;
            pressedM = false;
            pressedN = false;
            pressedO = false;
            pressedP = false;
            pressedQ = false;
            pressedR = false;
            pressedS = false;
            pressedT = false;
            pressedU = false;
            pressedV = false;
            pressedW = false;
            pressedX = false;
            pressedY = false;
            pressedZ = false;
            savegameEditor.putBoolean("pressedA", false);
            savegameEditor.putBoolean("pressedB", false);
            savegameEditor.putBoolean("pressedC", false);
            savegameEditor.putBoolean("pressedD", false);
            savegameEditor.putBoolean("pressedE", false);
            savegameEditor.putBoolean("pressedF", false);
            savegameEditor.putBoolean("pressedG", false);
            savegameEditor.putBoolean("pressedH", false);
            savegameEditor.putBoolean("pressedI", false);
            savegameEditor.putBoolean("pressedJ", false);
            savegameEditor.putBoolean("pressedK", false);
            savegameEditor.putBoolean("pressedL", false);
            savegameEditor.putBoolean("pressedM", false);
            savegameEditor.putBoolean("pressedN", false);
            savegameEditor.putBoolean("pressedO", false);
            savegameEditor.putBoolean("pressedP", false);
            savegameEditor.putBoolean("pressedQ", false);
            savegameEditor.putBoolean("pressedR", false);
            savegameEditor.putBoolean("pressedS", false);
            savegameEditor.putBoolean("pressedT", false);
            savegameEditor.putBoolean("pressedU", false);
            savegameEditor.putBoolean("pressedV", false);
            savegameEditor.putBoolean("pressedW", false);
            savegameEditor.putBoolean("pressedX", false);
            savegameEditor.putBoolean("pressedY", false);
            savegameEditor.putBoolean("pressedZ", false);

            // depending on game mode, get word list or word
            if (evilMode) {
                wordList = this.getWordList(wordLength);
                Set<String> setWordList = new HashSet<>();
                setWordList.addAll(wordList);
                savegameEditor.putStringSet("wordList", setWordList);
            }
            else {
                word = this.getWord(wordLength);
                savegameEditor.putString("word", word);
            }

            // set stripes on place of word in layout
            hiddenChars = new char[wordLength];
            for (int i = 0; i < wordLength; i++) {
                hiddenChars[i] = '-';
            }
            hiddenWord = new String(hiddenChars);
            savegameEditor.putString("hiddenWord", hiddenWord);

            savegameEditor.putBoolean("gameOver", false);
        }

        // save changes
        savegameEditor.apply();

        // set right drawing of gallows
        this.setDrawing(incGuess);

        // set text incorrect guesses left
        String textGuessLeft = "Incorrect Guesses Left: " + incGuess;
        incGuessView.setText(textGuessLeft);

        // set score, hidden word and wrong letters in layout
        scoreString = "Score: " + score;
        scoreView.setText(scoreString);
        wordView.setText(hiddenWord);
        wrongLettersView.setText(wrongLetters);

        // set onClickListener on new button
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // show alert dialog before starting new game
            AlertDialog.Builder builder = new AlertDialog.Builder(Gameplay.this);
            builder.setMessage("Start a new game?");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                savegameEditor.putBoolean("gameOver", true);
                savegameEditor.apply();
                settingsSharedPref = getSharedPreferences("settings", MODE_PRIVATE);
                boolean mode = settingsSharedPref.getBoolean("evilMode", true);
                if (mode) {
                    Intent goToEvilMode = new Intent(Gameplay.this, EvilGameplayActivity.class);
                    startActivity(goToEvilMode);
                    finish();
                }
                else {
                    Intent goToGoodMode = new Intent(Gameplay.this, GoodGameplayActivity.class);
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
                Intent goToSettings = new Intent(Gameplay.this, SettingsActivity.class);
                startActivity(goToSettings);
            }
        });
    }

    /**
     * Depending on word length, take the right arraylist of words from LoadingActivity.
     */
    private ArrayList<String> getWordList (int wordLength) {
        ArrayList<String> wordList;
        switch (wordLength) {
            case 1:
                wordList = LoadingActivity.letters1;
                break;
            case 2:
                wordList = LoadingActivity.letters2;
                break;
            case 3:
                wordList = LoadingActivity.letters3;
                break;
            case 4:
                wordList = LoadingActivity.letters4;
                break;
            case 5:
                wordList = LoadingActivity.letters5;
                break;
            case 6:
                wordList = LoadingActivity.letters6;
                break;
            case 7:
                wordList = LoadingActivity.letters7;
                break;
            case 8:
                wordList = LoadingActivity.letters8;
                break;
            case 9:
                wordList = LoadingActivity.letters9;
                break;
            case 10:
                wordList = LoadingActivity.letters10;
                break;
            case 11:
                wordList = LoadingActivity.letters11;
                break;
            case 12:
                wordList = LoadingActivity.letters12;
                break;
            case 13:
                wordList = LoadingActivity.letters13;
                break;
            case 14:
                wordList = LoadingActivity.letters14;
                break;
            default:
                wordList = LoadingActivity.letters15;
                break;
        }
        return wordList;
    }

    /**
     * Depending on word length, get a random word from the right arraylist from LoadingActivity.
     * Random word is generated with a random integer that's corresponding as index for the
     * arraylist.
     */
    private String getWord (int wordLength) {
        Random random = new Random();
        int arrayLength;
        String getWord = "";
        switch (wordLength) {
            case 1:
                arrayLength = LoadingActivity.letters1.size();
                getWord = LoadingActivity.letters1.get(random.nextInt(arrayLength));
                break;
            case 2:
                arrayLength = LoadingActivity.letters2.size();
                getWord = LoadingActivity.letters2.get(random.nextInt(arrayLength));
                break;
            case 3:
                arrayLength = LoadingActivity.letters3.size();
                getWord = LoadingActivity.letters3.get(random.nextInt(arrayLength));
                break;
            case 4:
                arrayLength = LoadingActivity.letters4.size();
                getWord = LoadingActivity.letters4.get(random.nextInt(arrayLength));
                break;
            case 5:
                arrayLength = LoadingActivity.letters5.size();
                getWord = LoadingActivity.letters5.get(random.nextInt(arrayLength));
                break;
            case 6:
                arrayLength = LoadingActivity.letters6.size();
                getWord = LoadingActivity.letters6.get(random.nextInt(arrayLength));
                break;
            case 7:
                arrayLength = LoadingActivity.letters7.size();
                getWord = LoadingActivity.letters7.get(random.nextInt(arrayLength));
                break;
            case 8:
                arrayLength = LoadingActivity.letters8.size();
                getWord = LoadingActivity.letters8.get(random.nextInt(arrayLength));
                break;
            case 9:
                arrayLength = LoadingActivity.letters9.size();
                getWord = LoadingActivity.letters9.get(random.nextInt(arrayLength));
                break;
            case 10:
                arrayLength = LoadingActivity.letters10.size();
                getWord = LoadingActivity.letters10.get(random.nextInt(arrayLength));
                break;
            case 11:
                arrayLength = LoadingActivity.letters11.size();
                getWord = LoadingActivity.letters11.get(random.nextInt(arrayLength));
                break;
            case 12:
                arrayLength = LoadingActivity.letters12.size();
                getWord = LoadingActivity.letters12.get(random.nextInt(arrayLength));
                break;
            case 13:
                arrayLength = LoadingActivity.letters13.size();
                getWord = LoadingActivity.letters13.get(random.nextInt(arrayLength));
                break;
            case 14:
                arrayLength = LoadingActivity.letters14.size();
                getWord = LoadingActivity.letters14.get(random.nextInt(arrayLength));
                break;
            default:
                arrayLength = LoadingActivity.letters15.size();
                getWord = LoadingActivity.letters15.get(random.nextInt(arrayLength));
                break;
        }
        return getWord;
    }

    /**
     * Generates the drawing of a gallows depending on the amount of incorrect guesses that are
     * left.
     */
    protected void setDrawing(int incGuessLeft) {
        switch (incGuessLeft) {
            case 8:
                drawing.setImageResource(R.drawable.tek1);
                break;
            case 7:
                drawing.setImageResource(R.drawable.tek2);
                break;
            case 6:
                drawing.setImageResource(R.drawable.tek3);
                break;
            case 5:
                drawing.setImageResource(R.drawable.tek4);
                break;
            case 4:
                drawing.setImageResource(R.drawable.tek5);
                break;
            case 3:
                drawing.setImageResource(R.drawable.tek6);
                break;
            case 2:
                drawing.setImageResource(R.drawable.tek7);
                break;
            case 1:
                drawing.setImageResource(R.drawable.tek8);
                break;
            case 0:
                drawing.setImageResource(R.drawable.tek9);
                break;
            case -1:
                drawing.setImageResource(R.drawable.tek10);
                break;
        }
    }

    /**
     * Shows alert dialog for quiting game if user presses on back button.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Gameplay.this);
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

    /**
     * Makes a toast that warns the user that a letter is already chosen.
     */
    protected void alreadyPressedButton() {
        Toast.makeText(Gameplay.this, "You have already chosen this letter", Toast.LENGTH_SHORT).show();
    }
}