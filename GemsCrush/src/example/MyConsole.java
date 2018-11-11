/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Layla
 */
public class MyConsole extends JFrame{
    private static MyConsole myconsole= new MyConsole();
    final int DEFAULT_FRAME_WIDTH = 806;
    final int DEFAULT_FRAME_HEIGHT = 629;
    static List textToDraw=new ArrayList();
    static List imgToDraw=new ArrayList();
    public MyConsole()
    {
        setTitle("GemCrush");
        setPreferredSize(new Dimension(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT));
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setContentPane(MyBoard.getInstance());
        pack();
    }
    public static MyConsole getInstance()
    {
        return myconsole;
    }
    public Point getClickedPoint()
    {
        Point ClickedPoint=MyBoard.getInstance().getClickedPoint();
        if(ClickedPoint!=null)
        {
            Point temp=ClickedPoint;
            MyBoard.getInstance().clearClickedPoint();
            return temp;
        }
        else return null;
    }
    public Point getMouseMotion()
    {
        Point MousePosition=MyBoard.getInstance().getMousePosition();
        if(MousePosition!=null)
        {
            Point temp=MousePosition;
            return temp;
        }
        else return null;
    }
    public void clear()
    {
        MyBoard.getInstance().revalidate();
    }
    public void drawText(final int x,final int y,final String s,Font f,Color c)
    {
        textToDraw.add(new MyTextPair(s,f,c,x,y));
    }
    public void drawImage(final int x,final int y,final Image image)
    {
        imgToDraw.add(new MyImgPair(image,x,y));
    }
    public void close()
    {
        setVisible(false);
        dispose(); 
    }
    public boolean shouldUpdate()
    {
        return true;
    }
    public void update()
    {
        repaint();
    }
    public void idle(int x)
    {
        try
        {
            Thread.sleep(x);
        }
        catch(Exception y)
        {}
    }
}
class MyBoard extends JPanel{
    private static MyBoard myboard= new MyBoard();
    private Point ClickedPoint=null;
    private Point MousePosition=null;
    private Image background;
    public static JButton save;
    public static JButton resume;
    public static JButton preview;
    public MyBoard() {
        setFocusable(true);
        setDoubleBuffered(true);   
        setBackground(Color.red);
        setLayout(null);
        save=new JButton("Save");
        save.setBounds(60, 350, 90, 30);
        save.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                Status.save(Main.table);
            }
        });
        add(save);
        resume=new JButton("Resume");
        resume.setBounds(60, 395, 90, 30);
        resume.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                Status.resume();
            }
        });
        add(resume);
        preview=new JButton("Preview");
        preview.setBounds(60, 440, 90, 30);
        preview.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                Main.shouldPreview=true;
            }
        });
        add(preview);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
               MousePosition=new Point((int)e.getX(),(int)e.getY());
           }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                ClickedPoint=new Point((int)e.getX(),(int)e.getY());
           }
        });
        try{
            background = ImageIO.read(getClass().getResource("/assets/board.png"));
        }
        catch(IOException e){}
    }
    public static MyBoard getInstance()
    {
        return myboard;
    }
    public Point getClickedPoint()
    {
        return ClickedPoint;
    }
    
    public Point getMousePosition()
    {
        return MousePosition;
    }
    public void clearClickedPoint()
    {
        ClickedPoint=null;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        for (int i=0; i<MyConsole.textToDraw.size(); i++) {
            MyTextPair value = (MyTextPair)MyConsole.textToDraw.get(i); 
            g.setFont(value.getFont());
            g.setColor(value.getColor());
            g.drawString(value.getStr(),value.getX(),value.getY());
        }
        for (int i=0; i<MyConsole.imgToDraw.size(); i++) {
            MyImgPair value = (MyImgPair)MyConsole.imgToDraw.get(i);    
            g.drawImage(value.getImg(),value.getX(),value.getY(),this);
        }
        MyConsole.textToDraw.clear();
        MyConsole.imgToDraw.clear();
    }
}
class MyTextPair
{
    private String s;
    private Font f;
    private Color c;
    private int x,y;
    MyTextPair(String str, Font font,Color color,int x1, int y1)
    {
        s=str;x=x1;y=y1;f=font;c=color;
    }
    public String getStr(){return s;}
    public Font getFont(){return f;}
    public Color getColor(){return c;}
    public int getX(){return x;}
    public int getY(){return y;}
}
class MyImgPair
{
    private Image img;
    private int x,y;
    MyImgPair(Image i, int x1, int y1)
    {
        img=i;x=x1;y=y1;
    }
    public Image getImg(){return img;}
    public int getX(){return x;}
    public int getY(){return y;}
}