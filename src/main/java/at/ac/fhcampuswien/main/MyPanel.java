package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.entity.Player;
import at.ac.fhcampuswien.tile.Manager;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel implements Runnable{
    //Screen Settings:
    final int originalTileSize = 16;//16x16px tile (Standard size for retro 2D Games)
    //16x16 to small on modern screens (due to much higher resolution)
    final int scale = 3;            //multiply by 3
    //16*3 = 48 -> 48x48
    public int tileSize = originalTileSize*scale;    //48px
    public final int maxScreenCol = 16; //tiles displayed horizontally
    public final int maxScreenRow = 12; //tiles displayed vertically
    public final int ScreenWidth = tileSize*maxScreenCol;  //768px
    public final int ScreenHeight = tileSize*maxScreenRow; //576px

    //MAP SETTINGS
    public final int maxMapCol = 16;
    public final int maxMapRow = 16;
    public final int MapWidth = tileSize*maxMapCol;
    public final int MapHeight = tileSize*maxMapRow;

    Handler handler = new Handler();    //add Handler

    //Time in Game for Animation
    Thread thread;      //implements Runnable (in public class)

    public Player player = new Player(this, this.handler);  //public for Manager
    Manager manager = new Manager(this);

    int FPS = 60;           //FPS

    public MyPanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));    //set the size of the class (JPanel)
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);   //If set to true, all the drawing from this component will be done in an offscreen painting buffer
        //enabling his can improve games rendering performance

        this.addKeyListener(handler);   //Panel can recognise Keyboard Input
        this.setFocusable(true);
    }

    public void startThread(){          //Thread
        thread = new Thread(this);
        thread.start();
    }

    @Override               //when thread started, it automatically calls run()
    public void run() {     //GAME LOOP (core of game)

        double timeInterval = 1000000000/FPS;   //1000000000n Nanoseconds = 1 Second -> 1/FPS= 0.01666 sec
        double nextTime = System.nanoTime()+timeInterval;   //next time is 0.01666 seconds later

        while(thread != null){  //as long as thread exists: Repeat!
            //System.out.println("Running...");     //testing loop
            updateInfo();   //Update information (f.e. as character position)
            repaint();      //Repaint screen with new information

            //https://www.geeksforgeeks.org/thread-sleep-method-in-java-with-examples/
            try {   //Thread.sleep can't take long variables (optional solution from java)
                double remainingTime = nextTime - System.nanoTime();    //Updating game is too fast -> Sleep Methode
                remainingTime = remainingTime/1000000;      //nano sec -> milli sec

                if(remainingTime < 0){      //if updating took more Time than "timeInterval" than no Time is remains
                    remainingTime=0;        //no need for Thread to sleep
                }
                Thread.sleep((long) remainingTime); //let thread sleep through remainingTime
                nextTime += timeInterval;           //0.01666 sec later
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void updateInfo(){
        player.update();
    }
    public void paintComponent(Graphics graphics){    //class that has many functions to draw objects on screen
        super.paintComponent(graphics);     //"paintComponent" building method from Java (not a name)

        //"Graphics2D" provides more function for graphic (color, geometry,...)
        Graphics2D graphics2d = (Graphics2D)graphics;

        //Layer: first written is under the second
        manager.draw(graphics2d);   //draw manager tiles
        player.draw(graphics2d);    //draw player character
        graphics2d.dispose();
    }
}
/*
 public void draw(Graphics2D g2, GamePanel gp) {

  int ScreenX = MapX - panel.player.MapX + gp.player.ScreenX;
  int ScreenY = MapY - gp.player.MapY + gp.player.ScreenY;

  // STOP MOVING CAMERA
  if(panel.player.MapX < panel.player.ScreenX) {
   ScreenX = MapX;
  }
  if(panel.player.MapY < panel.player.ScreenY) {
   ScreenY = MapY;
  }
  int RightOffset = panel.ScreenWidth - panel.player.ScreenX;
  if(RightOffset > panel.MapWidth - panel.player.MapX) {
   ScreenX = panel.ScreenWidth - (panel.MapWidth - MapX);
  }
  int BottomOffset = panel.ScreenHeight - panel.player.ScreenY;
  if(BottomOffset > panel.MapHeight - panel.player.MapY) {
   ScreenY = panel.ScreenHeight - (panel.MapHeight - MapY);
  }

  if(MapX + panel.tileSize > panel.player.MapX - panel.player.ScreenX &&
     MapX - panel.tileSize < panel.player.MapX + panel.player.ScreenX &&
     MapY + panel.tileSize > panel.player.MapY - panel.player.ScreenY &&
     MapY - panel.tileSize < panel.player.MapY + panel.player.ScreenY) {

   g2.drawImage(image, ScreenX, ScreenY, panel.tileSize, panel.tileSize, null);
  }
  // If player is around the edge, draw everything
  else if(panel.player.MapX < panel.player.ScreenX ||
    panel.player.MapY < panel.player.ScreenY ||
    rightOffset > panel.MapWidth - panel.player.MapX ||
    bottomOffset > panel.MapHeight - panel.player.MapY) {
   g2.drawImage(image, ScreenX, ScreenY, panel.tileSize, panel.tileSize, null);
  }
 }*/
