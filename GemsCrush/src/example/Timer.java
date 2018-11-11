/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

public class Timer {

    private static long initTime;
    private static int recordedTime;
    private static boolean isStop=false;
    public int time=0;

    public void start() {
        initTime = System.currentTimeMillis();
    }
    public static void stop()
    {
        recordedTime=300-(int)(System.currentTimeMillis() - initTime) / 1000;
        isStop=true;
    }
    
    public static void recordTime()
    {
        recordedTime=300-(int)(System.currentTimeMillis() - initTime) / 1000;
    }
    
    public static void restart()
    {
        isStop=false;
        initTime = System.currentTimeMillis()+1000*(recordedTime-300);
    }
    public String getTimeString() {        
        if(!isStop)
            time = 300-(int)(System.currentTimeMillis() - initTime) / 1000;
        else
            time=recordedTime;
        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 3600 % 60;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
