package com.totalaxe.totalaxescoring;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int playerOneNum = -1;
    int playerTwoNum = -1;
    int round = 0;

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
    }

    public void displayForTeamB(int text) {
        TextView t = (TextView) findViewById(R.id.team_b_score);
        t.setText("" + text);
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
        displayForTeamA(scoreTeamA);
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
        displayForTeamB(scoreTeamB);
    }

    public void resetScores(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
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
}
