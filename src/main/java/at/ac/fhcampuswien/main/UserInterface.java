package at.ac.fhcampuswien.main;

import at.ac.fhcampuswien.object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

// CLASS CONTENT
/*
    UI CONSTRUCTOR
        - Define Imported Font

    PRINT
        - TITLE SCREEN STATE
        - PLAY STATE
            > Time
            > Message
            > Key
            > Tool
            > Kitchen Tools
            > Tutorial
        - PAUSE STATE
        - DIALOGUE STATE
        - GAME WON STATE
        - GAME OVER STATE
        - TRANSITION STATE
        - CHARACTER STATE

    METHODS FOR EACH STATE!
 */

public class UserInterface {

    GamePanel panel;
    Graphics2D graphics2D;
    BufferedImage bufferedImage;
    Font Retro_Gaming;              // Imported Font
    public int command = 0;         // Choose Option

    // DIALOGE AND TEXT
    public String currentDialogue = "";
    public boolean MessageOn = false;
    public String Message = "";
    int MessageCounter = 0;

    // INVENTORY
    public boolean foundKey = false;
    public boolean foundTool = false;
    public boolean usedTool = false;

    //KITCHEN INVENTORY
    public boolean collectEmptyPot = false;
    public boolean collectSoupPot = false;
    public boolean collectSoupCan = false;
    public boolean collectEmptyPlate = false;
    public boolean collectSoupPlate = false;
    public boolean usedEmptyPot = false;
    public boolean usedSoupPot = false;
    public boolean usedSoupCan = false;
    public boolean usedEmptyPlate = false;
    public boolean usedSoupPlate = false;

    public int CompletionOfGame = 0;

    int TransitionCounter = 0;

    public boolean TutorialOn = false;
    public boolean Goal = false;
    int TutorialCounter = 0;


    // PLAY TIME
    public double playTime=100;      //100 sec Countdown
    public double DefaultPlayTime = playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");  //minimize decimals

    // UI CONSTRUCTOR
    public UserInterface(GamePanel panel){
        this.panel = panel;

        // IMPORTED FONT FILE
        InputStream is = getClass().getResourceAsStream("/Font/Retro Gaming.ttf");
        try {
            assert is != null;  //Javas Suggestion
            Retro_Gaming = Font.createFont(Font.TRUETYPE_FONT, is); //TRUE TYPE FONT due to imported Font
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }

    // PRINT
    public void draw(Graphics2D graphics2D){

        this.graphics2D = graphics2D;
        graphics2D.setFont(Retro_Gaming);
        graphics2D.setColor(Color.white);

        // TITLE STATE  ------------------------------------------------------------------
        if(panel.GameState == panel.titleState){
            TitleScreen();
        }

        // PLAY STATE   ------------------------------------------------------------------
        if(panel.GameState == panel.playState) {

            graphics2D.setFont(Retro_Gaming);
            graphics2D.setColor(Color.white);

            // TIME ------------------------------------------------------------------
            playTime -= (double) 1 / 60;    //add 1/60 to the loop (60 Frames per sec)
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));
            // SHADOW
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString("Time: " + decimalFormat.format(playTime), panel.tileSize * 12 + 7+2, 60+2);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("Time: " + decimalFormat.format(playTime), panel.tileSize * 12 + 7, 60);


            // MESSAGE ------------------------------------------------------------------
            if (MessageOn) {
                graphics2D.drawString(Message, panel.tileSize, panel.tileSize + 90);

                MessageCounter++;
                if (MessageCounter > 120) {   //120 Frames (60 Frames per sec -> 2 sec)
                    MessageCounter = 0;
                    MessageOn = false;
                }
            }

            // KEY ------------------------------------------------------------------
            if (foundKey) {
                OBJECT_Key key = new OBJECT_Key(panel);
                bufferedImage = key.image;
                graphics2D.drawImage(bufferedImage, 13 * panel.tileSize, 90, 24 * 3, 13 * 3, null);
            }

            // TOOL (Screwdriver) -----------------------------------------------------------
            if (foundTool) {
                if(!usedTool) {
                    OBJECT_Tool tool = new OBJECT_Tool(panel);
                    bufferedImage = tool.image;
                    graphics2D.drawImage(bufferedImage, 14 * panel.tileSize, 90, 7 * 3, 28 * 3, null);
                }
            }

            // KITCHEN INVENTORY -----------------------------------------------------------
            if(collectEmptyPlate){
                if(!usedEmptyPlate) {
                    K_EmptyPlate emptyPlate = new K_EmptyPlate(panel);
                    bufferedImage = emptyPlate.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize+15, 90, 14*5, 8*5, null);
                }
            }

            if(collectEmptyPot){
                if(!usedEmptyPot) {
                    K_EmptyPot emptyPot = new K_EmptyPot(panel);
                    bufferedImage = emptyPot.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize+24, 90, 14 * 5, 13 * 5, null);
                }
            }

            if(collectSoupCan) {
                if (!usedSoupCan) {
                    K_SoupCan soupCan = new K_SoupCan(panel);
                    bufferedImage = soupCan.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize+24, 90, 6*5, 9*5, null);
                }
            }

            if(collectSoupPlate) {
                if (!usedSoupPlate) {
                    K_SoupPlate soupPlate = new K_SoupPlate(panel);
                    bufferedImage = soupPlate.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize+15, 90, 14*5, 8*5, null);
                }
            }

            if(collectSoupPot) {
                if (usedSoupPot) {
                    K_SoupPot soupPot = new K_SoupPot(panel);
                    bufferedImage = soupPot.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize, 90, 24 * 3, 13 * 3, null);
                }
            }

            // TUTORIAL ------------------------------------------------------------------
            if (TutorialOn) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 17F));
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString("Move: WASD", panel.tileSize+2, panel.tileSize+2);
                graphics2D.drawString("Interact: SPACE", panel.tileSize+2, panel.tileSize + 30+2);
                graphics2D.drawString("Dialogue: ENTER", panel.tileSize+2, panel.tileSize + 60+2);
                graphics2D.drawString("Pause/Play: P", panel.tileSize+2, panel.tileSize + 90+2);
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString("Move: WASD", panel.tileSize, panel.tileSize);
                graphics2D.drawString("Interact: SPACE", panel.tileSize, panel.tileSize + 30);
                graphics2D.drawString("Dialogue: ENTER", panel.tileSize, panel.tileSize + 60);
                graphics2D.drawString("Pause/Play: P", panel.tileSize, panel.tileSize + 90);

                TutorialCounter++;
                if (TutorialCounter > 240) {   //180 Frames (60 Frames per sec -> 3 sec)
                    TutorialCounter = 0;
                    TutorialOn = false;
                    Goal = true;
                }
            } else if (Goal) {
                graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 17F));
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString("Find the KEY!", panel.tileSize+2, panel.tileSize+2);
                graphics2D.drawString("Escape the Room!", panel.tileSize+2, panel.tileSize + 32);
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString("Find the KEY!", panel.tileSize, panel.tileSize);
                graphics2D.drawString("Escape the Room!", panel.tileSize, panel.tileSize + 30);

                TutorialCounter++;
                if (TutorialCounter > 240) {   //180 Frames (60 Frames per sec -> 3 sec)
                    TutorialCounter = 0;
                    Goal = false;
                }
            }
        }

        // PAUSE STATE  ------------------------------------------------------------------
        if(panel.GameState == panel.pauseState){
            PauseScreen();
        }

        // DIALOGUE STATE   ------------------------------------------------------------------
        if(panel.GameState == panel.dialogueState){
            DialogueWindow();
        }

        // GAME WON STATE   ------------------------------------------------------------------
        if(panel.GameState == panel.GameWonState){
            GameWonScreen();
        }

        // GAME OVER STATE  ------------------------------------------------------------------
        if(panel.GameState == panel.GameOverState){
            GameOverScreen();
        }

        // CHARACTER STATE  ------------------------------------------------------------------
        if(panel.GameState == panel.characterState){
            CharacterScreen();
        }

        // TRANSITION STATE ------------------------------------------------------------------
        if(panel.GameState == panel.transitionState){
            drawTransitionInState();
        }
        if(panel.GameState == panel.transitionOutState){
            drawTransitionOutState();
        }
    }

    // TILE SCREEN
    public void TitleScreen(){

        // BACKGROUND COLOR
        //graphics2D.setColor(new Color(81,113,145));
        //graphics2D.fillRect(0, 0, panel.ScreenWidth, panel.ScreenHeight);

        // GAME TITLE       ------------------------------------------------------------------
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


        // PLAYER CHARACTER   ------------------------------------------------------------------
        x = panel.ScreenWidth/2 - (panel.tileSize);
        y += panel.tileSize*2;
        graphics2D.drawImage(panel.player.DOWN2, x, y, panel.tileSize*2, panel.tileSize*2, null);



        // NEW GAME     ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 0){
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }

        // LOAD GAME    ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 1){
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }

        // QUIT     ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 2){
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }
    }

    // PAUSE SCREEN
    public void PauseScreen(){

        // Background Transparency (little darker than actual background image)
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
        // WINDOW
        //Set color for Window (not 100% black; little transparency)
        Color color = new Color(0, 0, 0,205);       //RGB Number for Black (r, g, b, transparency) Color + transparency
        graphics2D.setColor(color);

        graphics2D.fillRoundRect(x, y, width, height, 35, 35);  //fill Round Rectangle

        // WHITE OUTLINES
        color = new Color(255,255,255, 215);         //RGB Number for White
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(5));
        //setStroke(new BasicStroke(int)) -> defines the width of the outlines of graphics which are rendered with a Graphics2D

        //Draw Round Rect vs. Fill Round Rect:   Fill - filled Rectangle / Draw - draw only outlines without filled area
        graphics2D.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    // DIALOGUE SCREEN
    public void DialogueWindow(){
        // WINDOW
        int width = panel.ScreenWidth - (panel.tileSize*4);
        int height = panel.tileSize*3;
        int x = panel.ScreenWidth/2 - width/2;      //Window in Center of Screen
        int y = panel.ScreenHeight - height - panel.tileSize/2;

        Window(x, y, width, height);    // call Window() Methode to draw wanted Window

        // DIALOGUE TEXT WITHIN WINDOW
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 18));
        x += panel.tileSize;    // little more to the left than where the dialogue starts
        y += panel.tileSize;
        //graphics2D.drawString(currentDialogue, x, y); //Write Dialogue

        //Problem: Dialogue to long to fit -> create more Strings to separate dialogue in pieces
        for(String line : currentDialogue.split("\n")){ // .split("\n")  -> knows when to split each dialogue
            //without .split("\n") the drawString doesn't know what \n means and just keeps going without inserting a new line
            graphics2D.drawString(line, x, y);
            y += 20;    //know where to draw the new String Text
        }
    }

    public void CharacterScreen(){
        //Still to come...
    }

    // MAP TRANSITION
    public void drawTransitionInState(){
        TransitionCounter++;        // Count up
        graphics2D.setColor(new Color(0, 0, 0, 2*TransitionCounter + 5)); // gets darker the more it counts up
        graphics2D.fillRect(0, 0, panel.ScreenWidth, panel.ScreenHeight);      //fill screen darker and darker

        if (TransitionCounter == 40){   //when reached certain point

            panel.currentMap = panel.TransitionMap;                         // Which Map to Switch to
            if (panel.TransitionMap == 0) panel.asset.setObjectBedroom();   //Set object to corresponding map
            if (panel.TransitionMap == 1) panel.asset.setObjectCorridor();
            if(panel.TransitionMap == 2) panel.asset.setObjectBathroom();
            if(panel.TransitionMap == 3) panel.asset.setObjectKitchen();

            panel.player.MapX = panel.tileSize * panel.TransitionX - (panel.tileSize / 2);     // X-Coordinate of Spawn point
            panel.player.MapY = panel.tileSize * panel.TransitionY - (panel.tileSize / 2);     // Y-Coordinate of Spawn point

            TransitionCounter=0;    //set counter back to zero for next Transition
            panel.GameState = panel.transitionOutState; // go to next step (Make screen lighter again; same transition just backwards)
        }
    }

    public void drawTransitionOutState(){
        // go out of transition: lighten the screen again
        TransitionCounter++;
        graphics2D.setColor(new Color(0, 0, 0, 80-4*TransitionCounter));    //the certain which was reached in if(TransitionCounter == 40) from methode above
        graphics2D.fillRect(0,0, panel.ScreenWidth, panel.ScreenHeight);        //show new screen transparency


        if(TransitionCounter == 20){    //when its lighten enough
            TransitionCounter = 0;      //set TransitionCounter back for next needed Transition
            panel.GameState = panel.playState;  //switch back to player state
        }
    }

    // CENTER OF X-COORDINATE
    public int CenterXText(String Text){
        //gets length of written input string
        int TextLength = (int)graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();
        return panel.ScreenWidth/2 - TextLength/2;  // Half of length to get it in center
    }

    // GAME WON SCREEN
    public void GameWonScreen(){

        // Background Transparency (little darker than actual background image)
        graphics2D.setColor(new Color(0,0,0,140));
        graphics2D.fillRect(0,0,panel.ScreenWidth,panel.ScreenHeight);

        // CONGRATULATIONS  ------------------------------------------------------------------
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


        // DOOR UNLOCKED    ------------------------------------------------------------------
        Text = "Completed: ";
        graphics2D.setFont(Retro_Gaming);
        graphics2D.setColor(Color.white);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 30F));
        x = CenterXText(Text);
        y += panel.tileSize*2;

        // SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(Text + (CompletionOfGame*100/panel.player.FullCompletion) + "%",x+3,y+3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text + ((CompletionOfGame*100)/panel.player.FullCompletion) + "%", x, y);


        // PLAY AGAIN       ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 0){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }

        // MAIN MENU        ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 1){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }

        // QUIT         ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 2){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }
    }


    // GAME OVER SCREEN
    public void GameOverScreen(){

        // Background Transparency (little darker than actual background image)
        graphics2D.setColor(new Color(0,0,0,140));
        graphics2D.fillRect(0,0,panel.ScreenWidth,panel.ScreenHeight);

        // GAME OVER  ------------------------------------------------------------------
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

        // RETRY      ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 0){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }

        // MAIN MENU        ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 1){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }

        // QUIT         ------------------------------------------------------------------
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

        // CHOOSE OPTION
        if(command == 2){
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString(">", x- panel.tileSize+3, y+3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x- panel.tileSize, y);
        }
    }
}
