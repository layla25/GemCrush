package com.mygdx.game;

/**
 * Created by Layla on 2017/4/13.
 */
import com.badlogic.gdx.math.Rectangle;

import java.awt.Image;
import java.awt.Point;
import static java.lang.Math.abs;
import javax.swing.ImageIcon;

/**
 * Sample design of a toggle-enable gem
 *
 * @author Your Name and ID
*/
public class Gem {

    // the upper-left corner of the board, reference origin point
    public static final int orgX = 240;
    public static final int orgY = 40;
    // the size of the gem
    public static final int w = 65;
    public static final int h = 65;
    // default position in 8x8 grid
    private int posX = 0;
    private int posY = 0;
    public boolean selected = false;
    public boolean frozen=false;
    public boolean chocolate=false;
    public boolean hide=false;

    //Image pic;
    String picstr;
    int picId;
    private int nextpicId;
    private Image focus;
    private Image blank;
    private Image frozen_pic;
    private Image chocolate_pic;

    Gem(String file, int x, int y,int picId) {
        //this.focus = new ImageIcon(this.getClass().getResource("focus.png")).getImage();
        //this.blank=new ImageIcon(this.getClass().getResource("blank.png")).getImage();
        //this.frozen_pic = new ImageIcon(this.getClass().getResource("spider-web.png")).getImage();
        //this.chocolate_pic = new ImageIcon(this.getClass().getResource("candy.png")).getImage();
        //this.pic = new ImageIcon(this.getClass().getResource(file)).getImage();
        this.posX = x;
        this.posY = y;
        this.picId=picId;
        this.picstr=file;
    }

    public void display() {
        MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) (posY * h + orgY), this.getStringFromPicId(false));
        if (this.selected) {
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) (posY * h + orgY), "focus.png");
        }
        if (this.frozen&&hide==false){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) (posY * h + orgY), getBonusString());
        }
        else if (this.chocolate&&hide==false){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) (posY * h + orgY), getBonusString());
        }
    }

    public boolean isAt(Rectangle point) {
        if (point != null) {
            return (point.x > (posX * w + orgX) && point.x <= ((posX + 1) * w + orgX) && point.y > (posY * h + orgY) && point.y <= ((posY + 1) * h + orgY));
        } else {
            return false;
        }
    }

    public int getPicId() {
        return picId;
    }
    public void setNextPicId(int x)
    {
        this.nextpicId=x;
    }
    public String getStringFromPicId(boolean next){
        int temp=0;
        if(next)temp=nextpicId;
        else temp=picId;
        switch(temp){
            case 0:
                return "bear.png";
            case 1:
                return "dolphin.png";
            case 2:
                return "bird.png";
            case 3:
                return "snail.png";
            case 4:
                return "dog.png";
            case 5:
                return "butterfly.png";
            case 6:
                return "frog.png";
        }
        return "";
    }

    public String getBonusString(){
        if(this.frozen==true)
            return "spider-web.png";
        else if(this.chocolate==true)
            return "candy.png";
        else
            return "";
    }

    public Image getPicFromId(boolean notnext){
        int temp;
        if(notnext)
            temp=picId;
        else temp=nextpicId;
        switch(temp){
            case 0:
                return new ImageIcon(this.getClass().getResource("bear.png")).getImage();
            case 1:
                return new ImageIcon(this.getClass().getResource("dolphin.png")).getImage();
            case 2:
                return new ImageIcon(this.getClass().getResource("bird.png")).getImage();
            case 3:
                return new ImageIcon(this.getClass().getResource("snail.png")).getImage();
            case 4:
                return new ImageIcon(this.getClass().getResource("dog.png")).getImage();
            case 5:
                return new ImageIcon(this.getClass().getResource("butterfly.png")).getImage();
            case 6:
                return new ImageIcon(this.getClass().getResource("frog.png")).getImage();
        }
        return null;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public void setFrozen(){
        frozen=true;
    }

    public void setChocolate(){
        chocolate=true;
    }

    public void resetBonus(){
        frozen=false;
        chocolate=false;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggleFocus() {
        selected = !selected;
    }

    public boolean swiftGem(Gem other) {
        if (this.canSwift(other))
        {
            this.toggleFocus();
            other.toggleFocus();

            int temp1 = this.picId;
            this.picId = other.picId;
            other.picId=temp1;

            boolean temp2 = this.chocolate;
            this.chocolate=other.chocolate;
            other.chocolate=temp2;

            this.hide=true;
            other.hide=true;

            this.picstr = "blank.png";
            other.picstr="blank.png";
            return true;
        }
        return false;
    }

    public void swiftAnimation(Gem other, int iteration) {
        int xDirection=other.posX-this.posX;
        int yDirection=other.posY-this.posY;
        MyConsole.getInstance().drawImage((int) (posX * w + orgX+5*iteration*xDirection), (int) (posY * h + orgY+5*iteration*yDirection), other.getStringFromPicId(true));
        MyConsole.getInstance().drawImage((int) (other.posX * w + orgX-5*iteration*xDirection), (int) (other.posY * h + orgY-5*iteration*yDirection), getStringFromPicId(true));
        if(this.chocolate==true&&other.chocolate!=true)
            MyConsole.getInstance().drawImage((int) (other.posX * w + orgX-5*iteration*xDirection), (int) (other.posY * h + orgY-5*iteration*yDirection), "candy.png");
        else if(this.chocolate!=true&&other.chocolate==true)
            MyConsole.getInstance().drawImage((int) (posX * w + orgX+5*iteration*xDirection), (int) (posY * h + orgY+5*iteration*yDirection), "candy.png");
        else if(this.chocolate==true&&other.chocolate==true){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX+5*iteration*xDirection), (int) (posY * h + orgY+5*iteration*yDirection), "candy.png");
            MyConsole.getInstance().drawImage((int) (other.posX * w + orgX-5*iteration*xDirection), (int) (other.posY * h + orgY-5*iteration*yDirection), "candy.png");
        }
    }

    public boolean canSwift(Gem other) {
        if(this.posX==other.posX&&this.posY==other.posY)
            return false;
        else if (this.posX == other.posX && abs(this.posY - other.posY) == 1&&this.frozen==false&&other.frozen==false)
            return true;
        else if (this.posY == other.posY && abs(this.posX - other.posX) == 1&&this.frozen==false&&other.frozen==false)
            return true;
        else
            return false;
    }

    public void dropGem(Gem top,Gem[]table)
    {
        this.nextpicId=top.picId;
        this.frozen=top.frozen;
        this.chocolate=top.chocolate;
        hide=true;
        top.resetBonus();
        this.hideGem();
    }
    public void dropAnimation(int iteration)
    {
        String myPic=this.getStringFromPicId(false);
        MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) ((posY-1) * h + orgY+5*iteration), myPic);

        if(chocolate){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) ((posY-1) * h + orgY+5*iteration), "candy.png");
        }
        else if(frozen){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) ((posY-1) * h + orgY+5*iteration), getBonusString());
        }


        if(iteration==13){
            this.picstr=this.getStringFromPicId(false);
            this.picId=this.nextpicId;
            hide=false;
        }
    }
    public void hideGem()
    {
        this.picstr = "blank.png";
    }
    public void showGem()
    {
        this.picstr=this.getStringFromPicId(true);
    }

}
