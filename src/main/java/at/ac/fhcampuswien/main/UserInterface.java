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
            > Title Screen
            > Menu Option Screen
            > How To Play Screen
            > Credits Screen
        - PLAY STATE
            > Time
            > Message
            > Key
            > Tool (Screwdriver)
            > Kitchen Inventory
            > Tutorial
        - PAUSE STATE
            > Pause Screen
        - DIALOGUE STATE
            > Dialogue Window
        - GAME WON STATE
            > Game Won Screen
        - GAME OVER STATE
            > Game Over Screen
        - TRANSITION STATE
            > Transition ON Screen
            > Transition OFF Screen
        - CHARACTER STATE
            > Character Screen
        - OPTION STATE
            > Option Screen
            > Settings
            > How To Play
            > Restart Game
            > Exit Game
            > Main Menu
        - OTHER
            > Window
            > Center X Text
            > Center Window X Text

    METHODS FOR EACH!
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
    public boolean Timer = true;
    public double playTime=100;      //100 sec Countdown
    public double DefaultPlayTime = playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");  //minimize decimals
    DecimalFormat decimalForCharacter = new DecimalFormat("#0.0");  //minimize decimals

    public int optionState = 0;
    public int menuState = 0;

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
            if(Timer) {
                playTime -= (double) 1 / 60;    //add 1/60 to the loop (60 Frames per sec)
                graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));
                // SHADOW
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString("Time: " + decimalFormat.format(playTime), panel.tileSize * 12 + 7 + 2, 60 + 2);
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString("Time: " + decimalFormat.format(playTime), panel.tileSize * 12 + 7, 60);
            }

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
                OBJECT_Key key = new OBJECT_Key();
                bufferedImage = key.image;
                graphics2D.drawImage(bufferedImage, 13 * panel.tileSize, 90, 24 * 3, 13 * 3, null);
            }

            // TOOL (Screwdriver) -----------------------------------------------------------
            if (foundTool) {
                if(!usedTool) {
                    OBJECT_Tool tool = new OBJECT_Tool();
                    bufferedImage = tool.image;
                    graphics2D.drawImage(bufferedImage, 14 * panel.tileSize, 90, 7 * 3, 28 * 3, null);
                }
            }

            // KITCHEN INVENTORY -----------------------------------------------------------
            if(collectEmptyPlate){
                if(!usedEmptyPlate) {
                    K_EmptyPlate emptyPlate = new K_EmptyPlate();
                    bufferedImage = emptyPlate.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize+15, 90, 14*5, 8*5, null);
                }
            }

            if(collectEmptyPot){
                if(!usedEmptyPot) {
                    K_EmptyPot emptyPot = new K_EmptyPot();
                    bufferedImage = emptyPot.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize+24, 90, 14 * 5, 13 * 5, null);
                }
            }

            if(collectSoupCan) {
                if (!usedSoupCan) {
                    K_SoupCan soupCan = new K_SoupCan();
                    bufferedImage = soupCan.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize+24, 90, 6*5, 9*5, null);
                }
            }

            if(collectSoupPlate) {
                if (!usedSoupPlate) {
                    K_SoupPlate soupPlate = new K_SoupPlate();
                    bufferedImage = soupPlate.image;
                    graphics2D.drawImage(bufferedImage, 13 * panel.tileSize+15, 90, 14*5, 8*5, null);
                }
            }

            if(collectSoupPot) {
                if (usedSoupPot) {
                    K_SoupPot soupPot = new K_SoupPot();
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

        // OPTION STATE ------------------------------------------------------------------
        if(panel.GameState == panel.optionState){
            OptionScreen();
        }


        // MENU OPTION STATE ------------------------------------------------------------------
        if(panel.GameState == panel.menuOptionState){
            MenuOptionScreen();
        }

        // HOW TO PLAY STATE ------------------------------------------------------------------
        if(panel.GameState == panel.howToPlayState){
            HowToPlayScreen();
        }

        // CREDITS STATE
        if(panel.GameState == panel.creditsState){
            CreditsScreen();

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
        int y = panel.tileSize * 3;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 5, y + 5);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);


        // PLAYER CHARACTER   ------------------------------------------------------------------
        x = panel.ScreenWidth / 2 - (panel.tileSize);
        y += panel.tileSize * 2;
        graphics2D.drawImage(panel.player.DOWN2, x, y, panel.tileSize * 2, panel.tileSize * 2, null);


        // NEW GAME     ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 28F));
        Text = "PLAY";
        x = CenterXText(Text);
        y += panel.tileSize * 3;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // CHOOSE OPTION
        if (command == 0) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
        }

        // SETTINGS   ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 28F));
        Text = "SETTINGS";
        x = CenterXText(Text);
        y += 36;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // CHOOSE OPTION
        if (command == 1) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
            if (panel.handler.Enter) {
                panel.handler.Enter = false;
                menuState = 1;
                command = 0;
            }
        }

        // HOW TO PLAY    ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 28F));
        Text = "HOW TO PLAY";
        x = CenterXText(Text);
        y += 36;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // CHOOSE OPTION
        if (command == 2) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
            if (panel.handler.Enter) {
                menuState = 2;
                command = 0;
            }
        }

        // CREDITS    ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 28F));
        Text = "CREDITS";
        x = CenterXText(Text);
        y += 36;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // CHOOSE OPTION
        if (command == 3) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
        }

        // EXIT GAME     ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 28F));
        Text = "EXIT GAME";
        x = CenterXText(Text);
        y += 36;

        // SHADOW
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);

        // MAIN COLOR
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // CHOOSE OPTION
        if (command == 4) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
        }
    }
    public void MenuOptionScreen(){

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, panel.ScreenWidth, panel.ScreenHeight);

        // SETTINGS       ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 70F));
        String Text = "SETTINGS";
        int x = CenterXText(Text);
        int y = panel.tileSize * 3;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 5, y + 5);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // MUSIC       ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 28F));
        Text = "Music";
        x = panel.tileSize*2;
        y += panel.tileSize * 2.5;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if (command == 0) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
        }

        // SOUND FX       ------------------------------------------------------------------
        Text = "SOUND FX";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if (command == 1) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
        }

        // TIMER ON/OFF      ------------------------------------------------------------------
        Text = "Timer ON/OFF";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if (command == 2) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
            if(panel.handler.Enter){
                panel.handler.Enter = false;
                Timer = ! Timer;
            }
        }

        // BACK       ------------------------------------------------------------------
        Text = "Back";
        x = CenterXText(Text);
        y = panel.ScreenHeight-panel.tileSize*2;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        if (command == 3) {
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(">", x - 30, y + 3);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(">", x - 30, y);
            if(panel.handler.Enter){
                panel.GameState = panel.titleState;
                command = 1;
                panel.handler.Enter=false;
            }
        }

        // RIGHT SIDE       ------------------------------------------------------------------

        // MUSIC VOLUME BAR ------------------------
        x = panel.tileSize*9;
        y = panel.tileSize*5-5;
        graphics2D.drawRoundRect(x,y,153, 22, 6, 6);
        int volumeWidth = 29*panel.sound.volumeScale;
        graphics2D.fillRoundRect(x+4,y+4,volumeWidth, 14, 3, 3); //145:5 = 29

        // SOUND FX BAR ------------------------
        y += 40;
        graphics2D.drawRoundRect(x,y,153, 22, 6, 6);
        volumeWidth = 29*panel.soundEffect.volumeScale;
        graphics2D.fillRoundRect(x+4,y+4,volumeWidth, 14, 3, 3);


        // TIMER CHECKBOX  ------------------------
        y += 38;
        x = panel.tileSize*10+12;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(x, y,28, 28, 8, 8);
        if(Timer){
            graphics2D.fillRoundRect(x+4,y+4,20, 20, 3, 3);
        }
    }
    public void HowToPlayScreen(){
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, panel.ScreenWidth, panel.ScreenHeight);

        // RIGHT SIDE       ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 70F));
        String Text = "SETTINGS";
        int x = CenterXText(Text);
        int y = panel.tileSize * 3;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 5, y + 5);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 28F));
        Text = "Move ";
        x = panel.tileSize * 2;
        y += panel.tileSize * 2.5;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "Interact";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "Pause/Play";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "Character";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "Settings";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "Select";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // BOTTOM       ------------------------------------------------------------------
        Text = "Back with ESC";
        x = CenterXText(Text);
        y = panel.ScreenHeight-panel.tileSize-24;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);

        // LEFT SIDE       ------------------------------------------------------------------

        Text = "W A S D ";
        x = panel.tileSize * 9;
        y = panel.tileSize * 5 + 24;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "SPACE";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "     P";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "     C";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "     O";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        Text = "ENTER";
        y += 36;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 3, y + 3);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
    }
    public void CreditsScreen(){
        // BACKGROUND COLOR
        graphics2D.setColor(new Color(0,0,0));
        graphics2D.fillRect(0, 0, panel.ScreenWidth, panel.ScreenHeight);

        int y = panel.handler.y;

        // GAME TITLE       ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));
        String Text = "WAY OUT";
        int x = CenterXText(Text);
        y += panel.tileSize * 3;
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(Text, x + 5, y + 5);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(Text, x, y);
        y += panel.tileSize * 3;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 22F));

        // CREDITS
        String credits = """
                Game by

                Klea Kupi-Herbst
                Philipp Glatz
                Stella Gross
                Richard Libres
                Zahra Mousavi





                Project Leader &
                Program Supervision
                
                Klea Kupi-Herbst
                





                Music by

                Philipp Glatz






                Art by

                Stella Gross






                Gameplay by

                Richard Libres






                FH Campus Wien

                Computer Science and Digital Communication
                Year Group: CE25

                2022""";

        for(String line : credits.split("\n")){
            x = CenterXText(line);
            graphics2D.drawString(line, x,y);
            y += 24;
        }

        // SCROLL
        String text;
        text = "Scroll:\n  W/S";
        x = 24;
        y = panel.ScreenHeight- panel.tileSize;
        for(String line : text.split("\n")){
            graphics2D.drawString(line, x,y);
            y += 24;
        }

        // BACK WITH ESC
        text = "Back with ESC";
        int TextLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        x = panel.ScreenWidth-TextLength-24;
        y = panel.ScreenHeight- 26;
        graphics2D.drawString(text, x,y);
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
        // WINDOW for Dialogue, Character & Options
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

    // CHARACTER SCREEN
    public void CharacterScreen(){

        // WINDOW
        final int characterWindowX = panel.tileSize;
        final int characterWindowY = panel.tileSize;
        final int characterWindowWidth = panel.tileSize*8;
        final int characterWindowHeight = panel.tileSize*5;
        Window(characterWindowX, characterWindowY, characterWindowWidth, characterWindowHeight);

        // TEXT
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24F));

        int textX = characterWindowX + panel.tileSize/2;
        int textY = characterWindowY+ 2*panel.tileSize;
        final int lineHeight = 24;      //same as Font Size


        // GAME TITLE       ------------------------------------------------------------------
        String title = "WAY OUT";
        int titleX = CenterWindowXText(title, characterWindowWidth, characterWindowX);
        graphics2D.drawString(title, titleX, textY-40);


        // LEFT SIDE       ------------------------------------------------------------------
        graphics2D.drawString("Name", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Level", textX, textY);
        textY += lineHeight;

        graphics2D.drawString("Complete", textX, textY);
        textY += lineHeight;

        if(Timer) {     //Only show time if Timer is ON
            graphics2D.drawString("Time Left", textX, textY);
            textY += lineHeight;
        }

        graphics2D.drawString("Keys", textX, textY);


        // RIGHT SIDE       ------------------------------------------------------------------
        String Info = panel.player.CharacterName;
        textY = characterWindowY+characterWindowY+ panel.tileSize;
        int InfoX = (characterWindowX+characterWindowWidth) - (int)graphics2D.getFontMetrics().getStringBounds(Info, graphics2D).getWidth() - 20;
        graphics2D.drawString(Info, InfoX, textY);
        textY += lineHeight;

        Info = panel.player.Level;
        InfoX = (characterWindowX+characterWindowWidth) - (int)graphics2D.getFontMetrics().getStringBounds(Info, graphics2D).getWidth() - 20;
        graphics2D.drawString(Info, InfoX, textY);
        textY += lineHeight;

        Info = (CompletionOfGame*100/panel.player.FullCompletion) + " %";
        InfoX = (characterWindowX+characterWindowWidth) - (int)graphics2D.getFontMetrics().getStringBounds(Info, graphics2D).getWidth() - 20;
        graphics2D.drawString(Info, InfoX, textY);
        textY += lineHeight;

        if(Timer) {     // only show Time if Timer is ON
            Info = decimalForCharacter.format(playTime) + " s";
            InfoX = (characterWindowX + characterWindowWidth) - (int) graphics2D.getFontMetrics().getStringBounds(Info, graphics2D).getWidth() - 20;
            graphics2D.drawString(Info, InfoX, textY);
            textY += lineHeight;
        }

        Info = panel.player.Keys +"/3";
        InfoX = (characterWindowX+characterWindowWidth) - (int)graphics2D.getFontMetrics().getStringBounds(Info, graphics2D).getWidth() - 20;
        graphics2D.drawString(Info, InfoX, textY);
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

    // OPTION SCREEN
    public void OptionScreen(){
        // WINDOW
        final int optionWindowX = panel.tileSize;
        final int optionWindowY = panel.tileSize;
        final int optionWindowWidth = panel.tileSize*8;
        final int optionWindowHeight = panel.tileSize*10;
        Window(optionWindowX, optionWindowY, optionWindowWidth, optionWindowHeight);

        // TEXT
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24F));

        int textY = optionWindowY+ 2*panel.tileSize;

        // TITLE
        String title = "Settings";
        int titleX = CenterWindowXText(title, optionWindowWidth, optionWindowX);
        graphics2D.drawString(title, titleX, textY-40);

        // OPTION STATE
        switch (optionState) {
            case 0 -> Settings(optionWindowX, optionWindowY, optionWindowHeight, optionWindowWidth);
            case 1 -> HowToPlay(optionWindowX, optionWindowY, optionWindowHeight, optionWindowWidth);
            case 2 -> ExitGame(optionWindowX, optionWindowY, optionWindowWidth);
            case 3 -> RestartGame(optionWindowX, optionWindowY, optionWindowWidth);
            case 4 -> MainMenu(optionWindowX, optionWindowY, optionWindowWidth);
        }

        panel.handler.Enter = false;
    }
    public void Settings(int optionWindowX, int optionWindowY, int optionWindowHeight, int optionWindowWidth){

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 22F));

        int textX = optionWindowX + panel.tileSize-10;
        int textY = optionWindowY+ 2*panel.tileSize+12;
        final int lineHeight = 36;


        // LEFT SIDE       ------------------------------------------------------------------
        // TIMER
        graphics2D.drawString("Timer", textX, textY);
        if(command==0){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                Timer = !Timer;
            }
        }

        // MUSIC
        textY += lineHeight;
        graphics2D.drawString("Music", textX, textY);
        if(command==1){
            graphics2D.drawString(">",textX-20, textY);
        }

        // SOUND FX
        textY += lineHeight;
        graphics2D.drawString("Sound FX", textX, textY);
        if(command==2){
            graphics2D.drawString(">",textX-20, textY);
        }

        // HOW TO PLAY
        textY += lineHeight;
        graphics2D.drawString("How to play", textX, textY);
        if(command==3){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                optionState = 1;
                command = 0;
            }
        }

        // RESTART
        textY += lineHeight;
        graphics2D.drawString("Restart", textX, textY);
        if(command==4){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                optionState = 3;
                command = 0;
            }
        }

        // MAIN MENU
        textY += lineHeight;
        graphics2D.drawString("Main Menu", textX, textY);
        if(command==5){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                optionState = 4;
                command = 0;
            }
        }

        // EXIT GAME
        textY += lineHeight;
        graphics2D.drawString("Exit Game", textX, textY);
        if(command==6){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                optionState = 2;
                command = 0;
            }
        }

        // RIGHT SIDE       ------------------------------------------------------------------
        textY = optionWindowY+optionWindowHeight- 30;
        textX = (optionWindowX+optionWindowWidth) - (int)graphics2D.getFontMetrics().getStringBounds("Back", graphics2D).getWidth() - 24;
        graphics2D.drawString("Back", textX, textY);
        if(command==7){
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 22F));
            graphics2D.drawString(".",textX-20, textY-5);
            if(panel.handler.Enter){
                panel.GameState = panel.playState;
            }
        }

        // TIMER ON/OFF
        textY = optionWindowY+ 2*panel.tileSize-2;
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(textX,textY,20, 20, 8, 8);
        if(Timer){
            graphics2D.fillRoundRect(textX+4,textY+4,12, 12, 3, 3);
        }

        // MUSIC VOLUME
        textY += lineHeight-1;
        textX = (optionWindowX+optionWindowWidth)-153-24;
        graphics2D.drawRoundRect(textX,textY,153, 16, 6, 6);
        int volumeWidth = 29*panel.sound.volumeScale;
        graphics2D.fillRoundRect(textX+4,textY+4,volumeWidth, 8, 3, 3); //145:5 = 29

        // SOUND FX
        textY += lineHeight;
        graphics2D.drawRoundRect(textX,textY,153, 16, 6, 6);
        volumeWidth = 29*panel.soundEffect.volumeScale;
        graphics2D.fillRoundRect(textX+4,textY+4,volumeWidth, 8, 3, 3);
    }
    public void HowToPlay(int optionWindowX, int optionWindowY, int optionWindowHeight, int optionWindowWidth){
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));

        int textX = optionWindowX + panel.tileSize-10;
        int textY = optionWindowY+ 3*panel.tileSize;
        final int lineHeight = 36;      //same as Font Size

        // TITLE       ------------------------------------------------------------------
        String title = "How to play";
        int titleX = CenterWindowXText(title, optionWindowWidth, optionWindowX);
        graphics2D.drawString(title, titleX, textY-40);


        // LEFT SIDE       ------------------------------------------------------------------
        textY +=lineHeight;
        graphics2D.drawString("Move", textX, textY);

        textY += lineHeight;
        graphics2D.drawString("Interact", textX, textY);

        textY += lineHeight;
        graphics2D.drawString("Pause/Play", textX, textY);

        textY += lineHeight;
        graphics2D.drawString("Character", textX, textY);

        textY += lineHeight;
        graphics2D.drawString("Settings", textX, textY);

        textY += lineHeight;
        graphics2D.drawString("Select", textX, textY);


        // BOTTOM       ------------------------------------------------------------------
        textY = optionWindowY+optionWindowHeight- 30;
        textX = (optionWindowX+optionWindowWidth) - (int)graphics2D.getFontMetrics().getStringBounds("Back", graphics2D).getWidth() - 24;
        graphics2D.drawString("Back", textX, textY);
        if(command==0){
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 22F));
            graphics2D.drawString(".",textX-20, textY-5);
            if(panel.handler.Enter){
                optionState = 0;
                command = 3;
            }
        }


        // RIGHT SIDE       ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));
        textX = (optionWindowX+optionWindowWidth) - (int)graphics2D.getFontMetrics().getStringBounds("W A S D", graphics2D).getWidth() - 24;
        textY = optionWindowY+ 3*panel.tileSize;
        textY +=lineHeight;
        graphics2D.drawString("W A S D", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("SPACE", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("    P", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("    C", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("    O", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("ENTER", textX, textY);
    }
    public void RestartGame(int optionWindowX, int optionWindowY, int optionWindowWidth){

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));

        // TITLE
        String title = "Restart";
        int textY = optionWindowY+ 3*panel.tileSize;
        int titleX = CenterWindowXText(title, optionWindowWidth, optionWindowX);
        graphics2D.drawString(title, titleX, textY);

        // CONFIRMATION REQUEST
        textY += panel.tileSize;
        String text = "Are your sure you want to\nrestart the Game?";
        for(String line: text.split("\n")){
            titleX = CenterWindowXText(line, optionWindowWidth, optionWindowX);
            graphics2D.drawString(line, titleX, textY);
            textY += 24;
        }

        // YES
        textY += panel.tileSize;
        text = "Yes";
        int textX = CenterWindowXText(text, optionWindowWidth, optionWindowX);
        graphics2D.drawString(text, textX, textY);
        if(command==0){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                panel.GameState = panel.playState;
                panel.handler.Reset = true;
            }
        }

        // NO
        textY += panel.tileSize;
        text = "No";
        textX = CenterWindowXText(text, optionWindowWidth, optionWindowX);
        graphics2D.drawString(text, textX, textY);
        if(command==1){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                optionState=0;
                command = 4;
            }
        }
    }
    public void ExitGame(int optionWindowX, int optionWindowY, int optionWindowWidth){

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));

        // TITLE
        String title = "Exit Game";
        int textY = optionWindowY+ 3*panel.tileSize;
        int titleX = CenterWindowXText(title, optionWindowWidth, optionWindowX);
        graphics2D.drawString(title, titleX, textY);


        // CONFIRMATION REQUEST
        textY += panel.tileSize;
        String text = "Are your sure you want to\nExit the Game?";
        for(String line: text.split("\n")){
            titleX = CenterWindowXText(line, optionWindowWidth, optionWindowX);
            graphics2D.drawString(line, titleX, textY);
            textY += 24;
        }

        // YES
        textY += panel.tileSize;
        text = "Yes";
        int textX = CenterWindowXText(text, optionWindowWidth, optionWindowX);
        graphics2D.drawString(text, textX, textY);
        if(command==0){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                System.exit(0);
            }
        }

        // NO
        textY += panel.tileSize;
        text = "No";
        textX = CenterWindowXText(text, optionWindowWidth, optionWindowX);
        graphics2D.drawString(text, textX, textY);
        if(command==1){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                optionState=0;
                command = 6;
            }
        }
    }
    public void MainMenu(int optionWindowX, int optionWindowY,  int optionWindowWidth){
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 20F));

        // TITLE
        String title = "Main Menu";
        int textY = optionWindowY+ 3*panel.tileSize;
        int titleX = CenterWindowXText(title, optionWindowWidth, optionWindowX);
        graphics2D.drawString(title, titleX, textY);

        // CONFIRMATION REQUEST
        String text = "Are your sure you want to\nreturn to main menu?";
        textY += panel.tileSize;
        for(String line: text.split("\n")){
            titleX = CenterWindowXText(line, optionWindowWidth, optionWindowX);
            graphics2D.drawString(line, titleX, textY);
            textY += 24;
        }

        // YES
        textY += panel.tileSize;
        text = "Yes";
        int textX = CenterWindowXText(text, optionWindowWidth, optionWindowX);
        graphics2D.drawString(text, textX, textY);
        if(command==0){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                panel.GameState = panel.titleState;
                panel.stopMusic();
                panel.playMusic(2);
            }
        }

        // NO
        textY += panel.tileSize;
        text = "No";
        textX = CenterWindowXText(text, optionWindowWidth, optionWindowX);
        graphics2D.drawString(text, textX, textY);
        if(command==1){
            graphics2D.drawString(">",textX-20, textY);
            if(panel.handler.Enter){
                optionState=0;
                command = 5;
            }
        }
    }

    // CENTER OF X-COORDINATE
    public int CenterXText(String Text){
        //gets length of written input string
        int TextLength = (int)graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();
        return panel.ScreenWidth/2 - TextLength/2;  // Half of length to get it in center
    }
    public int CenterWindowXText(String Text, int WindowWidth, int WindowX){
        //gets length of written input string
        int TextLength = (int)graphics2D.getFontMetrics().getStringBounds(Text, graphics2D).getWidth();
        return WindowWidth/2 - TextLength/2 + WindowX;  // Half of Window length to get it in center + begin where window begins
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


        // COMPLETION PERCENTAGE    ------------------------------------------------------------------
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

        // EXIT GAME         ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "EXIT GAME";
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

        // EXIT GAME         ------------------------------------------------------------------
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40F));
        Text = "EXIT GAME";
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
