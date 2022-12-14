package at.ac.fhcampuswien.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Handler implements KeyListener {
    public boolean UP, DOWN, LEFT, RIGHT;   //Move direction

    //implemented methods
    @Override
    public void keyTyped(KeyEvent e) {}     //don't need it

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_W) {   //(=0x57)
            UP = true;      //if "W" in Keyboard is Pressed -> go UP
        }
        if(keyCode == KeyEvent.VK_A) {   //(=0x41)
            LEFT = true;
        }
        if(keyCode == KeyEvent.VK_S) {   //(=0x53)
            DOWN = true;
        }
        if(keyCode == KeyEvent.VK_D) {   //(=0x44)
            RIGHT = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_W) {   //(=0x57)
            UP = false;      //if "W" in Keyboard is not Pressed -> stop UP
        }
        if(keyCode == KeyEvent.VK_A) {   //(=0x41)
            LEFT = false;
        }
        if(keyCode == KeyEvent.VK_S) {   //(=0x53)
            DOWN = false;
        }
        if(keyCode == KeyEvent.VK_D) {   //(=0x44)
            RIGHT = false;
        }
    }
}
