package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.Object.OBJECT_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

// CLASS CONTENT
/*
    UI CONSTRUCTOR
        - Define Fonts

    SHOW MESSAGE
        - Text

    PRINT
     PLAY
        - GAME WON
            > Congratulation!
            > Door unlocked!
        - GAME OVER
            > Game Over!
        - ONGOING GAME
            > Remaining Tile
            > Notification Message
      PAUSE
        - PAUSE SCREEN
 */

public class UI {

    GamePanel panel;
    Handler handler;
    Graphics2D graphics2D;
    BufferedImage bufferedImage;
    Font Retro_Gaming;
    public int command = 0;

    public boolean GameOver = false;
    public boolean GameWon = false;

    public String currentDialogue = "";
    public boolean MessageOn = false;
    public String Message = "";
    int MessageCounter = 0;
    public boolean foundKey;
    int KeyCounter = 0;

    public boolean TutorialOn = false;
    public boolean Goal = false;
    int TutorialCounter = 0;


    public double playTime=30;      //30 sec Countdown
    public double DefaultPlayTime = playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");  //minimize decimals

    // UI CONSTRUCTOR
    public UI(GamePanel panel){
        this.panel = panel;

        InputStream is = getClass().getResourceAsStream("/Font/Retro Gaming.ttf");
        try {
            Retro_Gaming = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //SHOW MESSAGE
    public void ShowMessage(String Text){
        Message = Text;
        MessageOn = true;
    }

    // PRINT
    public void draw(Graphics2D graphics2D){

        this.graphics2D = graphics2D;
        graphics2D.setFont(Retro_Gaming);
        graphics2D.setColor(Color.white);

        // TITLE STATE
        if(panel.GameState == panel.titleState){
            TitleScreen();
        }

        // PLAY STATE
        if(panel.GameState == panel.playState) {

            graphics2D.setFont(Retro_Gaming);
            graphics2D.setColor(Color.white);

            // TIME
            playTime -= (double) 1 / 60;    //add 1/60 to the loop (60 Frames per sec)
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));
            // SHADOW
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString("Time: " + decimalFormat.format(playTime), panel.tileSize * 12 + 7+2, 60+2);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("Time: " + decimalFormat.format(playTime), panel.tileSize * 12 + 7, 60);


            // MESSAGE
            if (MessageOn) {
                graphics2D.drawString(Message, panel.tileSize, panel.tileSize + 90);

                MessageCounter++;
                if (MessageCounter > 120) {   //120 Frames (60 Frames per sec -> 2 sec)
                    MessageCounter = 0;
                    MessageOn = false;
                }
            }

            // KEY
            if (foundKey) {
                OBJECT_Key key = new OBJECT_Key(panel);
                bufferedImage = key.image;
                graphics2D.drawImage(bufferedImage, 13 * panel.tileSize, 90, 24 * 3, 13 * 3, null);

                    /*KeyCounter++;
                    if (KeyCounter > 200) {
                        KeyCounter = 0;
                        foundKey = false;
                    }*/
            }

            // TUTORIAL
            if (TutorialOn) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 17F));
                graphics2D.drawString("Move: WASD", panel.tileSize, panel.tileSize);
                graphics2D.drawString("Interact: SPACE", panel.tileSize, panel.tileSize + 30);
                graphics2D.drawString("Dialogue: ENTER", panel.tileSize, panel.tileSize + 60);
                graphics2D.drawString("Pause/Play: P", panel.tileSize, panel.tileSize + 90);

                TutorialCounter++;
                if (TutorialCounter > 180) {   //180 Frames (60 Frames per sec -> 3 sec)
                    TutorialCounter = 0;
                    TutorialOn = false;
                    Goal = true;
                }
            } else if (Goal) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 17F));
                graphics2D.drawString("Find the KEY!", panel.tileSize, panel.tileSize);
                graphics2D.drawString("Escape the Room!", panel.tileSize, panel.tileSize + 30);

                TutorialCounter++;
                if (TutorialCounter > 180) {   //180 Frames (60 Frames per sec -> 3 sec)
                    TutorialCounter = 0;
                    Goal = false;
                }
            }
        }

        // PAUSE STATE
        if(panel.GameState == panel.pauseState){
            PauseScreen();
        }

        // DIALOGUE STATE
        if(panel.GameState == panel.dialogueState){
            DialogueWindow();
        }

        // GAME WON STATE
        if(panel.GameState == panel.GameWonState){
            GameWonScreen();
        }

        // GAME OVER STATE
        if(panel.GameState == panel.GameOverState){
            GameOverScreen();
        }

    }

    // TILE SCREEN
    public void TitleScreen(){

        // BACKGROUND COLOR
        //graphics2D.setColor(new Color(81,113,145));
        //graphics2D.fillRect(0, 0, panel.ScreenWidth, panel.ScreenHeight);

        // GAME TITLE NAME
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));
        String Text = "WAY OUT";
        int x = CenterXText(Text);
        int y = panel.tileSize*3;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text,x+5,y+5);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // PLAYER CHARACTER
        x = panel.ScreenWidth/2 - (panel.tileSize);
        y += panel.tileSize*2;
        graphics2D.drawImage(panel.player.DOWN2, x, y, panel.tileSize*2, panel.tileSize*2, null);

        // NEW GAME
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "NEW GAME";
        x = CenterXText(Text);
        y += panel.tileSize*3.5;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        if(command == 0){
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);        }

        // LOAD GAME
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "LOAD GAME";
        x = CenterXText(Text);
        y += panel.tileSize;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        if(command == 1){
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }

        // QUIT
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "QUIT";
        x = CenterXText(Text);
        y += panel.tileSize;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        if(command == 2){
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);        }

    }

    // PAUSE SCREEN
    public void PauseScreen(){

        graphics2D.setColor(new Color(0,0,0,100));
        graphics2D.fillRect(0,0,panel.ScreenWidth,panel.ScreenHeight);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 45F));
        String Text = "PAUSED";

        int x = CenterXText(Text);
        int y= panel.ScreenHeight/2;

        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
    }

    // GENERAL WINDOW
    public void Window(int x, int y, int width, int height){
        Color color = new Color(0, 0, 0,205);       //RGB Number for Black (r, g, b, hasalpha) Color + transparency
        graphics2D.setColor(color);

        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255,255,255, 215);         //RGB Number for White
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(5));
        //setStroke(new BasicStroke(int)) -> defines the width of the outlines of graphics which are rendered with a Graphics2D
        graphics2D.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    // DIALOGUE SCREEN
    public void DialogueWindow(){
        // WINDOW
        int width = panel.ScreenWidth - (panel.tileSize*4);
        int height = panel.tileSize*3;
        int x = panel.ScreenWidth/2 - width/2;      //Window in Center of Screen
        int y = panel.ScreenHeight - height - panel.tileSize/2;

        Window(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 18));
        x += panel.tileSize;
        y += panel.tileSize;
        //graphics2D.drawString(currentDialogue, x, y);

        for(String line : currentDialogue.split("\n")){
            graphics2D.drawString(line, x, y);
            y += 20;
        }
    }

    // CENTER OF X-COORDINATE
    public int CenterXText(String Text){
        int TextLength = (int)graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();
        int x = panel.ScreenWidth/2 - TextLength/2;
        return x;
    }

    public void GameWonScreen(){

        graphics2D.setColor(new Color(0,0,0,140));
        graphics2D.fillRect(0,0,panel.ScreenWidth,panel.ScreenHeight);

        // CONGRATULATIONS  ----------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 60F));
        String Text = "CONGRATULATIONS";
        int x = CenterXText(Text);
        int y = panel.tileSize*4;

        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+5,y+5);

        // MAIN COLOR
        graphics2D.setColor(Color.YELLOW);
        graphics2D.drawString(Text, x, y);


        // DOOR UNLOCKED    ----------------------
        Text = "Door unlocked!";
        graphics2D.setFont(Retro_Gaming);
        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 30F));
        x = CenterXText(Text);
        y += panel.tileSize*2;
        graphics2D.drawString(Text, x, y);
        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+3,y+3);
        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);


        // PLAY AGAIN       ----------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "PLAY AGAIN";
        x = CenterXText(Text);
        y += panel.tileSize*2.5;
        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if(command == 0){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);        }

        // MAIN MENU        ----------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "MAIN MENU";
        x = CenterXText(Text);
        y += panel.tileSize;
        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if(command == 1){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }

        // QUIT         ----------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "QUIT";
        x = CenterXText(Text);
        y += panel.tileSize;
        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if(command == 2){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);        }
    }

    public void GameOverScreen(){

        graphics2D.setColor(new Color(0,0,0,140));
        graphics2D.fillRect(0,0,panel.ScreenWidth,panel.ScreenHeight);

        // GAME OVER  ----------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 60F));
        String Text = "GAME OVER!";
        int x = CenterXText(Text);
        int y = panel.tileSize*4;

        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+5,y+5);

        // MAIN COLOR
        graphics2D.setColor(Color.RED);
        graphics2D.drawString(Text, x, y);

        // RETRY      ----------------------
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "RETRY";
        x = CenterXText(Text);
        y += panel.tileSize*4.5;
        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if(command == 0){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);        }

        // MAIN MENU        ----------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "MAIN MENU";
        x = CenterXText(Text);
        y += panel.tileSize;
        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if(command == 1){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);        }

        // QUIT         ----------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "QUIT";
        x = CenterXText(Text);
        y += panel.tileSize;
        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text,x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if(command == 2){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);        }
    }
}
