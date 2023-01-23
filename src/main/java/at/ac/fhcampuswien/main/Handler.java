package at.ac.fhcampuswien.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// CLASS CONTENT
/*
    KEY TYPED

    KEY PRESSED
        - TITLE STATE
            > W / S
            > ENTER
        - GAME WON / OVER STATE
            > W / S
            > ENTER
        - PLAY STATE
            > W A S D
            > SPACE
            > P
            > C
            > O
        - PAUSE STATE
            > P
        - DIALOGUE STATE
            > SPACE
        - CHARACTER STATE
            > C
        - OPTION STATE
            > O
            > W A S D
            > ENTER
        - MENU OPTION STATE
            > W A S D
            > ENTER
        - HOW TO PLAY STATE
            > ESCAPE
        - CREDITS STATE
            > ESCAPE
            > W / S

    KEY RELEASED
       -  PLAY STATE
            > W A S D
            > SPACE
 */

public class Handler implements KeyListener {
    GamePanel panel;

    public boolean UP, DOWN, LEFT, RIGHT, INTERACT;   //Move direction
    public boolean drawTime = false;
    public boolean Reset = false;
    public boolean Enter = false;
    public int y = 0;

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

        // TITLE STATE
        if(panel.GameState == panel.titleState){
            if (keyCode == KeyEvent.VK_W) {
                panel.ui.command--;
                panel.playSoundEffect(6);
                if (panel.ui.command < 0) panel.ui.command = 4;
            }
            if (keyCode == KeyEvent.VK_S) {
                panel.ui.command++;
                panel.playSoundEffect(6);
                if (panel.ui.command > 4) panel.ui.command = 0;
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                panel.playSoundEffect(5);
                Enter = false;

                if (panel.ui.command == 0) {
                    panel.stopMusic();
                    panel.playMusic(1);
                    panel.GameState = panel.playState;
                    Reset = true;
                    panel.ui.command=0;
                }
                if (panel.ui.command == 1) {
                    panel.playSoundEffect(5);
                    panel.GameState = panel.menuOptionState;
                    panel.ui.command=0;
                }
                if (panel.ui.command == 2) {
                    panel.playSoundEffect(5);
                    panel.GameState = panel.howToPlayState;
                    panel.ui.command=0;
                }
                if (panel.ui.command == 3) {
                    panel.playSoundEffect(5);
                    panel.GameState = panel.creditsState;
                    panel.ui.command=0;
                }

                if (panel.ui.command == 4) {
                    panel.playSoundEffect(5);
                    System.exit(0);
                }
            }
        }

        // GAME WON STATE    |   GAME OVER STATE
        if(panel.GameState == panel.GameWonState || panel.GameState == panel.GameOverState){
            panel.playSoundEffect(6);

            if (keyCode == KeyEvent.VK_W){
                panel.ui.command--;
                if(panel.ui.command < 0) panel.ui.command = 2;
            }
            if (keyCode == KeyEvent.VK_S){
                panel.ui.command++;
                if(panel.ui.command > 2) panel.ui.command = 0;
            }
            if(keyCode == KeyEvent.VK_ENTER){
                panel.playSoundEffect(5);

                if(panel.ui.command == 0){
                    panel.GameState = panel.playState;
                    Reset = true;
                }
                if(panel.ui.command == 1){
                    panel.GameState = panel.titleState;
                    panel.stopMusic();
                    panel.playMusic(2);
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
                panel.playSoundEffect(5);
                panel.playMusic(2);
                drawTime = false;
            }
            // DEBUG
            //if (keyCode == KeyEvent.VK_T) drawTime = !drawTime;

            // CHARACTER STATE
            if(keyCode == KeyEvent.VK_C){
                panel.playSoundEffect(5);
                panel.GameState = panel.characterState;
            }

            // OPTION STATE
            if(keyCode == KeyEvent.VK_O){
                panel.playSoundEffect(5);
                Enter = false;
                panel.ui.command = 0;
                panel.ui.optionState = 0;
                panel.GameState = panel.optionState;
            }


        }

        // PAUSE STATE
        else if(panel.GameState == panel.pauseState){
            if (keyCode == KeyEvent.VK_P){
                panel.playSoundEffect(5);
                panel.GameState = panel.playState;
                panel.stopMusic();
                panel.playMusic(1);
            }

        }

        // DIALOGUE STATE
        else if(panel.GameState == panel.dialogueState){
            if(keyCode == KeyEvent.VK_SPACE){
                panel.GameState = panel.playState;      //End Dialogue
            }
            //if(keyCode == KeyEvent.VK_ENTER)    panel.GameState = panel.playState;    //Skip Dialogue

        }

        // CHARACTER STATE
        else if(panel.GameState == panel.characterState){
            if(keyCode == KeyEvent.VK_C) {
                panel.playSoundEffect(5);
                panel.GameState = panel.playState;
            }
        }

        // OPTION STATE
        else if(panel.GameState == panel.optionState){
            if(keyCode == KeyEvent.VK_O) {
                panel.playSoundEffect(5);
                panel.GameState = panel.playState;
                panel.ui.command = 0;
            }

            if(keyCode == KeyEvent.VK_ENTER) {
                Enter = true;
                panel.playSoundEffect(5);
            }

            int maxCommand = 0;
            if (panel.ui.optionState == 0) {
                maxCommand = 7;
            } else if (panel.ui.optionState == 2 || panel.ui.optionState == 3 || panel.ui.optionState == 4) {
                maxCommand = 1;
            }

            if(keyCode == KeyEvent.VK_W) {
                panel.ui.command--;
                panel.playSoundEffect(6);
                if(panel.ui.command < 0) panel.ui.command = maxCommand;
            }
            if(keyCode == KeyEvent.VK_S) {
                panel.ui.command++;
                panel.playSoundEffect(6);
                if(panel.ui.command > maxCommand) panel.ui.command = 0;
            }

            if(keyCode == KeyEvent.VK_A) {
                if(panel.ui.optionState == 0){
                    if(panel.ui.command == 1 && panel.sound.volumeScale > 0){
                        panel.sound.volumeScale--;
                        panel.sound.setVolume();     //need to change ongoing music. Call setVolume while changing
                        panel.playSoundEffect(6);
                    }
                    if(panel.ui.command == 2 && panel.sound.volumeScale > 0){
                        panel.soundEffect.volumeScale--;
                        panel.playSoundEffect(6);
                    }
                }
            }
            if(keyCode == KeyEvent.VK_D) {
                if(panel.ui.optionState == 0){
                    if(panel.ui.command == 1 && panel.soundEffect.volumeScale < 5){
                        panel.sound.volumeScale++;
                        panel.sound.setVolume();     //need to change ongoing music. Call setVolume while changing
                        panel.playSoundEffect(6);
                    }
                    if(panel.ui.command == 2 && panel.soundEffect.volumeScale < 5){
                        panel.soundEffect.volumeScale++;
                        panel.playSoundEffect(6);
                    }
                }
            }
        }

        // MENU OPTION STATE
        else if(panel.GameState == panel.menuOptionState){

            if(keyCode == KeyEvent.VK_ENTER) {
                Enter = true;
                panel.playSoundEffect(5);
            }

            if(keyCode == KeyEvent.VK_W) {
                Enter = false;
                panel.ui.command--;
                panel.playSoundEffect(6);
                if(panel.ui.command < 0) panel.ui.command = 3;
            }
            if(keyCode == KeyEvent.VK_S) {
                Enter = false;
                panel.ui.command++;
                panel.playSoundEffect(6);
                if(panel.ui.command > 3) panel.ui.command = 0;
            }

            if(keyCode == KeyEvent.VK_A) {
                    if(panel.ui.command == 0 && panel.sound.volumeScale > 0){
                        panel.sound.volumeScale--;
                        panel.sound.setVolume();
                        panel.playSoundEffect(6);
                    }
                    if(panel.ui.command == 1 && panel.sound.volumeScale > 0){
                        panel.soundEffect.volumeScale--;
                        panel.playSoundEffect(6);
                    }
            }
            if(keyCode == KeyEvent.VK_D) {
                    if(panel.ui.command == 0 && panel.soundEffect.volumeScale < 5){
                        panel.sound.volumeScale++;
                        panel.sound.setVolume();
                        panel.playSoundEffect(6);
                    }
                    if(panel.ui.command == 1 && panel.soundEffect.volumeScale < 5){
                        panel.soundEffect.volumeScale++;
                        panel.playSoundEffect(6);
                    }
            }
        }

        // HOW TO PLAY STATE
        else if(panel.GameState == panel.howToPlayState) {
            if(keyCode == KeyEvent.VK_ESCAPE) {
                panel.GameState = panel.titleState;
                panel.playSoundEffect(5);
            }
        }

        // CREDITS STATE
        else if(panel.GameState == panel.creditsState){
            if(keyCode == KeyEvent.VK_ESCAPE) {
                panel.GameState = panel.titleState;
                panel.playSoundEffect(5);
                panel.ui.command=3;
            }

            if(keyCode == KeyEvent.VK_W) {
                panel.playSoundEffect(5);
                if(y != 0)  y += 12;
            }

            if(keyCode == KeyEvent.VK_S) {
                panel.playSoundEffect(5);
                if(y >= -1212) y -= 12;
            }

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
