/**
 * EvilGameplayAcitivity.java
 *
 * Subclass of abstract Gameplay class.
 * Handles clicks on ok button and compares chosen letter with words in wordlist.
 * New wordlists are made, depending on the choices of the user.
 * The user wins when all letters of one word is guessed
 */

package com.remcoblom.hangman;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EvilGameplayActivity extends Gameplay {

    int guesses = 0;
    ArrayList<String> chosenWordList;

    /**
     * Class to make objects of word lists
     */
    public class NewWordList {
        Integer[] placesLetter;
        ArrayList<String> words = new ArrayList<>();
    }

    /**
     * Depending on chosen letter, make new word lists of current word list.
     * Every word list has other positions of the chosen letter in the words.
     * The game continues with the longest word list. Game stops when there is
     * one word left and fully guessed or when a guess is wrong if there are
     * no incorrect guesses left.
     */
    public void checkLetter(String getLetter) {
        char getChar = getLetter.charAt(0);

        // make arraylist for new objects
        ArrayList<NewWordList> newWordLists = new ArrayList<>();

        // if there were no guesses before, choose start word list as current word list
        ArrayList<String> currentWordList;
        if (guesses == 0) {
            currentWordList = wordList;
        }

        // else, choose the last chosen word list
        else {
            currentWordList = chosenWordList;
        }

        guesses++;

        // iterate over all words in word list
        for (String word : currentWordList) {
            wordChars = word.toCharArray();

            // iterate over letters of word, if chosen letter is found, save index in arraylist
            ArrayList<Integer> wordPlacesLetter = new ArrayList<>();
            for (int i = 0; i < wordLength; i++){
                if (wordChars[i] == getChar) {
                    wordPlacesLetter.add(i);
                }
            }

            final Integer[] arrayPlacesLetter = wordPlacesLetter.toArray(new Integer[wordPlacesLetter.size()]);
            boolean objectMade = false;

            // iterate over all new word lists objects that are already have been made
            for (NewWordList newWordList : newWordLists) {
                // if word is equal to a word list with same letter positions, add word to word list
                if (Arrays.equals(newWordList.placesLetter, arrayPlacesLetter)) {
                    newWordList.words.add(word);
                    objectMade = true;
                    break;
                }
            }

            // if word hasn't been added to a word list, make new word list object and add word to it
            if (!objectMade) {
                final String finalWord = word;
                NewWordList newWordList = new NewWordList() {{
                    placesLetter = arrayPlacesLetter;
                    words.add(finalWord);
                }};
                newWordLists.add(newWordList);
            }
        }

        // determine and get word list with most words
        int biggestValue = 0;
        Integer[] chosenPlacesLetter = new Integer[0];
        for (NewWordList newWordList : newWordLists) {
            if (newWordList.words.size() > biggestValue) {
                biggestValue = newWordList.words.size();
                chosenWordList = newWordList.words;
                chosenPlacesLetter = newWordList.placesLetter;
            }
        }

        Set<String> setWordList = new HashSet<>();
        setWordList.addAll(chosenWordList);
        savegameEditor.putStringSet("wordList", setWordList);

        // if chosen word list has the chosen letter in it, set the letter(s) in the hidden word
        if (chosenPlacesLetter.length != 0) {
            hiddenChars = hiddenWord.toCharArray();
            for (int i = 0; i < wordLength; i++) {
                if (Arrays.asList(chosenPlacesLetter).contains(i)) {
                    hiddenChars[i] = getChar;
                    correctLetters++;
                }
            }
            hiddenWord = new String(hiddenChars);
            wordView.setText(hiddenWord);

            // increment score with 1
            score++;
            scoreString = "Score: " + score;
            scoreView.setText(scoreString);

            // save changes
            savegameEditor.putString("hiddenWord", hiddenWord);
            savegameEditor.putInt("score", score);
            savegameEditor.putInt("correctLetters", correctLetters);
            savegameEditor.apply();

            // if all letters are no longer hidden, the user has won the game
            if (correctLetters == wordLength) {
                Toast.makeText(EvilGameplayActivity.this, "You won", Toast.LENGTH_SHORT).show();
                savegameEditor.putBoolean("gameOver", true);
                savegameEditor.apply();
                Intent goToHighscores = new Intent(EvilGameplayActivity.this, HighscoresActivity.class);
                goToHighscores.putExtra("win", true);
                goToHighscores.putExtra("score", score);
                goToHighscores.putExtra("word", hiddenWord);
                goToHighscores.putExtra("mode", "Evil mode");
                startActivity(goToHighscores);
                finish();
            }
        }

        // if chosen word list has no chosen letter in it, the guess is incorrect
        else {
            incGuess--;

            // decrease score with 1
            score--;
            scoreString = "Score: " + score;
            scoreView.setText(scoreString);

            // set wrong guessed letter at previous wrong letters
            CharSequence getWrongLetters = wrongLettersView.getText();
            String newWrongLetters = getWrongLetters + getLetter + " ";
            wrongLettersView.setText(newWrongLetters);

            // save changes
            savegameEditor.putInt("incGuess", incGuess);
            savegameEditor.putInt("score", score);
            savegameEditor.putString("wrongLetters", newWrongLetters);
            savegameEditor.apply();

            // if incorrect guesses is 0 or higher, set the changed value
            if (incGuess >= 0) {
                String textGuessLeft = "Incorrect Guesses Left: " + incGuess;
                incGuessView.setText(textGuessLeft);
            }

            // else, the game is lost, and user goes to highscores
            else {
                Toast.makeText(EvilGameplayActivity.this, "You lost", Toast.LENGTH_SHORT).show();
                savegameEditor.putBoolean("gameOver", true);
                savegameEditor.apply();
                Intent goToHighscores = new Intent(EvilGameplayActivity.this, HighscoresActivity.class);
                goToHighscores.putExtra("win", false);
                goToHighscores.putExtra("mode", "Evil mode");
                startActivity(goToHighscores);
                finish();
            }

            // change drawing of gallows on blackboard
            this.setDrawing(incGuess);
        }
    }

    /**
     * If ok button is pressed, get letter input of editText and check if it is already chosen.
     * If not, check if letter is in word.
     */
    public void onClickOk(View view) {

        // get letter, capitalize it and empty editText
        String getLetter = String.valueOf(editText.getText()).toUpperCase();
        editText.setText("");

        // if letter is not already chosen, check if letter is in word
        switch (getLetter) {
            case "A":
                if (pressedA) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedA = true;
                    savegameEditor.putBoolean("pressedA", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "B":
                if (pressedB) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedB = true;
                    savegameEditor.putBoolean("pressedB", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "C":
                if (pressedC) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedC = true;
                    savegameEditor.putBoolean("pressedC", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "D":
                if (pressedD) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedD = true;
                    savegameEditor.putBoolean("pressedD", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "E":
                if (pressedE) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedE = true;
                    savegameEditor.putBoolean("pressedE", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "F":
                if (pressedF) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedF = true;
                    savegameEditor.putBoolean("pressedF", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "G":
                if (pressedG) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedG = true;
                    savegameEditor.putBoolean("pressedG", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "H":
                if (pressedH) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedH = true;
                    savegameEditor.putBoolean("pressedH", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "I":
                if (pressedI) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedI = true;
                    savegameEditor.putBoolean("pressedI", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "J":
                if (pressedJ) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedJ = true;
                    savegameEditor.putBoolean("pressedJ", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "K":
                if (pressedK) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedK = true;
                    savegameEditor.putBoolean("pressedK", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "L":
                if (pressedL) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedL = true;
                    savegameEditor.putBoolean("pressedL", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "M":
                if (pressedM) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedM = true;
                    savegameEditor.putBoolean("pressedM", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "N":
                if (pressedN) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedN = true;
                    savegameEditor.putBoolean("pressedN", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "O":
                if (pressedO) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedO = true;
                    savegameEditor.putBoolean("pressedO", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "P":
                if (pressedP) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedP = true;
                    savegameEditor.putBoolean("pressedP", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "Q":
                if (pressedQ) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedQ = true;
                    savegameEditor.putBoolean("pressedQ", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "R":
                if (pressedR) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedR = true;
                    savegameEditor.putBoolean("pressedR", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "S":
                if (pressedS) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedS = true;
                    savegameEditor.putBoolean("pressedS", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "T":
                if (pressedT) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedT = true;
                    savegameEditor.putBoolean("pressedT", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "U":
                if (pressedU) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedU = true;
                    savegameEditor.putBoolean("pressedU", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "V":
                if (pressedV) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedV = true;
                    savegameEditor.putBoolean("pressedV", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "W":
                if (pressedW) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedW = true;
                    savegameEditor.putBoolean("pressedW", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "X":
                if (pressedX) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedX = true;
                    savegameEditor.putBoolean("pressedX", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "Y":
                if (pressedY) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedY = true;
                    savegameEditor.putBoolean("pressedY", true);
                    this.checkLetter(getLetter);
                }
                break;
            case "Z":
                if (pressedZ) {
                    this.alreadyPressedButton();
                }
                else {
                    pressedZ = true;
                    savegameEditor.putBoolean("pressedZ", true);
                    this.checkLetter(getLetter);
                }
                break;

            // if input is invalid, send message to user
            default:
                Toast.makeText(EvilGameplayActivity.this, "Wrong input, choose a letter", Toast.LENGTH_SHORT).show();
                break;
        }
        savegameEditor.apply();
    }
}