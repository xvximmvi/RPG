package at.ac.fhcampuswien.main;

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

    public boolean UP, DOWN, LEFT, RIGHT, INTERACT;   //Move direction
    public boolean drawTime = false;

    // HANDLER CONSTRUCTOR
    public Handler(GamePanel panel){
        this.panel = panel;
    }

    // KEY TYPED
    @Override
    public void keyTyped(KeyEvent e) {}     //don't need it

    // KEY PRESSED
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // MOVE
        if(keyCode == KeyEvent.VK_W)    UP = true;      //(=0x57) (if "W" in Keyboard is Pressed -> go UP)
        if(keyCode == KeyEvent.VK_A)    LEFT = true;    //(=0x41)
        if(keyCode == KeyEvent.VK_S)    DOWN = true;    //(=0x53)
        if(keyCode == KeyEvent.VK_D)    RIGHT = true;   //(=0x44)

        // INTERACTION
        if(keyCode == KeyEvent.VK_SPACE)    INTERACT = true;    //Key for interaction (objects, ...)

        // DEBUG
        if(keyCode == KeyEvent.VK_T)        drawTime = !drawTime;

        // PAUSE/PLAY
        if(keyCode == KeyEvent.VK_P){
            if(panel.GameState == panel.playState){
                panel.GameState = panel.pauseState;
                drawTime = false;
            }

            else if(panel.GameState == panel.pauseState)
                panel.GameState = panel.playState;
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

        // DEBUG
    }
}
