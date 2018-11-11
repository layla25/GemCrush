/*
 * Project Name: EE2311 Project - Gems Crush
 * Student Name:
 * Student ID:
 * 
 */
package example;

import game.GameConsole;
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

    Image pic;
    int picId;
    private int nextpicId;
    private Image focus;
    private Image blank;    
    private Image frozen_pic;
    private Image chocolate_pic;
    
    Gem(String file, int x, int y,int picId) {
        this.focus = new ImageIcon(this.getClass().getResource("/assets/focus.png")).getImage();
        this.blank=new ImageIcon(this.getClass().getResource("/assets/blank.png")).getImage();        
        this.frozen_pic = new ImageIcon(this.getClass().getResource("/assets/spider-web.png")).getImage();
        this.chocolate_pic = new ImageIcon(this.getClass().getResource("/assets/candy.png")).getImage();
        this.pic = new ImageIcon(this.getClass().getResource(file)).getImage();
        this.posX = x;
        this.posY = y;
        this.picId=picId;
    }

    public void display() {
        MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) (posY * h + orgY), pic);        
        if (this.selected) {
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) (posY * h + orgY), focus);
        }        
        if (this.frozen&&hide==false){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) (posY * h + orgY), frozen_pic);
        }
        else if (this.chocolate&&hide==false){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) (posY * h + orgY), chocolate_pic);
        }
    }
    
    public boolean isAt(Point point) {
        if (point != null) {
            return (point.x > (posX * w + orgX) && point.x <= ((posX + 1) * w + orgX) && point.y > (posY * h + orgY) && point.y <= ((posY + 1) * h + orgY));
        } else {
            return false;
        }
    }

    public Image getPic() {
        return pic;
    } 
    public void setPic(String file) {
        this.pic = new ImageIcon(this.getClass().getResource(file)).getImage();
    }
    
    public void setPic(Image file){
        this.pic=file;
    }

    public int getPicId() {
        return picId;
    }
    public void setNextPicId(int x)
    {
        this.nextpicId=x;
    }
    public Image getPicFromId(boolean notnext){
        int temp;
        if(notnext)
           temp=picId;
        else temp=nextpicId;
        switch(temp){     
            case 0:
                return new ImageIcon(this.getClass().getResource("/assets/bear.png")).getImage();
            case 1:
                return new ImageIcon(this.getClass().getResource("/assets/dolphin.png")).getImage();
            case 2:
                return new ImageIcon(this.getClass().getResource("/assets/bird.png")).getImage();
            case 3:
                return new ImageIcon(this.getClass().getResource("/assets/snail.png")).getImage();
            case 4:
                return new ImageIcon(this.getClass().getResource("/assets/dog.png")).getImage();
            case 5:
                return new ImageIcon(this.getClass().getResource("/assets/butterfly.png")).getImage();
            case 6:
                return new ImageIcon(this.getClass().getResource("/assets/frog.png")).getImage();         
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
            
            this.pic = new ImageIcon(this.getClass().getResource("/assets/blank.png")).getImage();
            other.pic=new ImageIcon(this.getClass().getResource("/assets/blank.png")).getImage();
            return true;
        }   
        return false;
    }
    
    public void swiftAnimation(Gem other, int iteration) {    	
    	int xDirection=other.posX-this.posX;
    	int yDirection=other.posY-this.posY;
    	MyConsole.getInstance().drawImage((int) (posX * w + orgX+5*iteration*xDirection), (int) (posY * h + orgY+5*iteration*yDirection), other.getPicFromId(true));
    	MyConsole.getInstance().drawImage((int) (other.posX * w + orgX-5*iteration*xDirection), (int) (other.posY * h + orgY-5*iteration*yDirection), getPicFromId(true)); 
        if(this.chocolate==true&&other.chocolate!=true)
            MyConsole.getInstance().drawImage((int) (other.posX * w + orgX-5*iteration*xDirection), (int) (other.posY * h + orgY-5*iteration*yDirection), chocolate_pic);
        else if(this.chocolate!=true&&other.chocolate==true)
            MyConsole.getInstance().drawImage((int) (posX * w + orgX+5*iteration*xDirection), (int) (posY * h + orgY+5*iteration*yDirection), chocolate_pic);            
        else if(this.chocolate==true&&other.chocolate==true){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX+5*iteration*xDirection), (int) (posY * h + orgY+5*iteration*yDirection), chocolate_pic);
    	    MyConsole.getInstance().drawImage((int) (other.posX * w + orgX-5*iteration*xDirection), (int) (other.posY * h + orgY-5*iteration*yDirection), chocolate_pic);
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
    	Image myPic=this.getPicFromId(false);
        MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) ((posY-1) * h + orgY+5*iteration), myPic);        
        
        if(chocolate){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) ((posY-1) * h + orgY+5*iteration), chocolate_pic);
        }
        else if(frozen){
            MyConsole.getInstance().drawImage((int) (posX * w + orgX), (int) ((posY-1) * h + orgY+5*iteration), frozen_pic);
        }
        
        
        if(iteration==13){
           this.pic=myPic;          
           this.picId=this.nextpicId;
           hide=false;
       }
    }
    public void hideGem()
    {
        this.pic = blank;
    }      
    public void showGem()
    {
        this.pic=this.getPicFromId(true);
    }  
    
}
