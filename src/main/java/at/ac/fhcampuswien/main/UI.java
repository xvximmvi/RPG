package at.ac.fhcampuswien.main;

import java.awt.*;
import java.text.DecimalFormat;

// CLASS CONTENT
/*
    UI CONSTRUCTOR
        - Define Fonts

    SHOW MESSAGE
        - Text

    PRINT
        - GAME WON
            > Congratulation!
            > Door unlocked!
        - GAME OVER
            > Game Over!
        - ONGOING GAME
            > Remaining Tile
            > Notification Message
 */

public class UI {

    GamePanel panel;
    Font courier_new_20;
    Font courier_new_40;

    public boolean MessageOn = false;
    public String Message = "";
    int MessageCounter = 0;

    public boolean TutorialOn = false;
    public boolean Goal = false;
    int TutorialCounter = 0;

    public boolean GameOver = false;
    public boolean GameWon = false;

    public double playTime=30;      //30 sec Countdown
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");  //minimize decimals


    // UI CONSTRUCTOR
    public UI(GamePanel panel){
        this.panel = panel;

        courier_new_20 = new Font("Courier New", Font.BOLD, 20);    // define Fonts
        courier_new_40 = new Font("Courier New", Font.BOLD, 40);    // define Fonts
    }

    //SHOW MESSAGE
    public void ShowMessage(String Text){
        Message = Text;
        MessageOn = true;
    }

    // PRINT
    public void draw(Graphics2D graphics2D){

        //GAME WON
        if(GameWon){

            String Text;
            int TextLength, x, y;

            // CONGRATULATIONS
            Text = "CONGRATULATIONS!";

            graphics2D.setFont(courier_new_40);
            graphics2D.setColor(Color.white);

            //Gets the length of the text
            TextLength = (int)graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();

            x = panel.ScreenWidth/2 - TextLength/2;     //Center of X-Coordinate
            y = panel.ScreenHeight/2 - (panel.tileSize*2);

            graphics2D.drawString(Text, x, y);


            // DOOR UNLOCKED
            Text = "Door unlocked!";

            graphics2D.setFont(courier_new_20);
            graphics2D.setColor(Color.white);

            //Gets the length of the text
            TextLength = (int)graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();

            x  = panel.ScreenWidth/2 - TextLength/2;
            y = panel.ScreenHeight/2 + (panel.tileSize*2);

            graphics2D.drawString(Text, x, y);


            // STOP GAME
            panel.playSoundEffect(2);
            panel.thread = null;
        }

        // GAME OVER
        else if(GameOver){
            String Text;
            int TextLength, x, y;

            // GAME OVER
            Text = "GAME OVER!";

            graphics2D.setFont(courier_new_40);
            graphics2D.setColor(Color.white);

            //Gets the length of the text
            TextLength = (int)graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();

            x = panel.ScreenWidth/2 - TextLength/2;
            y = panel.ScreenHeight/2;

            graphics2D.drawString(Text, x, y);

            // STOP GAME
            panel.playSoundEffect(3);
            panel.thread = null;
        }

        // ONGOING GAME
        else{
            graphics2D.setFont(courier_new_20);
            graphics2D.setColor(Color.white);

            // TIME
            playTime -=(double)1/60;    //add 1/60 to the loop (60 Frames per sec)
            graphics2D.drawString("Time: "+ decimalFormat.format(playTime), panel.tileSize*12+25, 60);

            // MESSAGE
            if(MessageOn){
                graphics2D.drawString(Message, panel.tileSize, panel.tileSize+60);

                MessageCounter++;
                if(MessageCounter > 120){   //120 Frames (60 Frames per sec -> 2 sec)
                    MessageCounter = 0;
                    MessageOn = false;
                }
            }

            // TUTORIAL
            if(TutorialOn) {
                graphics2D.drawString("Move: WASD", panel.tileSize, panel.tileSize);
                graphics2D.drawString("Interact: SPACE", panel.tileSize, panel.tileSize + 30);

                TutorialCounter++;
                if (TutorialCounter > 240) {   //240 Frames (60 Frames per sec -> 4 sec)
                    TutorialCounter = 0;
                    TutorialOn = false;
                    Goal = true;
                }
            } else if(Goal) {
                graphics2D.drawString("Find the KEY!", panel.tileSize, panel.tileSize);
                graphics2D.drawString("Escape the Room!", panel.tileSize, panel.tileSize + 30);

                TutorialCounter++;
                if (TutorialCounter > 240) {   //240 Frames (60 Frames per sec -> 4 sec)
                    TutorialCounter = 0;
                    Goal = false;
                }
            }
        }
    }
}
