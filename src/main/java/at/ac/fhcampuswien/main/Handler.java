package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.entity.Player;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// CLASS CONTENT
/*
    KEY TYPED
    KEY PRESSED
    KEY RELEASED
 */

public class Handler implements KeyListener {
    GamePanel panel;
    Player player;

    public boolean UP, DOWN, LEFT, RIGHT, INTERACT;   //Move direction
    public boolean drawTime = false;
    public boolean Reset = false;

    // HANDLER CONSTRUCTOR
    public Handler(GamePanel panel){
        this.panel = panel;
    }

    /*public Handler(GamePanel panel, Player player){
        this.panel = panel;
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }*/

    // KEY TYPED
    @Override
    public void keyTyped(KeyEvent e) {}     //don't need it

    // KEY PRESSED
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // TITLE STATE
        if(panel.GameState == panel.titleState){
            if (keyCode == KeyEvent.VK_W){
                panel.ui.command--;
                if(panel.ui.command < 0) panel.ui.command = 2;
            }
            if (keyCode == KeyEvent.VK_S){
                panel.ui.command++;
                if(panel.ui.command > 2) panel.ui.command = 0;
            }
            if(keyCode == KeyEvent.VK_ENTER){
                if(panel.ui.command == 0){
                    panel.playMusic(1);
                    panel.GameState = panel.playState;
                }
                if(panel.ui.command == 1){
                    panel.GameState = panel.playState;
                    panel.playMusic(1);
                }
                if(panel.ui.command == 2){
                    System.exit(0);
                }
            }
        }

        // GAME WON STATE    |   GAME OVER STATE
        if(panel.GameState == panel.GameWonState || panel.GameState == panel.GameOverState){
            if (keyCode == KeyEvent.VK_W){
                panel.ui.command--;
                if(panel.ui.command < 0) panel.ui.command = 2;
            }
            if (keyCode == KeyEvent.VK_S){
                panel.ui.command++;
                if(panel.ui.command > 2) panel.ui.command = 0;
            }
            if(keyCode == KeyEvent.VK_ENTER){
                if(panel.ui.command == 0){
                    panel.GameState = panel.playState;
                    Reset = true;
                }
                if(panel.ui.command == 1){
                    panel.GameState = panel.titleState;
                    panel.stopMusic();
                }
                if(panel.ui.command == 2){
                    System.exit(0);
                }
            }

        }

        // PLAY STATE
        if(panel.GameState == panel.playState) {
            // MOVE
            if (keyCode == KeyEvent.VK_W) UP = true;      //(=0x57) (if "W" in Keyboard is Pressed -> go UP)
            if (keyCode == KeyEvent.VK_A) LEFT = true;    //(=0x41)
            if (keyCode == KeyEvent.VK_S) DOWN = true;    //(=0x53)
            if (keyCode == KeyEvent.VK_D) RIGHT = true;   //(=0x44)

            // INTERACTION
            if (keyCode == KeyEvent.VK_SPACE) INTERACT = true;    //Key for interaction (objects, ...)

            // PAUSE/PLAY
            if (keyCode == KeyEvent.VK_P) {
                panel.GameState = panel.pauseState;
                panel.stopMusic();
                drawTime = false;
            }
            // DEBUG
            //if (keyCode == KeyEvent.VK_T) drawTime = !drawTime;
        }

        // PAUSE STATE
        else if(panel.GameState == panel.pauseState){
            if (keyCode == KeyEvent.VK_P){
                panel.GameState = panel.playState;
                panel.playMusic(1);
            }

        }

        // DIALOGUE STATE
        else if(panel.GameState == panel.dialogueState){
            if(keyCode == KeyEvent.VK_SPACE)    panel.GameState = panel.playState;      //End Dialogue
            //if(keyCode == KeyEvent.VK_ENTER)    panel.GameState = panel.playState;    //Skip Dialogue
        }

    }

    // KEY RELEASED
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // MOVE
        if(keyCode == KeyEvent.VK_W)    UP = false;     //(=0x57) (if "W" in Keyboard is not Pressed -> stop UP)
        if(keyCode == KeyEvent.VK_A)    LEFT = false;   //(=0x41)
        if(keyCode == KeyEvent.VK_S)    DOWN = false;   //(=0x53)
        if(keyCode == KeyEvent.VK_D)    RIGHT = false;  //(=0x44)

        // INTERACTION
        if(keyCode == KeyEvent.VK_SPACE)    INTERACT = false; //Key for interaction (objects, ...)
    }
}
