package at.ac.fhcampuswien.main;

import javax.swing.JFrame;

public class Game {
    public static void main(String[] args){
        JFrame window = new JFrame();       //first create window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //lets the window properly close when user clicks the close ("x") button
        window.setResizable(false);         //no resizing of the window
        window.setTitle("GAME TITLE");      //Title of the Game

        MyPanel myPanel = new MyPanel();
        window.add(myPanel);                  //Add Panel Class to Window

        window.pack();   //causes window to be sized to fit preferred size and layouts of "Panel"

        window.setLocationRelativeTo(null); //Not specify the location of the window
        // => window will be displayed at the center of the screen
        window.setVisible(true);            //able to see the window

        myPanel.startThread();               //start thread
    }
}
