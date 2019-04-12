package com.totalaxe.totalaxescoring;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    int scorePlayerOne = 0;
    int scorePlayerTwo = 0;
    int playerOneNum = -1;
    int playerTwoNum = -1;
    int playerOneWins = 0;
    int playerOneLosses = 0;
    int playerOneTies = 0;
    int playerTwoWins = 0;
    int playerTwoLosses = 0;
    int playerTwoTies = 0;
    int flag = 0;
    int matchResetFlag = 0;
    int round = 0;
    LinkedList<Integer> throwsA = new LinkedList<Integer>();
    LinkedList<Integer> throwsB = new LinkedList<Integer>();
    LinkedList<Integer> gamesA = new LinkedList<Integer>();
    LinkedList<Integer> gamesB = new LinkedList<Integer>();
    String playerOne = "Player 1";
    String playerTwo = "Player 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText playerOneText = (EditText) findViewById(R.id.team_a_name);
        EditText playerTwoText = (EditText) findViewById(R.id.team_b_name);


        //String playerOne = getIntent().getStringExtra("playerOneName");
        //String playerTwo = getIntent().getStringExtra("playerTwoName");

        //round = Integer.parseInt(getIntent().getStringExtra("round"));
        //playerOneNum = Integer.parseInt(getIntent().getStringExtra("playerOneNum"));
        //playerTwoNum = Integer.parseInt(getIntent().getStringExtra("playerTwoNum"));

        playerOneText.setText(playerOne);
        playerTwoText.setText(playerTwo);


    }

    public void displayForTeamA(int text) {
        TextView t = (TextView) findViewById(R.id.team_a_score);
        t.setText("" + text);
        displayLead();
        matchResetFlag = 0;
    }

    public void displayForTeamB(int text) {
        TextView t = (TextView) findViewById(R.id.team_b_score);
        t.setText("" + text);
        displayLead();
        matchResetFlag = 0;
    }

    public void displayLead(){
        TextView ta = (TextView) findViewById(R.id.team_a_game_lead);
        TextView tb = (TextView) findViewById(R.id.team_b_game_lead);
        int scoreDiff = scorePlayerOne - scorePlayerTwo;
        if (scoreDiff > 0){
            ta.setText(playerOne + " leads by " + scoreDiff);
            tb.setText(playerTwo + " down by " + scoreDiff);
        } else if (scoreDiff < 0){
            ta.setText(playerOne + " down by " + Math.abs(scoreDiff));
            tb.setText(playerTwo + " leads by " + (Math.abs(scoreDiff)));
        }
        else {
            if (scorePlayerOne == 0){
            ta.setText("");
            tb.setText("");
            }else{
            ta.setText("Tied");
            tb.setText("Tied");
            }
        }
    }

    public void subA(View view) {
        int temp;
        temp = Integer.parseInt(view.getTag().toString());
        scorePlayerOne -= temp;
        displayForTeamA(scorePlayerOne);
    }

    public void addA(View view) {
        int temp;
        temp = Integer.parseInt(view.getTag().toString());
        scorePlayerOne += temp;
        throwsA.add(temp);
        displayForTeamA(scorePlayerOne);
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
        scorePlayerTwo -= temp;
        displayForTeamB(scorePlayerTwo);
    }

    public void addB(View view) {
        int temp;
        temp = Integer.parseInt(view.getTag().toString());
        scorePlayerTwo += temp;
        throwsB.add(temp);
        displayForTeamB(scorePlayerTwo);
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
            scorePlayerOne -= throwsA.removeLast();
            displayThrowsA(view);
            displayForTeamA(scorePlayerOne);
        }
    }

    public void undoB(View view){
        if (throwsB.size() > 0) {
            scorePlayerTwo -= throwsB.removeLast();
            displayThrowsB(view);
            displayForTeamB(scorePlayerTwo);
        }
    }

    public void resetScores(View view) {
        scorePlayerOne = 0;
        scorePlayerTwo = 0;
        throwsA.clear();
        throwsB.clear();
        displayForTeamA(scorePlayerOne);
        displayForTeamB(scorePlayerTwo);
        displayThrowsA(view);
        displayThrowsB(view);
    }

    public void submitScores(View view) {
        Intent data = new Intent();
        data.putExtra("playerOneNum", playerOneNum);
        data.putExtra("playerOneScore", scorePlayerOne);
        data.putExtra("playerTwoNum", playerTwoNum);
        data.putExtra("playerTwoScore", scorePlayerTwo);
        data.putExtra("round", scorePlayerTwo);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }

    public void switchSides(View view){
        TextView playerAScore = (TextView) findViewById(R.id.team_a_score);
        TextView playerBScore = (TextView) findViewById(R.id.team_b_score);
        TextView playerATotal = (TextView) findViewById(R.id.team_a_total);
        TextView playerBTotal = (TextView) findViewById(R.id.team_b_total);
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
        tempScore = scorePlayerOne;
        scorePlayerOne = scorePlayerTwo;
        scorePlayerTwo = tempScore;

        String tempName = "";
        tempName = playerOneText.getText().toString();
        playerOneText.setText(playerTwoText.getText().toString());
        playerTwoText.setText(tempName);

        flag = (flag + 1) % 2;
        if (flag == 0){
            playerAScore.setTextColor(Color.parseColor("#FF0000"));
            playerBScore.setTextColor(Color.parseColor("#0000FF"));
            playerATotal.setTextColor(Color.parseColor("#FF0000"));
            playerBTotal.setTextColor(Color.parseColor("#0000FF"));
        }
        else
        {
            playerAScore.setTextColor(Color.parseColor("#0000FF"));
            playerBScore.setTextColor(Color.parseColor("#FF0000"));
            playerATotal.setTextColor(Color.parseColor("#0000FF"));
            playerBTotal.setTextColor(Color.parseColor("#FF0000"));
        }

        displayForTeamA(scorePlayerOne);
        displayForTeamB(scorePlayerTwo);
        displayThrowsA(view);
        displayThrowsB(view);
        displayGamesA(view);
        displayGamesB(view);
        displayMatchScores(view);
    }

    public void nextGame(View view){
        gamesA.add(scorePlayerOne);
        gamesB.add(scorePlayerTwo);
        if (scorePlayerOne > scorePlayerTwo){
            playerOneWins += 1;
            playerTwoLosses += 1;
        } else if (scorePlayerOne < scorePlayerTwo){
            playerOneLosses += 1;
            playerTwoWins += 1;
        }else{
            playerOneTies += 1;
            playerTwoTies += 1;
        }
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
        EditText playerOneText = (EditText) findViewById(R.id.team_a_name);
        EditText playerTwoText = (EditText) findViewById(R.id.team_b_name);

        playerOneWins = 0;
        playerOneLosses = 0;
        playerOneTies = 0;
        playerTwoWins = 0;
        playerTwoLosses = 0;
        playerTwoTies = 0;

        matchResetFlag += 1;
        if (matchResetFlag > 1) {
            gamesA.clear();
            gamesB.clear();
            resetScores(view);
            if (flag == 0){
                //switches names
                String tempName = "";
                tempName = playerOneText.getText().toString();
                playerOneText.setText(playerTwoText.getText().toString());
                playerTwoText.setText(tempName);
            }
            flag = 1;
            switchSides(view);
            matchResetFlag = 0;
        }
    }


}
