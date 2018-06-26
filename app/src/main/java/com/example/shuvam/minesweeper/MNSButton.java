package com.example.shuvam.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import java.math.*;
import java.util.Random;

public class MNSButton extends android.support.v7.widget.AppCompatButton {
    int x,y;
    public int mineState=MainActivity.MineNotSet;
    public int numMines=0;
    public boolean revealStatus=false;
    //private int player = MainActivity.NO_PLAYER;


    public MNSButton(Context context) {
        super(context);

    }

    public void putMines(MNSButton board[][],int row, int col) {
        mineState = MainActivity.MineSet;
        int i, j;
        for (i = this.x - 1; i < this.x + 2; i++) {
            for (j = this.y - 1; j < this.y + 2; j++) {
                if (i >= 0 && i < row && j >= 0 && j < col && board[i][j].mineState == MainActivity.MineNotSet)
                    board[i][j].numMines++;
            }
        }
    }
    public void setXY(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int mineStatus(){
        return this.mineState;
    }

    public int getMineState(){
        if (mineState==MainActivity.MineNotSet) {
            return numMines;
        }
        else {
            return mineState;
        }

    }
    public void reveal(MNSButton board[][],int row,int col){
        int n=this.getMineState();
        if(!revealStatus){
            if (n!=MainActivity.MineSet && numMines==0){
                this.setEnabled(false);
                revealStatus=true;
                revealNeighbours(board,row,col,this.x,this.y);

            }
            else if (n!=MainActivity.MineSet && numMines!=0){
                this.setEnabled(false);
                revealStatus=true;
                this.setText(n);
            }
            else if (MainActivity.MineSet == this.mineState){
                this.setText("X");
                revealStatus=true;
                this.setEnabled(false);
                MainActivity.currentStatus=MainActivity.playerLost;
                revealAllMines(board,row,col);

            }
        }
    }
    public void revealAllMines(MNSButton board[][],int row,int col){
        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if (board[i][j].mineState==MainActivity.MineSet){
                    board[i][j].reveal(board,row,col);
                }
            }
        }
    }
    public  boolean notMine(){
        if (this.mineState==MainActivity.MineNotSet) return true;
        else return false;
    }
    public void revealNeighbours(MNSButton board[][],int row,int col,int x,int y){
        for (int i=x-1;i<x+2;i++){
            for (int j=y-1;j<y+2;j++){
                if (i>=0 && i<row && j>=0 && j<col && board[i][j].mineState==MainActivity.MineNotSet && !(i==x && j==y)){
                    int n=getMineState();
                    if (n!=MainActivity.MineSet && n==0){
                        board[i][j].setEnabled(false);
                        revealStatus=true;
                        board[i][j].reveal(board,row,col);

                    }
                    else if (n!=MainActivity.MineSet && n!=0){
                        board[i][j].setEnabled(false);
                        revealStatus=true;
                        board[i][j].setText(n);
                    }
                }
            }
        }
    }



   /* public void setPlayer(int player){
        this.player = player;
        if(player == MainActivity.PLAYER_X){
            setText("X");
        }else if(player == MainActivity.PLAYER_O){
            setText("O");
        }
        setEnabled(false);
    }*/

    /*public int getPlayer(){
        return this.player;
    }

    public boolean isEmtpy(){
        return this.player == MainActivity.NO_PLAYER;*/

//        if(this.player == MainActivity.NO_PLAYER){
//            return  true;
//        }
//        else {
//            return  false;
//        }
    }


