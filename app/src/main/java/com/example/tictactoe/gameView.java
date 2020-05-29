package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.VIBRATOR_SERVICE;

public class gameView extends View {
    private Paint bg,moves,msg;
    private float cellsize,hmargin,vmargin;
    private int count = 1;
    private static final float WALL_THICKNESS = 10;
    public List<Float> fromx = new ArrayList<Float>();
    public List<Float> fromy = new ArrayList<Float>();
    public List<Integer> player = new ArrayList<Integer>();
    public  int[][] winposition = { {0,1,2} , {3,4,5} , {6,7,8} ,
                                    {0,3,6} , {1,4,7} , {2,5,8} ,
                                    {0,4,8} , {2,4,6} };
    public  int[] gamestate = {0,0,0,0,0,0,0,0,0};
    public boolean gameend = false,win = false;
    public String player1,player2,wintext;
    private Vibrator vibrator;
    private static MediaPlayer touch,winsound,draw;

    @SuppressLint("ServiceCast")
    public gameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //
        bg = new Paint();
        bg.setARGB(100,244,162,121);
        bg.setStrokeWidth(WALL_THICKNESS);
        //
        moves = new Paint();
        moves.setARGB(500,255,113,41);
        moves.setTextSize(240);
        moves.setFakeBoldText(true);
        //
        msg = new Paint();
        msg.setARGB(500,255,113,41);
        msg.setTextSize(100);
        msg.setFakeBoldText(true);
        SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("player1",MODE_PRIVATE);
        player1 = sharedPreferences1.getString("player1","Player 1");
        //
        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("player2",MODE_PRIVATE);
        player2 = sharedPreferences2.getString("player2","Player 2");
        //
        touch = MediaPlayer.create(context,R.raw.touch);
        winsound = MediaPlayer.create(context,R.raw.winning);
        draw = MediaPlayer.create(context,R.raw.draw);
        vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getWidth();
        int height = getHeight()+300;

        if(width/height<1){
            cellsize = width/(4);
        }
        else {
            cellsize = height/(4);
        }
        hmargin = (width-(3*cellsize))/2;
        vmargin = (height-(3*cellsize))/2;
        canvas.drawRGB(46,47,41);
        canvas.translate(hmargin,vmargin);
        canvas.drawLine(0,cellsize,cellsize*3,cellsize,bg);
        canvas.drawLine(0,cellsize*2,cellsize*3,cellsize*2,bg);
        canvas.drawLine(cellsize,0,cellsize,cellsize*3,bg);
        canvas.drawLine(cellsize*2,0,cellsize*2,cellsize*3,bg);
        checksequence();
        if(count==1){
            wintext = player1+"'s turn";
        }
        if(count==10){
            wintext = "Draw!!";
            draw.start();
        }
        float wintextsize = msg.measureText(wintext);
        canvas.drawText(wintext,(width-wintextsize)/2-hmargin,-200,msg);
        if(fromx.size()>0){
            for(int i=0;i<fromx.size();i++){
                String text;
                if(player.get(i)==1){
                    text = "X";
                }
                else {
                    text = "O";
                }
                float textsize = moves.measureText(text);
                canvas.drawText(text,fromx.get(i)+((cellsize-textsize)/2),fromy.get(i)-40+cellsize,moves);
                if(!win) {
                    touch.start();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if(!gameend){
                float a = event.getX();
                float b = event.getY();
                for(int y=0; y<3; y++) {
                    if(b>((y*cellsize)+vmargin) && b<(((y+1)*cellsize)+vmargin)){
                        for (int x=0;x<3;x++) {
                            if(a>((x*cellsize)+hmargin) && a<(((x+1)*cellsize)+hmargin)){
                                boolean repeat = false;
                                for(int i=0;i<fromx.size();i++){
                                    if((x*cellsize-fromx.get(i)==0)&&(y*cellsize-fromy.get(i)==0)){
                                        repeat = true;
                                    }
                                }
                                if(!repeat){
                                    //passing the values to arraylist
                                    fromx.add((x*cellsize));
                                    fromy.add((y*cellsize));
                                    if(count%2==1){
                                        player.add(1);
                                        wintext = player2+"'s turn";
                                    }
                                    else {
                                        player.add(2);
                                        wintext = player1+"'s turn";
                                    }
                                    for(int i=0;i<3;i++){
                                        if(b>(cellsize*i+vmargin) && b<(cellsize*(i+1)+vmargin)){
                                            gamestate[x+3*i] = player.get(player.size()-1);
                                        }
                                    }
                                    count++;
                                }
                            }
                        }
                    }
                }
                invalidate();
            }
        }
        return true;
    }

    public void checksequence() {
        for(int i=0;i<8;i++){
            if(gamestate[winposition[i][0]]>0){
                if((gamestate[winposition[i][0]]==gamestate[winposition[i][1]])&&(gamestate[winposition[i][1]]==gamestate[winposition[i][2]])){
                    gameend=true;
                    if(gamestate[winposition[i][0]]==1){
                        wintext = player1+" Wins !!";
                    }
                    else {
                        wintext = player2+" Wins !!";
                    }
                    winsound.start();
                    vibrator.vibrate(200);
                    win = true ;
                }
            }
        }
    }
}
