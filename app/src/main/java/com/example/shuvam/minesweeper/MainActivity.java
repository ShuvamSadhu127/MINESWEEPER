package com.example.shuvam.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout rootLayout;
    public int SIZE=8;
    public int mine_KEY=8;
    public static int x_arnd,y_arnd;
    public static int MineSet=-1;
    public static int MineNotSet=1;
    public static boolean firstClick=true;
    public static final int incomplete=2;
    public static final int playerWon=7,playerLost=8;
    public static int currentStatus;

    public ArrayList<LinearLayout> rows;
    public MNSButton[][] board=new MNSButton[SIZE][SIZE];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout=findViewById(R.id.rootLayout);
        setupBoard();


    }
    public void setupBoard() {
        rows = new ArrayList<>();
        board = new MNSButton[SIZE][SIZE];
        rootLayout.removeAllViews();

        for (int i = 0; i < SIZE; i++) {

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);

            rootLayout.addView(linearLayout);
            rows.add(linearLayout);
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                MNSButton button = new MNSButton(this);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(this);
                LinearLayout row = rows.get(i);
                row.addView(button);
                board[i][j] = button;
                board[i][j].setXY(i,j);
            }
        }
    }
    public void setMines(){
        Random random=new Random();
        Random random1=new Random();
        for (int cnt=0;cnt< SIZE*SIZE/mine_KEY;){
            int x,y;
            x=random.nextInt(SIZE);
            y=random1.nextInt(SIZE);
            boolean p=true;
            for (int i=x_arnd-1;i<x_arnd+2;i++){
                for (int j=y_arnd-1;j<y_arnd+2;j++){
                    if (i==x && j==y)
                        p=false;
                }
            }
            if (p && board[x][y].mineStatus()!=MineSet) {
                board[x][y].putMines(board, SIZE, SIZE);
                cnt++;
            }

        }
    }
    @Override
    public void onClick(View view) {

            MNSButton button = (MNSButton) view;
            //FOR CHECKING FIRST CLICK
            if (firstClick){
                firstClick=false;
                x_arnd=button.x;
                y_arnd=button.y;
                setMines();
            }
            if (currentStatus==incomplete) {
                button.reveal(board, SIZE, SIZE);
                updateGameStatus();
                checkGameStatus();
            }




    }
    public void checkGameStatus(){
        if (currentStatus==playerLost)
            Toast.makeText(this,"You Lost",Toast.LENGTH_LONG).show();
        else if (currentStatus==playerWon)
            Toast.makeText(this,"You Won",Toast.LENGTH_LONG).show();
    }

    public void updateGameStatus(){
            boolean check=true;
            for (int i=0;i<SIZE;i++){
                for (int j=0;j<SIZE;j++){
                    if (board[i][j].notMine() && !(board[i][j].revealStatus))
                        check=false;
                }
            }
            if (check) {
                currentStatus = playerWon;
            }

    }
}
