package com.totalaxe.totalaxescoring;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int playerOneNum = -1;
    int playerTwoNum = -1;
    int flag = 0;
    int matchResetFlag = 0;
    int round = 0;
    LinkedList<Integer> throwsA = new LinkedList<Integer>();
    LinkedList<Integer> throwsB = new LinkedList<Integer>();
    LinkedList<Integer> gamesA = new LinkedList<Integer>();
    LinkedList<Integer> gamesB = new LinkedList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText playerOneText = (EditText) findViewById(R.id.team_a_name);
        EditText playerTwoText = (EditText) findViewById(R.id.team_b_name);


        //String playerOne = getIntent().getStringExtra("playerOneName");
        //String playerTwo = getIntent().getStringExtra("playerTwoName");
        String playerOne = "Player 1";
        String playerTwo = "Player 2";
        //round = Integer.parseInt(getIntent().getStringExtra("round"));
        //playerOneNum = Integer.parseInt(getIntent().getStringExtra("playerOneNum"));
        //playerTwoNum = Integer.parseInt(getIntent().getStringExtra("playerTwoNum"));

        playerOneText.setText(playerOne);
        playerTwoText.setText(playerTwo);


    }

    public void displayForTeamA(int text) {
        TextView t = (TextView) findViewById(R.id.team_a_score);
        t.setText("" + text);
        matchResetFlag = 0;
    }

    public void displayForTeamB(int text) {
        TextView t = (TextView) findViewById(R.id.team_b_score);
        t.setText("" + text);
        matchResetFlag = 0;
    }


    public void subA(View view) {
        int temp;
        temp = Integer.parseInt(view.getTag().toString());
        scoreTeamA -= temp;
        displayForTeamA(scoreTeamA);
    }

    public void addA(View view) {
        int temp;
        temp = Integer.parseInt(view.getTag().toString());
        scoreTeamA += temp;
        throwsA.add(temp);
        displayForTeamA(scoreTeamA);
        displayThrowsA(view);
    }

    public void displayThrowsA(View view) {
        String gameThrows = "";
        for (Integer n : throwsA) {
            gameThrows += n + "\n";
        }
        TextView t = (TextView) findViewById(R.id.a_throws);
        t.setText(gameThrows);
    }


    public void subB(View view) {
        int temp;
        temp = Integer.parseInt(view.getTag().toString());
        scoreTeamB -= temp;
        displayForTeamB(scoreTeamB);
    }

    public void addB(View view) {
        int temp;
        temp = Integer.parseInt(view.getTag().toString());
        scoreTeamB += temp;
        throwsB.add(temp);
        displayForTeamB(scoreTeamB);
        displayThrowsB(view);
    }

    public void displayThrowsB(View view) {
        String gameThrows = "";
        for (Integer n : throwsB) {
            gameThrows += n + "\n";
        }
        TextView t = (TextView) findViewById(R.id.b_throws);
        t.setText(gameThrows);
    }

    public void displayGamesA(View view){
        String games = "";
        for (Integer n : gamesA){
            games += n + "   ";
        }
        TextView t = (TextView) findViewById(R.id.team_a_games);
        t.setText(games);
    }

    public void displayGamesB(View view){
        String games = "";
        for (Integer n : gamesB){
            games += n + "   ";
        }
        TextView t = (TextView) findViewById(R.id.team_b_games);
        t.setText(games);
    }

    public void undoA(View view){
        if (throwsA.size() > 0) {
            scoreTeamA -= throwsA.removeLast();
            displayThrowsA(view);
            displayForTeamA(scoreTeamA);
        }
    }

    public void undoB(View view){
        if (throwsB.size() > 0) {
            scoreTeamB -= throwsB.removeLast();
            displayThrowsB(view);
            displayForTeamB(scoreTeamB);
        }
    }

    public void resetScores(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        throwsA.clear();
        throwsB.clear();
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayThrowsA(view);
        displayThrowsB(view);
    }

    public void submitScores(View view) {
        Intent data = new Intent();
        data.putExtra("playerOneNum", playerOneNum);
        data.putExtra("playerOneScore", scoreTeamA);
        data.putExtra("playerTwoNum", playerTwoNum);
        data.putExtra("playerTwoScore", scoreTeamB);
        data.putExtra("round", scoreTeamB);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }

    public void switchSides(View view){
        TextView playerAScore = (TextView) findViewById(R.id.team_a_score);
        TextView playerBScore = (TextView) findViewById(R.id.team_b_score);
        EditText playerOneText = (EditText) findViewById(R.id.team_a_name);
        EditText playerTwoText = (EditText) findViewById(R.id.team_b_name);
        TextView totalA = (TextView) findViewById(R.id.team_a_total);
        TextView totalB = (TextView) findViewById(R.id.team_b_total);

        LinkedList<Integer> tempList = new LinkedList<Integer>();
        tempList = (LinkedList) throwsA.clone();
        throwsA = (LinkedList) throwsB.clone();
        throwsB = (LinkedList) tempList.clone();

        tempList = (LinkedList) gamesA.clone();
        gamesA = (LinkedList) gamesB.clone();
        gamesB = (LinkedList) tempList.clone();

        int tempScore = 0;
        tempScore = scoreTeamA;
        scoreTeamA = scoreTeamB;
        scoreTeamB = tempScore;

        String tempName = "";
        tempName = playerOneText.getText().toString();
        playerOneText.setText(playerTwoText.getText().toString());
        playerTwoText.setText(tempName);

        flag = (flag + 1) % 2;
        if (flag == 0){
            playerAScore.setTextColor(Color.parseColor("#FF0000"));
            playerBScore.setTextColor(Color.parseColor("#0000FF"));
        }
        else
        {
            playerAScore.setTextColor(Color.parseColor("#0000FF"));
            playerBScore.setTextColor(Color.parseColor("#FF0000"));
        }

        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayThrowsA(view);
        displayThrowsB(view);
        displayGamesA(view);
        displayGamesB(view);
        displayMatchScores(view);
    }

    public void nextGame(View view){
        gamesA.add(scoreTeamA);
        gamesB.add(scoreTeamB);
        switchSides(view);
        resetScores(view);
    }

    public void displayMatchScores(View view){
        int scoreA = 0;
        int scoreB = 0;
        for ( Integer n : gamesA ) {
            scoreA += n;
        }
        for ( Integer n : gamesB ) {
            scoreB += n;
        }
        TextView totalA = (TextView) findViewById(R.id.team_a_total);
        TextView totalB = (TextView) findViewById(R.id.team_b_total);

        totalA.setText(Integer.toString(scoreA));
        totalB.setText(Integer.toString(scoreB));
    }

    public void nextMatch(View view) {
        matchResetFlag += 1;
        if (matchResetFlag > 1) {
            gamesA.clear();
            gamesB.clear();
            resetScores(view);
            flag = 1;
            switchSides(view);
            matchResetFlag = 0;
        }
    }


}
