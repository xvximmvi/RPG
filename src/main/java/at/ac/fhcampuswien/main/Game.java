package at.ac.fhcampuswien.main;

import javax.swing.JFrame;

//CLASS CONTENT
/*
    WINDOW SETTINGS
    ADD PANEL
    START GAME
 */

public class Game {
    public static void main(String[] args){

        //WINDOW SETTINGS
        JFrame window = new JFrame();       //first create window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //lets the window properly close when user clicks the close ("x") button
        window.setResizable(false);         //no resizing of the window
        window.setTitle("WAY OUT");      //Title of the Game

        // ADD PANEL
        GamePanel panel = new GamePanel();
        window.add(panel);                  //Add Panel Class to Window

        window.pack();   //causes window to be sized to fit preferred size and layouts of "Panel"

        window.setLocationRelativeTo(null); //Not specify the location of the window
        // => window will be displayed at the center of the screen
        window.setVisible(true);            //able to see the window

        // START GAME
        panel.setUpGame();                  //start setting up the Game
        panel.startThread();               //start thread
    }
}
