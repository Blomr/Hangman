/**
 * GoodGameplayAcitivity.java
 *
 * Subclass of abstract Gameplay class.
 * Handles clicks on ok button and checks if chosen letter is in the word.
 * The user wins when all letters of the word has been guessed.
 */

package com.remcoblom.hangman;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class GoodGameplayActivity extends Gameplay {

    /**
     * Takes letter and compares with word. If letter is in word, it displays the letter in the
     * hided word. If letter is not in word, a part of the gallows will be drawn.
     */
    public void checkLetter(String getLetter) {
        boolean letterInWord = false;
        char getChar = getLetter.charAt(0);
        wordChars = word.toCharArray();

        // iterate over letters of word, check if equals to input letter
        hiddenChars = hiddenWord.toCharArray();
        for (int i = 0; i < wordLength; i++) {
            if (wordChars[i] == getChar) {
                hiddenChars[i] = getChar;
                correctLetters++;
                letterInWord = true;
            }
        }

        // if chosen letter is in word, set new hidden word
        if (letterInWord) {
            hiddenWord = new String(hiddenChars);
            wordView.setText(hiddenWord);

            // increment score by 1
            score++;
            scoreString = "Score: " + score;
            scoreView.setText(scoreString);

            // save changes
            savegameEditor.putString("hiddenWord", hiddenWord);
            savegameEditor.putInt("score", score);
            savegameEditor.putInt("correctLetters", correctLetters);
            savegameEditor.apply();

            // if game is won, put data in intent and go to highscores
            if (correctLetters == wordLength) {
                Toast.makeText(GoodGameplayActivity.this, "You won", Toast.LENGTH_SHORT).show();
                savegameEditor.putBoolean("gameOver", true);
                savegameEditor.apply();
                Intent goToHighscores = new Intent(GoodGameplayActivity.this, HighscoresActivity.class);
                goToHighscores.putExtra("win", true);
                goToHighscores.putExtra("score", score);
                goToHighscores.putExtra("word", hiddenWord);
                goToHighscores.putExtra("mode", "Good mode");
                startActivity(goToHighscores);
                finish();
            }
        }

        // if letter is not in word, decrease incorrect guesses left by 1
        else {
            incGuess--;

            // decrease score by 1
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

            // else, the game is over, and user goes to highscores
            else {
                Toast.makeText(GoodGameplayActivity.this, "You lost", Toast.LENGTH_SHORT).show();
                savegameEditor.putBoolean("gameOver", true);
                savegameEditor.apply();
                Intent goToHighscores = new Intent(GoodGameplayActivity.this, HighscoresActivity.class);
                goToHighscores.putExtra("win", false);
                goToHighscores.putExtra("mode", "Good mode");
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
                Toast.makeText(GoodGameplayActivity.this, "Wrong input, choose a letter", Toast.LENGTH_SHORT).show();
                break;
        }
        savegameEditor.apply();
    }
}