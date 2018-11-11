/*
 * Project Name: EE2311 Project - Gems Crush
 * Student Name:
 * Student ID:
 * 
 */
package example;

import static example.Board.checkPreview;
import static example.Board.toDrop;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.Iterator;


public class Main{

    private MyConsole console = MyConsole.getInstance();
    public static Gem[] table = new Gem[64];
    public static boolean shouldPreview=false;
    public static void main(String[] args) {
        // a more OO approach to write the main method
        Main game = new Main();
        game.startGame();
    }
    
    
    public void startGame() {

        // board dimension can be obtained from console
        // set custom background image
        //begin timer
        Timer timer = new Timer();
        timer.start();
        //fill the board with random gems
        //play background music
        Board.initBoard(table);        
        // enter the main game loop
        int selectedId = -1;
        boolean firstTime=true;
        boolean shouldSwitch=false;
        int preview=0;
        int switchA=-1,switchB=-1,iter=0,iter2=0;
        boolean switchBack=false;
        while (true) 
        {
            Point point = console.getClickedPoint();
            if(shouldPreview)//response to previewbutton
            {
                shouldPreview=false;
                if(checkPreview(table))
                {
                    preview=1;
                    table[Board.previewA].switchGem(table[Board.previewB]);
                    switchA=Board.previewA;
                    switchB=Board.previewB;
                    shouldSwitch=true;
                    iter=0;
                }
                else  Board.initBoard(table);
            }
            else if(point!=null)//select gems
            {
                for (int i = 0; i < 64; i++) 
                {
                    if (table[i].isAt(point)) 
                    {
                        table[i].toggleFocus();                        
                        if (selectedId != -1)//selected
                        {
                            shouldSwitch=table[i].switchGem(table[selectedId]);
                            if(shouldSwitch)
                            {
                                switchA=selectedId;
                                switchB=i;
                                iter=0;
                                switchBack=false;
                            }
                            table[i].toggleFocus();
                            table[selectedId].toggleFocus();
                            selectedId=-1;
                        }                        
                        else if (table[i].isSelected()) 
                            selectedId = i;
                    }
                }
            }
            if(firstTime)
            {
                Board.checkChain(table);
                Board.clearScore();
                firstTime=false;
            }
            if(iter==13)//end switch animation
            {
                if(preview<2)
                {
                    table[Board.previewB].switchGem(table[Board.previewA]);
                    switchA=Board.previewB;
                    switchB=Board.previewA;
                    iter=0;
                    preview=2;
                }
                else if(preview>2&&iter2==0&&Board.checkChain(table))
                {                    
                    console.idle(100);                    
                }     
                else preview++;
            } 
            if(iter2==13)//end one elimination
            {
                iter2=0;
                Board.toDrop.clear();
                if(Board.checkChain(table))
                {//check again for cascade                     
                    console.idle(100);                   
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
                
                if(shouldSwitch&&iter++<13)//switch iteration
                {                        
                    table[switchA].switchAnimation(table[switchB],iter);                    
                    if(preview>2&&iter==13&&Board.checkChain(table)==false&&switchBack==false)
                    {
                        shouldSwitch=table[switchA].switchGem(table[switchB]);
                        if(shouldSwitch&&switchBack==false)
                        {                                
                            iter=0;
                            switchBack=true;
                        }                            
                        selectedId=-1;
                    }
                }
                else if(iter2++<13)//drop iteration
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
            console.idle(24);
        }
    }
}
