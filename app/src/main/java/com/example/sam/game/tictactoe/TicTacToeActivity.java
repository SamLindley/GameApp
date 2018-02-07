package com.example.sam.game.tictactoe;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.sam.game.R;

public class TicTacToeActivity extends AppCompatActivity {

    private int turn = 0;
    boolean gameOver = false;
    GridLayout layout;
    ImageView catWinner;

    //   2 means unplayed
    private int[] played = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe);
        catWinner = findViewById(R.id.cat_winner);
        catWinner.setTranslationX(-2000f);
        layout = findViewById(R.id.gameBoard);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int selected = Integer.parseInt(counter.getTag().toString());
        if (played[selected - 1] == 2 && !gameOver) {
            played[selected - 1] = turn;
            Animator fall = ObjectAnimator.ofFloat(counter, View.TRANSLATION_Y, -1000f, 0f).setDuration(300);
            Animator bounceUp = ObjectAnimator.ofFloat(counter, View.TRANSLATION_Y, 0f, -100f).setDuration(100);
            Animator bounceDown = ObjectAnimator.ofFloat(counter, View.TRANSLATION_Y, -100f, 0f).setDuration(100);
            Animator fadeIn = ObjectAnimator.ofFloat(counter, View.ALPHA, 0f, 1f).setDuration(100);

            if (turn == 0) {
                counter.setImageResource(R.drawable.yellow);
                turn = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                turn = 0;
            }

            counter.setTranslationY(-1000f);
            AnimatorSet as = new AnimatorSet();
            as.playSequentially(fall, bounceUp, bounceDown);
            as.start();
            gameOver = checkForWinner();
            if (gameOver) {
                Toast.makeText(view.getContext(), "WINNER!", Toast.LENGTH_SHORT).show();
                catWinner.animate().translationXBy(2000f).setDuration(1000);

            }
        }

    }

    public void reset(View view) {
        int squares = layout.getChildCount();
        ImageView currentSquare;
        for (int i=0; i < squares; i++) {
            currentSquare = (ImageView) layout.getChildAt(i);
            currentSquare.setImageResource(0);
            played[i] = 2;
            if (catWinner.getLeft() == 200) {
                catWinner.animate().translationXBy(-2000f).setDuration(1000);
            }
        }
        gameOver = false;

    }

    private boolean checkForWinner() {
        return played[0] == played[1] && played[2] == played[1] && played[0] != 2 ||
                played[3] == played[4] && played[4] == played[5] && played[3] != 2 ||
                played[6] == played[7] && played[7] == played[8] && played[8] != 2 ||
                played[0] == played[3] && played[3] == played[6] && played[3] != 2 ||
                played[1] == played[4] && played[4] == played[7] && played[4] != 2 ||
                played[2] == played[5] && played[5] == played[8] && played[5] != 2 ||
                played[0] == played[4] && played[4] == played[8] && played[4] != 2 ||
                played[2] == played[4] && played[4] == played[6] && played[4] != 2;
    }
}