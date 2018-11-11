/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 *
 * @author Layla
 */
public class Status {
    static int score=0;
    static Gem[] table = new Gem[64];    
  
    public static void save(Gem[] x)
    {
        table=x;
        score=Board.getScore();
        try{
            PrintWriter writer = new PrintWriter("status.txt", "UTF-8");
            writer.println(score);
            for(int i=0;i<=63;i++)
                writer.println(table[i].picId);
            Timer.recordTime();
            writer.close();
        } 
        catch (IOException e) {}
    }
    public static Gem[] resume()
    {
        try{
            Scanner sc = new Scanner(new File("status.txt"));
            score=sc.nextInt();
            Gem rubbish=new Gem("/assets/blank.png",0,0,0);
            for(int i=0;i<=63;i++)
            {   
                if(sc.hasNext())
                {
                    rubbish.picId=sc.nextInt();
                    System.out.println(rubbish.picId);
                    System.out.println(i);
                    table[i].pic=rubbish.getPicFromId(true);
                    table[i].picId=rubbish.picId;
                }
            }
            sc.close();
        } 
        catch (IOException e) {}
        Board.setScore(score);
        Timer.restart();
        return table;
    }
}


