package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.object.GameObject;
import at.ac.fhcampuswien.sound.Sound;
import at.ac.fhcampuswien.sound.SoundEffect;
import at.ac.fhcampuswien.entity.Player;
import at.ac.fhcampuswien.tile.Manager;

import javax.swing.*;
import java.awt.*;

// CLASS CONTENT
/*
    SETTINGS
        - SCREEN SETTINGS
        - MAP SETTINGS
        - FPS
        - SYSTEM
        - ENTITY AND OBJECT

    GAME PANEL CONSTRUCTOR
        - Set size and color of class
        - Listen Keyboard Input

    SETUP GAME
        - Play Theme Music

    THREAD
        - start Thread

    RUN
        - Fps
        - Thread
        - Update & Repaint

    UPDATE INFO
        - call player update
        - Game Over

    DRAW
        - Graphics2D
        - Tile
        - Object
        - Player
        - UI
        - Debug

    MUSIC AND SOUND EFFECTS
        - Play & Stop Music
        - Play & Stop Sound Effect
 */

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16;//16x16px tile (Standard size for retro 2D Games)
    //16x16 to small on modern screens (due to much higher resolution)
    final int scale = 3;            //multiply by 3
    //16*3 = 48 -> 48x48
    public int tileSize = originalTileSize*scale;    //48px
    public final int maxScreenCol = 16;             //tiles displayed horizontally
    public final int maxScreenRow = 12;             //tiles displayed vertically
    public final int ScreenWidth = tileSize*maxScreenCol;  //768px
    public final int ScreenHeight = tileSize*maxScreenRow; //576px

    // MAP SETTINGS
    public final int maxMap = 4;
    public int currentMap = 0;

    public int maxMapCol = 16;
    public int maxMapRow = 16;

    public int MapWidth = tileSize*maxMapCol;
    public int MapHeight = tileSize*maxMapRow;

    public int TransitionMap, TransitionX, TransitionY;

    // FPS
    int FPS = 60;

    // SYSTEM
    Handler handler = new Handler(this);    //add Handler
    Manager manager = new Manager(this);
    Sound sound = new Sound();
    SoundEffect soundEffect = new SoundEffect();
    public CollisionDetection collisionDetection = new CollisionDetection(this);    //public for Player
    public SetAsset asset = new SetAsset(this);
    public UserInterface ui = new UserInterface(this);
    Thread thread;      //implements Runnable (in public class)

    // ENTITY AND OBJECT
    public Player player = new Player(this, this.handler);//public for Manager
    //handler.setPlayer(player);
    public GameObject[][] object = new GameObject[maxMap][30];

    // GAME STATE
    public int GameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int GameWonState = 4;
    public final int GameOverState = 5;
    public final int characterState = 6;
    public final int transitionState = 7;
    public final int transitionOutState = 8;


    // GAME PANEL CONSTRUCTOR
    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));    //set the size of the class (JPanel)
        this.setBackground(Color.black);                                    //Background color
        this.setDoubleBuffered(true);   //If set to true, all the drawing from this component will be done in an offscreen painting buffer
        //enabling his can improve games rendering performance

        this.addKeyListener(handler);   //Panel can recognise Keyboard Input
        this.setFocusable(true);
    }

    // SETUP GAME
    public void setUpGame(){
        asset.setObjectBedroom();
        //playMusic(1);       //Start Theme Music
        GameState = titleState;
    }

    // THREAD
    public void startThread(){          //Thread
        thread = new Thread(this);
        thread.start();
    }

    // RUN
    @Override
    public void run() {
        //when thread started, it automatically calls run()
        //GAME LOOP (core of game)

        // FPS
        double timeInterval = 1000000000/FPS;   //1000000000n Nanoseconds = 1 Second -> 1/FPS= 0.01666 sec
        double nextTime = System.nanoTime()+timeInterval;   //next time is 0.01666 seconds later

        // THREAD
        while(thread != null){  //as long as thread exists: Repeat!
            //System.out.println("Running...");     //testing loop

            // UPDATE & REPAINT
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

    // UPDATE INFO
    public void updateInfo(){
        if(GameState == playState){
            player.update();        //update Player position
            if(ui.playTime <= 0){   //if Play Time is up
                GameState = GameOverState;
                playSoundEffect(1);
            }

        }
    }

    // DRAW
    public void paintComponent(Graphics graphics){
        //class that has many functions to draw objects on screen
        super.paintComponent(graphics);     //"paintComponent" building method from Java (not a name)

        //"Graphics2D" provides more function for graphic (color, geometry,...)
        Graphics2D graphics2d = (Graphics2D)graphics;

        // DEBUG
        //long drawStart = 0;
        //if(handler.drawTime)
            //drawStart = System.nanoTime();  //to check the time


        // TITLE SCREEN
        if(GameState == titleState){
            ui.draw(graphics2d);
        }

        // OTHER
        else{
            // TILE
            manager.draw(graphics2d);   //draw manager tiles

            // OBJECT
            for(int i = 0; i < object[1].length; i++){
                if(object[currentMap][i] != null){
                    object[currentMap][i].draw(graphics2d, this);
                }
            }

            // PLAYER
            player.draw(graphics2d);    //draw player character

            // UI
            ui.draw(graphics2d);
        }

        // DEBUG
            /*long drawEnd = System.nanoTime();
            long passedTime = drawEnd - drawStart;
            graphics2d.setColor(Color.white);
            graphics2d.drawString("Draw Time: " + passedTime, tileSize*10, 30);
            System.out.println("Draw Time:" + passedTime);*/

        graphics2d.dispose();   // dispose graphics
    }

    // MUSIC AND SOUND EFFECT
    public void playMusic(int i){
        sound.setFile(i);   //choose file
        sound.play();       //play file
        sound.loop();       //continue/repeat in loop
    }
    public void stopMusic(){
        sound.stop();   //stop current music
    }
    public void playSoundEffect(int i){
        soundEffect.play(i);       //only play once (no loop)
    }
}
