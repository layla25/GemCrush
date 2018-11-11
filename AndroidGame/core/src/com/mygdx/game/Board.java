package com.mygdx.game;

/**
 * Created by Layla on 2017/4/13.
*/
import static com.mygdx.game.Gem.h;
import static com.mygdx.game.Gem.orgX;
import static com.mygdx.game.Gem.orgY;
import static com.mygdx.game.Gem.w;
import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {
    private static int score=0;
    static Set toDrop=new HashSet();
    public static int previewA,previewB;
    public static boolean frog,chocolate;
    private boolean terminate=false;
    public static void clearScore() {
        score = 0;
    }
    public static int getScore()
    {
        return score;
    }
    public static void setScore(int x)
    {
        score=x;
    }
    public static void createGem(int i,int j,Gem[]table,Random initial)
    {
        switch (initial.nextInt(7)) {
            case 0:
                table[8 * j + i] = new Gem("bear.png", i, j, 0);
                break;
            case 1:
                table[8 * j + i] = new Gem("dolphin.png", i, j, 1);
                break;
            case 2:
                table[8 * j + i] = new Gem("bird.png", i, j, 2);
                break;
            case 3:
                table[8 * j + i] = new Gem("snail.png", i, j, 3);
                break;
            case 4:
                table[8 * j + i] = new Gem("dog.png", i, j, 4);
                break;
            case 5:
                table[8 * j + i] = new Gem("butterfly.png", i, j, 5);
                break;
            case 6:
                table[8 * j + i] = new Gem("frog.png", i, j, 6);
                break;
        }
        switch(initial.nextInt(64)){
            case 2:
                table[8 * j + i].setFrozen();
                break;
            case 3:
                table[8 * j + i].setChocolate();
                break;
        }
    }
    public static void initBoard(Gem[] table)
    {
        Random initial = new Random(System.currentTimeMillis());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                createGem(i,j,table,initial);
                table[8 * j + i].display();
            }
        }
    }
    public static boolean checkChain(Gem[]table)
    {
        return checkThree(table);
    }
    public static boolean checkPreview(Gem[]table)
    {
        for(int i=0;i<=7;i++)
            for(int j=0;j<=7;j++)
            {
                if(j<=5)
                {
                    if(i>=1)
                    {
                        if((table[8*j+i].getPicId()==table[8*j+7+i].getPicId())&&(table[8*j+7+i].getPicId()==table[8*j+16+i].getPicId()))
                        {
                            previewA=8*j+7+i;previewB=8*j+8+i;return true;
                        }
                        if((table[8*j-1+i].getPicId()==table[8*j+8+i].getPicId())&&(table[8*j+8+i].getPicId()==table[8*j+16+i].getPicId()))
                        {
                            previewA=8*j-1+i;previewB=8*j+i;return true;
                        }
                        if((table[8*j+i].getPicId()==table[8*j+8+i].getPicId())&&(table[8*j+8+i].getPicId()==table[8*j+15+i].getPicId()))
                        {
                            previewA=8*j+16+i;previewB=8*j+15+i;return true;
                        }
                    }
                    if(i<=6)
                    {
                        if((table[8*j+i].getPicId()==table[8*j+9+i].getPicId())&&(table[8*j+9+i].getPicId()==table[8*j+16+i].getPicId()))
                        {
                            previewA=8*j+9+i;previewB=8*j+8+i;return true;
                        }
                        if((table[8*j+1+i].getPicId()==table[8*j+8+i].getPicId())&&(table[8*j+8+i].getPicId()==table[8*j+16+i].getPicId()))
                        {
                            previewA=8*j+1+i;previewB=8*j+i;return true;
                        }
                        if((table[8*j+i].getPicId()==table[8*j+8+i].getPicId())&&(table[8*j+8+i].getPicId()==table[8*j+17+i].getPicId()))
                        {
                            previewA=8*j+16+i;previewB=8*j+17+i;return true;
                        }
                    }
                }
                if(i<=5)
                {
                    if(j>=1)
                    {
                        if((table[8*j+i].getPicId()==table[8*j-7+i].getPicId()) &&(table[8*j-7+i].getPicId()==table[8*j+2+i].getPicId()))
                        {
                            previewA=8*j-7+i;previewB=8*j+1+i;return true;
                        }
                        if((table[8*j-8+i].getPicId()==table[8*j+1+i].getPicId()) &&(table[8*j+1+i].getPicId()==table[8*j+2+i].getPicId()))
                        {
                            previewA=8*j-8+i;previewB=8*j+i;return true;
                        }
                        if((table[8*j+i].getPicId()==table[8*j+1+i].getPicId()) &&(table[8*j+1+i].getPicId()==table[8*j-6+i].getPicId()))
                        {
                            previewA=8*j-6+i;previewB=8*j+2+i;return true;
                        }
                    }
                    if(j<=6)
                    {
                        if((table[8*j+i].getPicId()==table[8*j+9+i].getPicId()) &&(table[8*j+9+i].getPicId()==table[8*j+2+i].getPicId()))
                        {
                            previewA=8*j+9+i;previewB=8*j+1+i;return true;
                        }
                        if((table[8*j+8+i].getPicId()==table[8*j+1+i].getPicId()) &&(table[8*j+1+i].getPicId()==table[8*j+2+i].getPicId()))
                        {
                            previewA=8*j+8+i;previewB=8*j+i;return true;
                        }
                        if((table[8*j+i].getPicId()==table[8*j+1+i].getPicId()) &&(table[8*j+1+i].getPicId()==table[8*j+10+i].getPicId()))
                        {
                            previewA=8*j+10+i;previewB=8*j+2+i;return true;
                        }
                    }
                }
            }
        return false;
    }
    public static boolean checkThree(Gem[]table)
    {
        Random initial = new Random(System.currentTimeMillis());
        boolean success=false;
        for(int i=0;i<6;i++)
            for(int j=0;j<6;j++)
            {
                if((table[8*j+i].getPicId()==table[8*j+8+i].getPicId())&&(table[8*j+8+i].getPicId()==table[8*j+16+i].getPicId()))
                {
                    if(!checkFourFive(i,j,i,j+2,false,table,initial))
                        drop(i,j+2,3,table,initial);
                    success=true;
                }
                if((table[8*j+i].getPicId()==table[8*j+1+i].getPicId()) &&(table[8*j+1+i].getPicId()==table[8*j+2+i].getPicId()))
                {
                    if(!checkFourFive(i,j,i+2,j,true,table,initial))
                    {
                        drop(i,j,1,table,initial);
                        drop(i+1,j,1,table,initial);
                        drop(i+2,j,1,table,initial);
                    }
                    success=true;
                }
            }
        for(int j=6;j<8;j++)
            for(int i=0;i<6;i++)
                if((table[8*j+i].getPicId()==table[8*j+1+i].getPicId())&&(table[8*j+1+i].getPicId()==table[8*j+2+i].getPicId()))
                {
                    if(!checkFourFive(i,j,i+2,j,true,table,initial))
                    {
                        drop(i,j,1,table,initial);
                        drop(i+1,j,1,table,initial);
                        drop(i+2,j,1,table,initial);
                    }
                    success=true;
                }
        for(int i=6;i<8;i++)
            for(int j=0;j<6;j++)
                if((table[8*j+i].getPicId()==table[8*j+8+i].getPicId())&&(table[8*j+8+i].getPicId()==table[8*j+16+i].getPicId()))
                {
                    if(!checkFourFive(i,j,i,j+2,false,table,initial))
                        drop(i,j+2,3,table,initial);
                    success=true;
                }
        return success;
    }
    private static boolean checkFourFive(int x1,int y1,int x2,int y2,boolean left,Gem[]table,Random initial)
    {
        boolean flag=false;
        if(left)
        {
            if(x1>0&&table[8*y1+x1-1].getPicId()==table[8*y1+x1].getPicId())
                flag=true;
            if(x2<7&&table[8*y2+x2+1].getPicId()==table[8*y2+x2].getPicId())
            {
                if(flag==true)//middle 5
                    drop(x1-1,y2,1,table,initial);
                for(int t=x1;t<=x2+1;t++)//last 4
                    drop(t,y2,1,table,initial);
                if(x2<6&&table[8*y2+x2+1].getPicId()==table[8*y2+x2+2].getPicId())//right 5
                    drop(x2+2,y2,1,table,initial);
                return true;
            }
            if(flag==true)//first 4
            {
                if(x2>1&&table[8*y1+x1-2].getPicId()==table[8*y1+x1-1].getPicId())//left 5
                    drop(x1-2,y1,1,table,initial);
                for(int t=x1-1;t<=x2;t++)
                    drop(t,y2,1,table,initial);
                return true;
            }
        }
        else
        {
            if(y1>0&&table[8*y1+x1-8].getPicId()==table[8*y1+x1].getPicId())
                flag=true;
            if(y2<7&&table[8*y2+x2+8].getPicId()==table[8*y2+x2].getPicId())
            {
                if(flag==true)//middle 5
                    drop(x1,y2+1,5,table,initial);
                else if(y2<6&&table[8*y2+x2+8].getPicId()==table[8*y2+x2+16].getPicId())//last 5
                    drop(x1,y2+2,5,table,initial);
                else //last 4
                    drop(x2,y2+1,4,table,initial);
                return true;
            }
            if(flag==true)//first 4
            {
                if(y1>1&&table[8*y1+x1-8].getPicId()==table[8*y1+x1-16].getPicId())//first 5
                    drop(x1,y2,5,table,initial);
                else//first 4
                    drop(x2,y2,4,table,initial);
                return true;
            }
        }
        return false;
    }

    public static void drop(int x,int y,int len,Gem[]table,Random initial)
    {
        score+=10*len;
        System.out.println(x+" "+y+" len:"+len);
        for(int i=x,j=y;j>=len;j--)
        {
            checkChocolate(table[8*j+i]);
            table[8*j+i].dropGem(table[8*j+i-8*len],table);
            toDrop.add(i+8*j);
        }
        for(int k=0;k<len;k++)
        {
            createGem(x,k,table,initial);
            table[x+8*k].hideGem();
            table[x+8*k].setNextPicId(table[x+8*k].getPicId());
            toDrop.add(x+8*k);
        }
    }



    public static void checkChocolate(Gem g)
    {
        if(g.chocolate==true)
        {
            score+=40;
            chocolate=true;
        }
    }
    /*public static void DisplayScore(int x,int y,int iteration)
    {
        if(frog)
            MyConsole.getInstance().drawText((int) (x * w + w/2+orgX), (int) (y * h + h/2+orgY), "-1s", new Font("Helvetica", Font.BOLD, 20), Color.white);
        else if(chocolate)
            MyConsole.getInstance().drawText((int) (x * w + w/2+orgX), (int) (y * h + h/2+orgY), "40", new Font("Helvetica", Font.BOLD, 20), Color.white);
        else
            MyConsole.getInstance().drawText((int) (x * w + w/2+orgX), (int) (y * h + h/2+orgY), "10", new Font("Helvetica", Font.BOLD, 20), Color.white);
    }*/
    public static void showAll(Gem[] table){
        for(int i=0; i<64; i++){
            table[i].picstr=table[i].getStringFromPicId(true);
        }
    }
}
