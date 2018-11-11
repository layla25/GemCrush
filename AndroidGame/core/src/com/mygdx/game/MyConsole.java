/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.List;


 /*
 * @author Layla
 **/
public class MyConsole {
    private static MyConsole myconsole= new MyConsole();
    final int DEFAULT_FRAME_WIDTH = 806;
    final int DEFAULT_FRAME_HEIGHT = 629;
    static List textToDraw=new ArrayList();
    static List imgToDraw=new ArrayList();
    public static MyConsole getInstance()
    {
        return myconsole;
    }
    public void setBackground(String s)
    {
    }
    public Rectangle getClickedPoint()
    {
        Rectangle ClickedPoint=Demo1.ClickedPoint;
        if(ClickedPoint!=null)
        {
            Rectangle temp=ClickedPoint;
            Demo1.clearClickedPoint();
            return temp;
        }
        else return null;
    }
    public void clear()
    {
    }
    public void drawText(final int x,final int y,final String s) {
        textToDraw.add(new MyTextPair(s,x,y));
    }
    public void drawImage(final int x,final int y,final String imagestr)
    {
        imgToDraw.add(new MyImgPair(imagestr,x,y));
    }
    public void close()
    {
    }
    public boolean shouldUpdate()
    {
        return true;
    }
    public void update()
    {
    }
    public void idle(int x)
    {
    }
}
class MyTextPair
{
    private String s;
    private int x,y;
    MyTextPair(String str,int x1, int y1)
    {
        s=str;x=x1;y=y1;
    }
    public String getStr(){return s;}
    public int getX(){return x;}
    public int getY(){return y;}
}
class MyImgPair
{
    private String imgstr;
    private int x,y;
    MyImgPair(String i, int x1, int y1)
    {
        imgstr=i;x=x1;y=y1;
    }
    public String getImg(){return imgstr;}
    public int getX(){return x;}
    public int getY(){return y;}
}

