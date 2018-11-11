/*
 * Project Name: EE2311 Project - Gems Crush
 * Student Name:
 * Student ID:
 * 
 */
package example;

import static example.Board.toDrop;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.Iterator;

/**
 * Demo for the use of:
 *
 * 1. create and display game console 2. start a game loop 3. create and display
 * gems 4. detect mouse click and toggle gem's selection 5. update screen at
 * predefined interval 6. draw text to show score/time information
 *
 * @author Your Name and ID
 */
public class Demo1{

    // create the game console for drawing         
    // singleton, always return the same instance
    private MyConsole console = MyConsole.getInstance();   
    public static Gem[] table =new Gem[64];
    public static void main(String[] args) {
        // a more OO approach to write the main method
        Demo1 game = new Demo1();
        game.startGame();
    }   
    
    public void startGame() {

        // board dimension can be obtained from console
        // set custom background image
        console.setBackground("/assets/board.png");
        //begin timer
        Timer timer = new Timer();
        timer.start();
        //define the sound
        Sound select=new Sound("/assets/select.wav");
        Sound fall=new Sound("/assets/fall.wav");
        Sound match=new Sound("/assets/match.wav");
        Sound back=new Sound("/assets/background.wav");
        //fill the board with random gems
        Board.initBoard(table);
        back.playSound();
        
        // enter the main game loop        
        boolean firstTime=true;
        boolean shouldSwift=false;        
        boolean findOne=false;
        int motion=0;
        int swiftA=-1,swiftB=-1,iter=0,iter2=0;
        while (true) 
        {         
            if(firstTime)
            {
                if(!Board.checkChain(table))
                {             
                    Board.clearScore();
                    firstTime=false;
                }
            }
            else{
                Point point = console.getClickedPoint();
                if(motion==0){
                    for (int i = 0; i < 64&&motion==0; i++) 
                    {
                        if (point!=null&&table[i].isAt(point)) 
                        {
                            table[i].toggleFocus();
                            motion++;
                            swiftA=i;                       
                        }                    
                    }
                }
                else if (motion==1)
                {                    
                    Point mouse=console.getMouseMotion();                    
                    for (int i = 0; i < 64; i++) 
                    {                                               
                        if (table[i].isAt(mouse)&&table[swiftA].canSwift(table[i])&&findOne==false) 
                        {                            
                            shouldSwift=true;
                            findOne=true;
                            iter=0;
                            swiftB=i;
                            table[swiftA].swiftGem(table[swiftB]);                            
                        }                    
                    } 
                    if(point!=null)
                    {
                        for (int i = 0; i < 64; i++) 
                        {
                            if (table[i].isAt(point)) 
                            {
                                table[i].toggleFocus();       
                            }                    
                        }
                        motion=2;
                        iter2=0;
                    }
                }               
                else if(motion==2)
                {
                    if(iter2==0&&Board.checkChain(table))
                    {
                        match.playSound();                        
                        console.idle(100);
                        fall.playSound();
                    }                       
                    else if(iter2==13)//end one elimination
                    {
                        iter2=0;
                        Board.toDrop.clear();
                        if(Board.checkChain(table)){
                             //check again for cascade
                            match.playSound();
                            console.idle(100);
                            fall.playSound();
                        } 
                        else motion=0;
                    }
                }
            }
            // refresh at the specific rate, default 25 fps
            if (console.shouldUpdate()) 
            {
                console.clear();

                console.drawText(60, 150, "[TIME]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                console.drawText(60, 180, timer.getTimeString(), new Font("Helvetica", Font.PLAIN, 20), Color.white);
                
                if(timer.time<0){
                     console.close();                     
                }                   

                console.drawText(60, 250, "[SCORE]", new Font("Helvetica", Font.BOLD, 20), Color.white);
                console.drawText(60, 280, ""+Board.getScore(), new Font("Helvetica", Font.PLAIN, 20), Color.white);
                
                
                for (int i = 0; i < 64; i++) 
                    table[i].display();
                
                if(shouldSwift&&iter++<13)
                {                        
                    table[swiftA].swiftAnimation(table[swiftB],iter);                    
                    if(iter==13){
                        table[swiftA].hide=false;
                        table[swiftB].hide=false;
                        table[swiftA].showGem();
                        table[swiftB].showGem();
                        swiftA=swiftB;
                        shouldSwift=false;
                        findOne=false;
                        select.playSound();
                    }
                }
                else if(iter2++<13)
                {                    
                    for (Iterator it=toDrop.iterator();it.hasNext();) {
                        int value= (Integer)it.next();
                        table[value].dropAnimation(iter2);  
                    }
                }                
                console.update();
            }
            // the idle time affects the no. of iterations per second which 
            // should be larger than the frame rate
            // for fps at 25, it should not exceed 40ms
            console.idle(15);
        }
    }
}
