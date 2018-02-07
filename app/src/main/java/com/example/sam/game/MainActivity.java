package com.example.sam.game;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.sam.game.chess.ChessActivity;
import com.example.sam.game.tictactoe.TicTacToeActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void startTicTacToe(View view) {
        Intent intent = new Intent(getApplicationContext(), TicTacToeActivity.class);
        startActivity(intent);
    }

    public void startChess(View view) {
        Intent intent = new Intent(getApplicationContext(), ChessActivity.class);
        startActivity(intent);
    }
}
