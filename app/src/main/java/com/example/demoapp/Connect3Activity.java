package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
//import android.widget.GridLayout;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Hashtable;

public class Connect3Activity extends AppCompatActivity {
    //0 yellow, 1 red
    private int activePlayer = 0;
    private boolean gameActive = true;
    private String[] positions = new String[] {"empty","empty","empty","empty","empty","empty","empty","empty","empty"};
    private int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void playAgain(View view){
        gameActive = true;
        TextView turns = (TextView) findViewById(R.id.textViewTurns);
        turns.setBackgroundColor(Color.parseColor("#EDDA2D"));
        turns.setText("Yellow's turn");
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for (int i=0; i< positions.length; i++){
            positions[i]="empty";
        }
        GridLayout board = (GridLayout) findViewById(R.id.board);
        for (int i=0; i<board.getChildCount();i++){
            ((ImageView) board.getChildAt(i)).setImageResource(0);
        }
    }

    public void dropIn(View view){

        ImageView selected = (ImageView) view;
        int tapedIn = Integer.parseInt(selected.getTag().toString());

        if(positions[tapedIn].equals("empty") && gameActive){
            selected.setTranslationY(-1000f);
            System.out.println("in here");
            TextView turns = (TextView) findViewById(R.id.textViewTurns);
            if(activePlayer == 0){
                positions[tapedIn] = "yellow";
                selected.setImageResource(R.drawable.yellow);
                activePlayer = 1;
                turns.setBackgroundColor(Color.parseColor("#F44336"));
                turns.setText("Red's turn");
            }else{
                positions[tapedIn] = "red";
                selected.setImageResource(R.drawable.red);
                activePlayer = 0;
                turns.setBackgroundColor(Color.parseColor("#EDDA2D"));
                turns.setText("Yellow's turn");
            }
            selected.animate().translationYBy(1000f).rotation(360).setDuration(300);
            for(int[] winningPosition: winningPositions){
                if(positions[winningPosition[0]].equals(positions[winningPosition[1]]) &&
                        positions[winningPosition[1]].equals(positions[winningPosition[2]]) &&
                        !positions[winningPosition[1]].equals("empty"))
                {
                    gameActive = false;
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerText);
                    LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    winnerLayout.setVisibility(View.VISIBLE);
                    if (activePlayer == 0) {
                        winnerLayout.setBackgroundColor(Color.parseColor("#F44336"));
                        winnerMessage.setText("Red has won");
                    } else if(activePlayer == 1) {
                        winnerLayout.setBackgroundColor(Color.parseColor("#EDDA2D"));
                        winnerMessage.setText("Yellow has won");
                    }
                }else {
                    gameActive = false;
                    for(String position : positions){
                        if(position.equals("empty"))
                            gameActive = true;
                    }
                    if(!gameActive){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerText);
                        LinearLayout winnerLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        winnerLayout.setVisibility(View.VISIBLE);
                        winnerLayout.setBackgroundColor(Color.parseColor("#AEEA69"));
                        winnerMessage.setText("It is a draw");
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect3);
    }
}