package edu.hzuapps.androidworks.homeworks.net1314080903138;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;


/**
 * Created by Administrator on 2016/4/26.
 */
public class Net1314080903138ChessBoard extends GridLayout {
    public Net1314080903138ChessBoard(Context context) {
        super(context);
        initChessBoard();
    }

    public Net1314080903138ChessBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChessBoard();

    }

    public Net1314080903138ChessBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initChessBoard();

    }

    private void initChessBoard(){

        //设置边界
        for(int y=0; y<10; y++){
            for(int x=0; x<10; x++){
                if(x==0||x==9||y==0||y==9){
                    cardsMap[x][y] = new Net1314080903138Card(getContext());
                    cardsMap[x][y].setNum(3);
                }
            }
        }

        setOnTouchListener(new View.OnTouchListener() {
            int x;
            int y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    //getSituation();

                    //相对于父组件的坐标
                    x = (int) event.getX();
                    y = (int) event.getY();

                    //在竖屏是，width 比 height 短； 在横屏的时候， height 比 width 短。
                    int length = Math.min(getWidth(), getHeight()) / 8;

                    //row 和 colu 的值在任何时候应该都小于9
                    int row = (int) Math.floor(y / length) + 1;
                    int colu = (int) Math.floor(x / length) + 1;

                    if (row < 9 && colu < 9) {
                        int[] temp = isValid(colu, row, current_color);


                        if (temp != null) {
//                            //************************** Test Information ***************************************
//                            System.out.println("Position: (" + colu + ", " + row + ") is valid!!");
//                            for (int i = 0; i < 10; i++) {
//                                for (int j = 0; j < 10; j++) {
//                                    System.out.print(temp[i * 10 + j] + "");
//                                }
//                                System.out.println();
//                            }
//                            //************************** Test Information ***************************************



                            getSituation();
                            blackCount = 0;
                            whiteCount = 0;

                            Net1314080903138Game.getGame().setRetractCount(0);
                            Net1314080903138Game.getGame().setNotfirstStep(true);

                            for (int y = 1; y <= 8; y++) {
                                for (int x = 1; x <= 8; x++) {
                                    //对棋盘进行刷新
                                    cardsMap[x][y].setNum(temp[10 * y + x]);

                                    switch (temp[10 * y + x]) {
                                        case 1:
                                            blackCount++;
                                            break;
                                        case 2:
                                            whiteCount++;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                            emptyCount = 64 - blackCount - whiteCount;
                            Net1314080903138Game.getGame().setTvBlackCount(blackCount);
                            Net1314080903138Game.getGame().setTvWhiteCount(whiteCount);

                            load();
                            current_color = 3 - current_color;
                            Net1314080903138Game.getGame().setIvCurrentTurn(current_color);
                            hint();

                            if (pass(current_color)) {
                                //Toast.makeText(getContext(), "No valid empty position", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("No valid empty position." + "\n" + "Please pass to the rival!");
                                builder.setView(Net1314080903138Game.getGame().getLlDialog(5));
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        passTimes++;
                                        current_color = 3 - current_color;
                                        hint();
                                        Net1314080903138Game.getGame().setIvCurrentTurn(current_color);
                                        dialog.dismiss();
                                    }
                                });

//                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
                                builder.create().show();

//                                passTimes++;
//                                current_color = 3 - current_color;
//                                hint();


                            } else {
                                passTimes = 0;
                            }

                            if (emptyCount == 0 || passTimes > 1) {
                                int winer;

                                if (blackCount > whiteCount) {
                                    winer = 1;
                                } else if (blackCount < whiteCount) {
                                    winer = 2;
                                } else {
                                    winer = 0;
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("The game is over. " + "\n" + "Do you want to start a new game?");
                                builder.setView(Net1314080903138Game.getGame().getLlDialog(winer));
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startNewGame();
                                        dialog.dismiss();
                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }

                        } else {
                            Toast.makeText(getContext(), "This position is invalid!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //cardsMap[colu][row].setNum(1);
//                    System.out.println((colu + "") + "---" + (row + ""));
                }
                return true;
            }
        });
    }

    public void startNewGame(){
        //初始化棋盘
        for(int y=1; y<=8; y++){
            for(int x=1; x<=8; x++){
                cardsMap[x][y].setNum(0);
            }
        }

        cardsMap[4][4].setNum(2);
        cardsMap[5][5].setNum(2);
        cardsMap[4][5].setNum(1);
        cardsMap[5][4].setNum(1);
        blackCount = 2;
        whiteCount = 2;

        //清除比分等记录
        Net1314080903138Game.getGame().clearRecord();

        //黑子先走
        current_color = 1;

        //重新载入棋盘的情况
        load();
        getSituation();

        hint();
        Net1314080903138Game.getGame().setNotfirstStep(false);

    }

    public void retract(){
        for(int y=1; y<=8; y++){
            for(int x=1; x<=8; x++){
                cardsMap[x][y].setNum(preSituation[10*y+x]);
            }
        }

        emptyCount = 64 - blackCount - whiteCount;
        Net1314080903138Game.getGame().setTvBlackCount(blackCount);
        Net1314080903138Game.getGame().setTvWhiteCount(whiteCount);

        load();
        //current_color = 3-current_color;
        current_color = preCurrentcolor;
        passTimes=0;
        Net1314080903138Game.getGame().setIvCurrentTurn(current_color);

        if(hint_on_off){
            for(int y=1; y<=8; y++){
                for(int x=1; x<=8; x++){
                    if((isValid(x,y,current_color) != null)){
                        cardsMap[x][y].setHint();
                    }
                }
            }
        }

    }

    private void getSituation(){
        for(int y=0; y<100; y++){
            preSituation[y] = situation[y];
        }

        preBlackCount = blackCount;
        preWhiteCount = whiteCount;
        preCurrentcolor = current_color;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Net1314080903138Card net1314080903138Card;
        super.onSizeChanged(w, h, oldw, oldh);
        int width = this.getWidth();
        int height = this.getHeight();
        int cardWidth = (Math.min(width, height)-4)/8;

        for(int y=1; y<=8; y++){
            for(int x=1; x<=8; x++){
                //card = new Card(getContext(),x,y);
                net1314080903138Card = new Net1314080903138Card(getContext());
                GridLayout.Spec rowSpec = GridLayout.spec(y-1);
                GridLayout.Spec ColumnSpec = GridLayout.spec(x-1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, ColumnSpec);
                params.width = cardWidth;
                params.height = cardWidth;
                params.setGravity(Gravity.TOP);
                addView(net1314080903138Card, params);

                cardsMap[x][y] = net1314080903138Card;
            }
        }

        cardsMap[4][4].setNum(2);
        cardsMap[5][5].setNum(2);
        cardsMap[4][5].setNum(1);
        cardsMap[5][4].setNum(1);
        blackCount = 2;
        Net1314080903138Game.getGame().setTvBlackCount(blackCount);
        whiteCount = 2;
        Net1314080903138Game.getGame().setTvWhiteCount(whiteCount);
        emptyCount = 64 - blackCount - whiteCount;


//        //************************** Test Information ***************************************
//        System.out.println("************onSizechange: cardsMap************");
//        for(int y=0; y<10; y++){
//            for(int x=0; x<10; x++){
//                System.out.print(cardsMap[x][y].getNum()+" ");
//            }
//            System.out.println();
//        }
//        //************************** Test Information ***************************************

        load();

    }


    private void load(){
        for(int y=0; y<10; y++){
            for(int x=0; x<10; x++){
                situation[y*10+x] = cardsMap[x][y].getNum();
            }
        }
    }

    //此轮是否没有可以下的位置了？ 如果没有就轮到对手下
    private boolean pass(int current_color){
        if(emptyCount == 0){
            return false;
        }

        boolean canPass = true;

        for(int y=1; y<=8; y++){
            for(int x=1; x<=8; x++){
                if((isValid(x,y,current_color) != null)){

                    canPass = false;
                    return canPass;
                }
            }
        }
        return canPass;
    }

    private void hint(){
        if(hint_on_off){
            for(int y=1; y<=8; y++){
                for(int x=1; x<=8; x++){
                    if((isValid(x,y,current_color) != null)){
                        cardsMap[x][y].setHint();
                    }
                }
            }
        }
    }

    private int[] isValid(int x, int y, int current_color){
        //load();
        boolean canKill = false;

        int[] tempSituation = situation.clone();

//        //************************** Test Information ***************************************
//        System.out.println("$$$$$$$$$$$$$$$$$$ isValid: tempSituation, the copy of situation $$$$$$$$$$$$$$$$$$$$$$");
//        for(int i=0; i<10; i++){
//            for(int j=0; j<10; j++){
//                System.out.print(tempSituation[i*10+j]+" ");
//            }
//            System.out.println();
//        }
//        //************************** Test Information ***************************************

        //如果（x，y）不为空则不需要判断
        if(tempSituation[y*10+x]!=0) return null;

        //搜索（x,y）的八个方向，找可以吃的子
        for(int i=0; i<8; i++){
            int p =y*10+x+directions[i];

            //如果在一个方向找到颜色不一样的子则往这个方向继续搜索
            if(tempSituation[p]==3-current_color){
                while(tempSituation[p]!=0 && tempSituation[p]!=3){
                    p += directions[i];

                    //再找到一个同色的子就满足吃子的条件了
                    if(tempSituation[p]==current_color){
                        canKill=true;

                        //将中间夹着的对手的子翻转
                        while ((p = p-directions[i]) != 10*y+x){
                            tempSituation[p] = current_color;
                        }
                        tempSituation[p] = current_color;
                        break;
                    }

                }
            }
            else{
                continue;
            }

        }

        if(canKill){
            return tempSituation;
        }
        else{
            return null;
        }
    }

    public void hint_switch(){
        hint_on_off = !hint_on_off;
        for(int y=1; y<=8; y++){
            for(int x=1; x<=8; x++){
                if((isValid(x,y,current_color) != null)){
                    if(hint_on_off){
                        cardsMap[x][y].setHint();
                    }
                    else {
                        cardsMap[x][y].moveHInt();
                    }


                }
            }
        }
    }

    private int[] directions = {-11,-10,-9,-1,1,9,10,11};
    private int[] situation = new int[100];
    private int[] preSituation = new int[100];
    private int emptyCount = 0;
    private int preEmptyCount = 0;
    private int blackCount = 0;
    private int preBlackCount = 0;
    private int whiteCount = 0;
    private int preWhiteCount = 0;
    private boolean hint_on_off = false;

    private Net1314080903138Card[][] cardsMap = new Net1314080903138Card[10][10];
    private int current_color=1;
    private int passTimes = 0;
    private int preCurrentcolor=1;


}
